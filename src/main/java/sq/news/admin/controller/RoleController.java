package sq.news.admin.controller;

import sq.base.AppResult;
import sq.news.admin.constants.SysConstants;
import sq.news.admin.service.AdminService;
import sq.news.admin.service.RoleService;
import sq.news.admin.constants.RestConstants;
import sq.news.admin.entity.Admin;
import sq.news.admin.entity.Permission;
import sq.news.admin.entity.Role;
import sq.news.admin.entity.Slider;
import sq.news.admin.service.PermissionService;
import sq.news.admin.service.SliderService;
import sq.util.AppResultBuilder;
import sq.util.FormatUtil;
import sq.util.PermissionUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/admins/roles")
@ApiResponses(value = { @ApiResponse(code = 1000, message = "操作成功"),
		@ApiResponse(code = 1001, message = "账户长时间未使用，请重新登录"), @ApiResponse(code = 1002, message = "请先登录"),
		@ApiResponse(code = 1003, message = "登录异常"), @ApiResponse(code = 1004, message = "您输入的密码有误"),
		@ApiResponse(code = 1005, message = "没有找到此管理员"), @ApiResponse(code = 1006, message = "没有权限"),
		@ApiResponse(code = 1007, message = "没有找到匹配的实体"), @ApiResponse(code = 1008, message = "此登录名[admin]无法创建和修改"),
		@ApiResponse(code = 1009, message = "该登录名已存在"), @ApiResponse(code = 1010, message = "查找成功"),
		@ApiResponse(code = 1011, message = "TOKEN不正确"), @ApiResponse(code = 1012, message = "没有查询到该角色"),
		@ApiResponse(code = 1013, message = "登录名不能为空"), @ApiResponse(code = 1014, message = "参数不合法！"),
		@ApiResponse(code = 1015, message = "该权限名称已存在"), @ApiResponse(code = 1016, message = "操作失败"),
		@ApiResponse(code = 1017, message = "该角色已存在"), @ApiResponse(code = 1020, message = "保存成功"),
		@ApiResponse(code = 1021, message = "查找失败"), })
public class RoleController {

	@Autowired
	private AdminController adminController;

	@Autowired
	private RoleService roleService;
	@Autowired
	private PermissionService permissionService;
	@Autowired
	private AdminService adminService;
	@Autowired
	private RedisTemplate redisTemplate;
	@Autowired
	private SliderService sliderService;

	@GetMapping
	@ApiOperation(value = "获取管理员列表（后台）")
	public AppResult<List<Role>> findAll(@RequestHeader("x-access-token") final String token,

			@RequestParam(defaultValue = "0") final String pageIndex,
			@RequestParam(defaultValue = "10") final String pageSize,
			@RequestParam(required = false) final String orderByField,
			@RequestParam(defaultValue = "false") final String pageNoLimit,
			@RequestParam(required = false) final String search) {
		AppResult<List<Role>> result = new AppResult<>();

		// 判断admin登录
		AppResult<Admin> adminResult = adminController.getAdmin(token);

		if (!adminResult.isSuccess()) {
			result.setMessage(RestConstants.NO_ADMIN);
			return result;
		}
		// 判断是否有权限
		if (!adminService.hasPermission(adminResult.getData(), "role:list")) {
			result.setMessage(RestConstants.NO_PERMISSION);
			return result;
		}

		try {
			List<Role> roles = roleService.findAllRolesNotAdmin(pageIndex, pageSize, orderByField, pageNoLimit, search);
			long count;
			if (!FormatUtil.isNullOrEmpty(search)) {
				count = roleService.countByNameLikeNotAdmin(search);
			} else {
				count = roleService.allCount();
			}

			result.setSuccess(true);
			result.setMessage(SysConstants.OPERATION_SUCCESS);
			result.setData(roles);
			result.setTotal(count);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setMessage(SysConstants.PARAM_UNLEGAL);
			result.setTotal(0);
			return result;
		}

	}

