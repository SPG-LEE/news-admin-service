package sq.news.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sq.base.AppResult;
import sq.news.admin.service.AdminLogService;
import sq.news.admin.service.AdminService;
import sq.news.admin.constants.RestConstants;
import sq.news.admin.constants.SysConstants;
import sq.news.admin.entity.Admin;
import sq.news.admin.entity.AdminLog;
import sq.util.FormatUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(path = "/admins/logs")
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
public class AdminLogController {

	@Autowired
	private AdminController adminsController;

	@Autowired
	private AdminLogService adminLogService;

	@Autowired
	private AdminService adminService;
	@Autowired
	private RedisTemplate redisTemplate;

	@GetMapping
	@ApiOperation(value = "获取日志列表（后台）")
	@ApiImplicitParams({ @ApiImplicitParam(paramType = "header", name = "x-access-token", value = "管理员token"),
			@ApiImplicitParam(paramType = "query", name = "pageIndex", value = "当前页", defaultValue = "0"),
			@ApiImplicitParam(paramType = "query", name = "pageSize", value = "列表显示条数", defaultValue = "10"),
			@ApiImplicitParam(paramType = "query", name = "linkId", value = "关联的对象id") })
	public AppResult<List<AdminLog>> findAll(@RequestHeader("x-access-token") final String token,
			@RequestParam(defaultValue = "0") final int pageIndex,
			@RequestParam(defaultValue = "10") final int pageSize, @RequestParam final String linkId) {
		final AppResult<List<AdminLog>> result = new AppResult<>();
		// 判断admin登录

		final AppResult<Admin> adminResult = this.adminsController.getAdmin(token);
		if (!adminResult.isSuccess()) {
			result.setMessage(RestConstants.NO_ADMIN);
			return result;
		}
		// 判断是否有权限
		if (!this.adminService.hasPermission(adminResult.getData(), "adminLog:list")) {
			result.setMessage(RestConstants.NO_PERMISSION);
			return result;
		}
		try {
			final List<AdminLog> adminLogs;
			final long count;
			if (!FormatUtil.isNullOrEmpty(linkId)) {
				adminLogs = this.adminLogService.findByLinkId(linkId, pageIndex * pageSize, pageSize);
				count = this.adminLogService.getCountByLinkId(linkId);
			} else {
				adminLogs = this.adminLogService.findAll(pageIndex * pageSize, pageSize);
				count = this.adminLogService.getAllCount();
			}
			result.setSuccess(true);
			result.setMessage(SysConstants.OPERATION_SUCCESS);
			result.setData(adminLogs);
			result.setTotal(count);
			return result;
		} catch (final Exception e) {
			result.setSuccess(false);
			result.setMessage(SysConstants.PARAM_UNLEGAL);
			result.setTotal(0);
			return result;
		}

	}

}
