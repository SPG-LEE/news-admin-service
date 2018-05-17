package sq.news.admin.controller;

import sq.base.AppResult;
import sq.news.admin.constants.SysConstants;
import sq.news.admin.service.AdminService;
import sq.news.admin.constants.RestConstants;
import sq.news.admin.entity.Admin;
import sq.news.admin.entity.Permission;
import sq.news.admin.service.PermissionService;
import sq.util.FormatUtil;
import sq.util.PermissionUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(path = "/admins/permissions")
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
public class PermissionController {

	@Autowired
	private AdminController adminController;

	@Autowired
	private PermissionService permissionService;

	@Autowired
	private AdminService adminService;
	@Autowired
	private RedisTemplate redisTemplate;

	@GetMapping
	@ApiOperation(value = "获取权限列表（后台）")
	@ApiImplicitParams({ @ApiImplicitParam(paramType = "header", name = "x-access-token", value = "管理员token"),
			@ApiImplicitParam(paramType = "query", name = "pageIndex", value = "当前页", defaultValue = "0"),
			@ApiImplicitParam(paramType = "query", name = "pageSize", value = "列表显示条数", defaultValue = "10"),
			@ApiImplicitParam(paramType = "query", name = "orderByField", value = "根据字段排序", required = false),
			@ApiImplicitParam(paramType = "query", name = "pageNoLimit", value = "是否分页", defaultValue = "false"),
			@ApiImplicitParam(paramType = "query", name = "search", value = "搜索信息（名）", required = false) })
	public AppResult<List<Permission>> findAll(@RequestHeader("x-access-token") final String token,
			@RequestParam(defaultValue = "0") final String pageIndex,
			@RequestParam(defaultValue = "10") final String pageSize,
			@RequestParam(required = false) final String orderByField,
			@RequestParam(defaultValue = "false") final String pageNoLimit,
			@RequestParam(required = false) final String search) {

		final AppResult<List<Permission>> result = new AppResult<>();

		// 判断admin登录
		System.out.println("================================1111111111111111");
		final AppResult<Admin> adminResult = this.adminController.getAdmin(token);
		if (!adminResult.isSuccess()) {
			result.setMessage(RestConstants.NO_ADMIN);
			return result;
		}
		// 判断是否有权限
		if (!this.adminService.hasPermission(adminResult.getData(), "permission:list")) {
			result.setMessage(RestConstants.NO_PERMISSION);
			return result;
		}
		try {

			final List<Permission> permissions = this.permissionService.findAllPermissions(pageIndex, pageSize,
					orderByField, pageNoLimit, search);

			final long count;
			if (!FormatUtil.isNullOrEmpty(search)) {
				count = this.permissionService.countByNameLikeOrPermissionLikeAndEntityStatus(search);
			} else {
				count = this.permissionService.allCount();
			}

			result.setSuccess(true);
			result.setMessage(SysConstants.OPERATION_SUCCESS);
			result.setData(permissions);
			result.setTotal(count);

		} catch (final Exception e) {
			result.setSuccess(false);
			result.setMessage(SysConstants.PARAM_UNLEGAL);
			result.setTotal(0);
			return result;
		}
		return result;
	}

	@PostMapping
	@ApiOperation(value = "创建权限（后台）")
	public AppResult<Permission> save(@RequestHeader("x-access-token") final String token,
			@RequestBody final Permission permission) {
		final AppResult<Permission> result = new AppResult<>();

		if (FormatUtil.isNullOrEmpty(permission.getName()) || FormatUtil.isNullOrEmpty(permission.getPermission())) {
			result.setMessage(SysConstants.PARAM_UNLEGAL);
			result.setSuccess(false);
			return result;
		}

		// 判断admin登录
		final AppResult<Admin> adminResult = this.adminController.getAdmin(token);
		if (!adminResult.isSuccess()) {
			result.setSuccess(false);
			result.setMessage(RestConstants.NO_ADMIN);
			return result;
		}
		if (!this.adminService.hasPermission(adminResult.getData(), "permission:edit")) {
			result.setMessage(RestConstants.NO_PERMISSION);
			return result;
		}
		// 业务逻辑判断

		if (permissionService.findByPermission(permission.getPermission()) != null) {

			result.setMessage(RestConstants.EXIST_PERMISSION);
			result.setSuccess(false);
			return result;
		}
		if (permissionService.findByName(permission.getName()) == null
				&& permissionService.findByPermission(permission.getPermission()) == null) {
			permissionService.save(permission);
			result.setData(permissionService.findByName(permission.getName()));
			result.setTotal(1);
			result.setMessage(RestConstants.SAVE_SUCCESS);
			result.setSuccess(true);
			return result;
		} else {
			result.setTotal(1);
			result.setSuccess(false);
			result.setMessage(RestConstants.EXIST_PERMISSION);
			return result;
		}

	}