	@PostMapping
	@ApiOperation(value = "创建角色（后台）")
	public AppResult<Role> save(@RequestHeader("x-access-token") String token, @RequestBody Role role) {
		AppResult<Role> result = new AppResult<>();

		// 判断admin登录
		AppResult<Admin> adminResult = adminController.getAdmin(token);
		if (!adminResult.isSuccess()) {
			result.setMessage(RestConstants.NO_ADMIN);
			return result;
		}
		// 判断是否有权限
		if (!adminService.hasPermission(adminResult.getData(), "role:edit")) {
			result.setMessage(RestConstants.NO_PERMISSION);
			return result;
		}
		// 业务逻辑判断
		if (roleService.findByName(role.getName()) == null && roleService.findByRoleName(role.getRoleName()) == null) {
			roleService.save(role);

			result.setData(roleService.findByName(role.getName()));
			result.setTotal(1);
			result.setMessage(RestConstants.SAVE_SUCCESS);
			result.setSuccess(true);
			return result;
		} else {
			result.setTotal(1);
			result.setSuccess(false);
			result.setMessage(RestConstants.EXIST_ROLE);
			return result;
		}

	}

	@PutMapping("/{id}")
	@ApiOperation(value = "修改角色（后台）")
	public AppResult<Role> update(@RequestHeader("x-access-token") String token, @PathVariable long id,
			@RequestBody Role role) {
		System.out.println("=============================");
		AppResult<Role> result = new AppResult<>();

		// 判断admin登录
		AppResult<Admin> adminResult = adminController.getAdmin(token);
		if (!adminResult.isSuccess()) {
			result.setMessage(RestConstants.NO_ADMIN);
			return result;
		}
		// 判断是否有权限
		if (!adminService.hasPermission(adminResult.getData(), "role:edit")) {
			result.setMessage(RestConstants.NO_PERMISSION);
			return result;
		}

		// 业务逻辑判断
		Role roleInDb = roleService.findById(id);
		if (roleInDb == null) {
			result.setMessage(RestConstants.NO_RESULT);
			return result;
		}
		if ((roleService.findByName(role.getName()) == null)
				|| role.getName().equals(roleInDb.getName()) && (roleService.findByRoleName(role.getRoleName()) == null
						|| role.getRoleName().equals(roleInDb.getRoleName()))) {
			try {// 拷贝属性

				roleInDb.setName(role.getName());

				roleInDb.setDescription(role.getDescription());

				roleInDb.setRoleName(role.getRoleName());

				roleInDb.setEntityStatus(role.getEntityStatus());

			} catch (Exception e) {
				result.setMessage(SysConstants.OPERATION_FAIL);
				return result;
			}
			roleService.merge(roleInDb);
			List<Long> adminIds = roleInDb.getAdmins().stream().map(Admin::getId)
					.collect(Collectors
							.toList());
			List<sq.bean.Admin> redisAdmins = new ArrayList<>();
			adminIds.stream().forEach(adminId->{
				Admin findAdmin = adminService.findById(adminId);
				sq.bean.Admin redisAdmin= copyToRedisAdmin(findAdmin);
				redisAdmins.add(redisAdmin);
			});
			PermissionUtil.getInstance(redisTemplate).updatePermission(redisAdmins);
		} else {
			result.setTotal(1);
			result.setSuccess(false);
			result.setMessage(RestConstants.EXIST_ROLE);
			return result;
		}

		// result.setData(roleInDb);
		result.setTotal(1);
		result.setSuccess(true);
		result.setMessage(SysConstants.OPERATION_SUCCESS);
		return result;

	}

	@GetMapping("/{id}")
	@ApiOperation(value = "获取单个角色（后台）")
	public AppResult<Role> findById(@RequestHeader("x-access-token") String token, @PathVariable long id) {
		AppResult<Role> result = new AppResult<>();

		// 判断admin登录
		AppResult<Admin> adminResult = adminController.getAdmin(token);
		if (!adminResult.isSuccess()) {
			result.setMessage(RestConstants.NO_ADMIN);
			return result;
		}
		// 判断是否有权限
		if (!adminService.hasPermission(adminResult.getData(), "role:list")) {
			result.setMessage(RestConstants.NO_PERMISSION);
			return result;
		}
		// 业务逻辑判断

		Role roleInDb = roleService.findById(id);
		if (roleInDb == null) {
			result.setMessage(RestConstants.NO_RESULT);
			return result;
		}
		result.setMessage(RestConstants.FIND_SUCCESS);
		result.setData(roleInDb);
		result.setTotal(1);
		result.setSuccess(true);
		return result;
	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "删除角色（后台）")
	public AppResult<Role> deleteById(@RequestHeader("x-access-token") String token, @PathVariable long id) {
		AppResult<Role> result = new AppResult<>();

		// 判断admin登录
		AppResult<Admin> adminResult = adminController.getAdmin(token);
		if (!adminResult.isSuccess()) {
			result.setMessage(RestConstants.NO_ADMIN);
			return result;
		}
		// 判断是否有权限
		if (!adminService.hasPermission(adminResult.getData(), "role:delete")) {
			result.setMessage(RestConstants.NO_PERMISSION);
			return result;
		}
		// 业务逻辑判断
		Role roleInDb = roleService.findById(id);
		if (roleInDb == null) {
			result.setMessage(RestConstants.NO_RESULT);
			return result;
		}
		if (roleInDb.getRoleName().equals(SysConstants.SUPERADMIN)) {
			return AppResultBuilder.buildFailedMessageResult(RestConstants.NO_PERMISSION);
		}
		List<Long> adminIds = roleInDb.getAdmins().stream().map(Admin::getId)
				.collect(Collectors
				.toList());
		roleService.delete(roleInDb);
		List<sq.bean.Admin> redisAdmins = new ArrayList<>();
		adminIds.stream().forEach(adminId->{
			Admin findAdmin = adminService.findById(adminId);
			sq.bean.Admin redisAdmin= copyToRedisAdmin(findAdmin);
			redisAdmins.add(redisAdmin);
		});
		PermissionUtil.getInstance(redisTemplate).updatePermission(redisAdmins);
		result.setTotal(1);
		result.setSuccess(true);
		result.setMessage(SysConstants.OPERATION_SUCCESS);
		return result;
	}

	@PutMapping("/{id}/menus")
	@ApiOperation(value = "为角色设置导航栏（后台）")
	@ApiImplicitParams({ @ApiImplicitParam(name = "roles", value = "参数结构为{'data':[{'id':'2'},{'id':'3'}]}") })
	public AppResult<Role> setSlider(@RequestHeader("x-access-token") String token, @PathVariable long id,
			@RequestBody JSONObject roles) {
		AppResult<Admin> adminResult = adminController.getAdmin(token);
		if (!adminResult.isSuccess()) {
			AppResultBuilder.buildFailedMessageResult(RestConstants.NO_TOKEN);
		}
		Role resultroles = roleService.findById(id);
		if (resultroles == null) {
			return AppResultBuilder.buildFailedMessageResult(SysConstants.OPERATION_FAIL);
		}
		Set<Slider> roleSliders = new HashSet<Slider>();
		JSONArray sliders = roles.getJSONArray("data");
		for (Object object : sliders) {
			Slider slider = sliderService.findById(Long.parseLong(((JSONObject) object).getString("id")));
			if (slider != null) {
				sliderService.merge(slider);
				roleSliders.add(slider);
			}
		}

		resultroles.setMenus(roleSliders);
		roleService.merge(resultroles);
		return AppResultBuilder.buildSuccessMessageResult(resultroles, SysConstants.OPERATION_SUCCESS);
	}