	@PutMapping("/{id}")
	@ApiOperation(value = "修改权限（后台）")
	public AppResult<Permission> update(@RequestHeader("x-access-token") final String token,
			@PathVariable final long id, @RequestBody final Permission permission) {
		final AppResult<Permission> result = new AppResult<>();

		// 判断admin登录
		final AppResult<Admin> adminResult = this.adminController.getAdmin(token);
		if (!adminResult.isSuccess()) {
			result.setMessage(RestConstants.NO_ADMIN);
			return result;
		}
		if (!this.adminService.hasPermission(adminResult.getData(), "permission:edit")) {
			result.setMessage(RestConstants.NO_PERMISSION);
			return result;
		}
		// 业务逻辑判断
		Permission permissionInDb = this.permissionService.findById(id);
		if (permissionInDb == null) {
			result.setMessage(RestConstants.NO_RESULT);
			return result;
		}
		// 拷贝属性
		if ((permissionService.findByName(permission.getName()) == null)
				|| permission.getName().equals(permissionInDb.getName())
						&& (permissionService.findByPermission(permission.getPermission()) == null
								|| permission.getPermission().equals(permissionInDb.getPermission()))) {
			try {

				permissionInDb.setName(permission.getName());

				permissionInDb.setDescription(permission.getDescription());

				permissionInDb.setPermission(permission.getPermission());

				permissionInDb.setEntityStatus(permission.getEntityStatus());
				permissionService.merge(permissionInDb);
				Set<Long> adminIds = new HashSet<>();
				permissionInDb.getRoles().stream().forEach(role->{
					role.getAdmins().stream().forEach(admin->{
						adminIds.add(admin.getId());
					});
				});

				List<sq.bean.Admin> redisAdmins = new ArrayList<>();
				adminIds.stream().forEach(adminId->{
					Admin findAdmin = adminService.findById(adminId);
					sq.bean.Admin redisAdmin= copyToRedisAdmin(findAdmin);
					redisAdmins.add(redisAdmin);
				});
				PermissionUtil.getInstance(redisTemplate).updatePermission(redisAdmins);

			} catch (Exception e) {
				result.setMessage(SysConstants.OPERATION_FAIL);
				return result;
			}
		} else {
			result.setTotal(1);
			result.setSuccess(false);
			result.setMessage(RestConstants.EXIST_PERMISSION);
			return result;
		}

		// result.setData(permissionInDb);
		result.setTotal(1);
		result.setSuccess(true);
		result.setMessage(SysConstants.OPERATION_SUCCESS);
		return result;
	}

	@GetMapping("/{id}")
	@ApiOperation(value = "获取单个权限（后台）")
	public AppResult<Permission> findById(@RequestHeader("x-access-token") final String token,
			@PathVariable final long id) {
		final AppResult<Permission> result = new AppResult<>();

		// 判断admin登录
		final AppResult<Admin> adminResult = this.adminController.getAdmin(token);
		if (!adminResult.isSuccess()) {
			result.setMessage(RestConstants.NO_ADMIN);
			return result;
		}
		if (!this.adminService.hasPermission(adminResult.getData(), "permission:list")) {
			result.setMessage(RestConstants.NO_PERMISSION);
			return result;
		}
		// 业务逻辑判断
		final Permission permissionInDb = this.permissionService.findById(id);
		if (permissionInDb == null) {
			result.setMessage(RestConstants.NO_RESULT);
			return result;
		}
		result.setMessage(RestConstants.FIND_SUCCESS);
		result.setData(permissionInDb);
		result.setTotal(1);
		result.setSuccess(true);
		return result;
	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "删除权限（后台）")
	public AppResult<Permission> deleteById(@RequestHeader("x-access-token") final String token,
			@PathVariable final long id) {
		final AppResult<Permission> result = new AppResult<>();

		// 判断admin登录
		final AppResult<Admin> adminResult = this.adminController.getAdmin(token);
		if (!adminResult.isSuccess()) {
			result.setMessage(RestConstants.NO_ADMIN);
			return result;
		}
		// 判断是否有权限
		if (!this.adminService.hasPermission(adminResult.getData(), "permission:delete")) {
			result.setMessage(RestConstants.NO_PERMISSION);
			return result;
		}
		// 业务逻辑判断
		final Permission permissionInDb = this.permissionService.findById(id);
		if (permissionInDb == null) {
			result.setMessage(RestConstants.NO_RESULT);
			return result;
		}
		Set<Long> adminIds = new HashSet<>();
		permissionInDb.getRoles().stream().forEach(role->{
			role.getAdmins().stream().forEach(admin->{
				adminIds.add(admin.getId());
			});
		});
		this.permissionService.delete(permissionInDb);


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