	@PostMapping("/{id}/permissions")
	@ApiOperation(value = "为角色设置权限（后台）")
	public AppResult<Role> setPermissions(@RequestHeader("x-access-token") String token, @PathVariable long id,
			@RequestBody JSONObject jsonObject) {

		AppResult<Role> appResult = new AppResult<Role>();

		if (StringUtils.isEmpty(token)) {
			appResult.setSuccess(false);
			appResult.setMessage(RestConstants.NO_LOGIN);
			return appResult;
		}
		AppResult<Admin> adminResult = adminController.getAdmin(token);
		if (!adminResult.isSuccess()) {
			appResult.setMessage(RestConstants.NO_ADMIN);
			return appResult;
		}
		// 判断是否有权限
		if (!adminService.hasPermission(adminResult.getData(), "role:edit")) {
			appResult.setMessage(RestConstants.NO_PERMISSION);
			return appResult;
		}
		Role findRole = roleService.findById(id);
		if (findRole != null) {
			Set<Permission> adminsPermissions = new HashSet<Permission>();
			JSONArray permissionsArray = jsonObject.getJSONArray("data");
			for (Object object : permissionsArray) {

				Permission permission = permissionService
						.findById(Long.parseLong(((JSONObject) object).getString("id")));
				if (permission != null) {
					permissionService.merge(permission);
					adminsPermissions.add(permission);
				} else {
					appResult.setSuccess(false);
					appResult.setMessage(RestConstants.NO_PERMISSION);
					return appResult;
				}
			}
			for (int i = 0; i < permissionsArray.size(); i++) {
				JSONObject json = JSONObject.parseObject(permissionsArray.getString(i));

				Permission permission = permissionService.findById(Long.parseLong(json.getString("id")));
				if (permission != null) {
					permissionService.merge(permission);
					adminsPermissions.add(permission);
				} else {
					appResult.setSuccess(false);
					appResult.setMessage(RestConstants.NO_PERMISSION);
					return appResult;
				}
			}
			findRole.setPermissions(adminsPermissions);
			roleService.merge(findRole);
			List<Long> adminIds = findRole.getAdmins().stream().map(Admin::getId)
					.collect(Collectors
							.toList());
			List<sq.bean.Admin> redisAdmins = new ArrayList<>();
			adminIds.stream().forEach(adminId->{
				Admin findAdmin = adminService.findById(adminId);
				sq.bean.Admin redisAdmin= copyToRedisAdmin(findAdmin);
				redisAdmins.add(redisAdmin);
			});
			PermissionUtil.getInstance(redisTemplate).updatePermission(redisAdmins);
			appResult.setData(findRole);
			appResult.setSuccess(true);
			appResult.setMessage(RestConstants.SAVE_SUCCESS);
			appResult.setTotal(1);
		} else {
			appResult.setSuccess(false);
			appResult.setMessage(RestConstants.FIND_ERROR);
		}
		return appResult;
	}
	private sq.bean.Admin copyToRedisAdmin(Admin dbAdmin) {
		sq.bean.Admin admin = new sq.bean.Admin();
		admin.setId(dbAdmin.getId());
		admin.setLoginName(dbAdmin.getLoginName());
		admin.setDescription(dbAdmin.getDescription());
		admin.setPassword(dbAdmin.getPassword());
		admin.setLastLoginDate(dbAdmin.getLastLoginDate());
		admin.setName(dbAdmin.getName());
		admin.setLoginDate(dbAdmin.getLoginDate());
		Set<sq.bean.Permission> permissions = new HashSet<>();
		Set<sq.bean.Role> roles = new HashSet<>();
		dbAdmin.getRoles().stream().forEach(role -> {
			sq.bean.Role redisRole = new sq.bean.Role();
			redisRole.setId(role.getId());
			redisRole.setRoleName(role.getRoleName());
			redisRole.setDescription(role.getDescription());
			redisRole.setName(role.getName());
			roles.add(redisRole);
			role.getPermissions().stream().forEach(permission -> {
				sq.bean.Permission redisPermission = new sq.bean.Permission();
				redisPermission.setId(permission.getId());
				redisPermission.setPermission(permission.getPermission());
				redisPermission.setDescription(permission.getDescription());
				redisPermission.setName(permission.getName());
				permissions.add(redisPermission);
			});
		});
		admin.setPermissions(permissions);
		admin.setRoles(roles);
		return admin;
	}
}
