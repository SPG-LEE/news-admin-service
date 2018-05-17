package sq.news.admin.controller;

import sq.base.AppResult;
import sq.bean.Permission;
import sq.news.admin.constants.SysConstants;
import sq.news.admin.entity.Role;
import sq.news.admin.service.AdminService;
import sq.news.admin.service.RoleService;
import sq.news.admin.constants.RestConstants;
import sq.enumeration.EntityStatus;
import sq.news.admin.entity.Admin;
import sq.util.*;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(path = "/admins")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @Autowired
    private RoleService roleService;
    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/auth")
    @ApiIgnore
    public AppResult<Admin> getAdmin(@RequestHeader("x-access-token") final
                                     String token) {

        final AppResult<Admin> result = new AppResult<>();
        System.out.println("-------------------------");
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        HashOperations<String, Object, Object> hash = redisTemplate
                .opsForHash();
        String redisToken = ops.get(token);
        if (redisToken == null) {
            return AppResultBuilder.buildFailedMessageResult(RestConstants
                    .NO_TOKEN);
        }
        Object redisAdmin = hash.get("ADMIN", redisToken);
        if (redisAdmin == null) {
            return AppResultBuilder.buildFailedMessageResult(RestConstants
                    .NO_TOKEN);
        }
        redisTemplate.expire(token, Integer.parseInt(SysConstants
                .ADMIN_TOKEN_DEADLINE), TimeUnit.DAYS);
        redisTemplate.expire("ADMIN", Integer.parseInt(SysConstants
                .ADMIN_TOKEN_DEADLINE), TimeUnit.DAYS);
        String loginName = null;
        try {
            loginName = Jwts.parser().setSigningKey(SysConstants.TOKEN_KEY)
                    .parseClaimsJws(redisToken).getBody()
                    .getSubject();
        } catch (final Exception e) {
            e.printStackTrace();
            return AppResultBuilder.buildFailedMessageResult(RestConstants
                    .LOGIN_UNUSUAL);
        }

        if (loginName == null || loginName.trim().isEmpty()) {
            result.setSuccess(false);
            result.setMessage(RestConstants.NO_TOKEN);
            return result;
        }

        final Admin admin = this.adminService.findByLoginName(loginName);
        if (admin != null) {
            System.out.println(admin + "name:" + admin.getLoginName());
        }
        if (admin == null) {
            result.setSuccess(false);
            result.setMessage(RestConstants.NO_ADMIN);
            return result;
        }
        return AppResultBuilder.buildSuccessMessageResult(admin,
                RestConstants.FIND_SUCCESS);
    }

    @GetMapping
    @ApiOperation(value = "获取管理员列表(后台)")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name =
            "x-access-token", value = "管理员token"),
            @ApiImplicitParam(paramType = "query", name = "pageIndex", value
                    = "当前页", defaultValue = "0"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value =
                    "列表显示条数", defaultValue = "10"),
            @ApiImplicitParam(paramType = "query", name = "orderByField",
                    value = "根据字段排序", required = false),
            @ApiImplicitParam(paramType = "query", name = "pageNoLimit",
                    value = "是否分页", defaultValue = "false"),
            @ApiImplicitParam(paramType = "query", name = "search", value =
                    "搜索信息（admin名）", required = false)})
    public AppResult<List<Admin>> findAll(@RequestHeader("x-access-token")
                                              final String token,
                                          @RequestParam(defaultValue = "0")
                                          final String pageIndex,
                                          @RequestParam(defaultValue = "10")
                                              final String pageSize,
                                          @RequestParam(required = false)
                                              final String orderByField,
                                          @RequestParam(defaultValue =
                                                  "false") final String
                                                  pageNoLimit,
                                          @RequestParam(required = false)
                                              final String search) {
        // 判断admin登录
        final AppResult<Admin> adminResult = getAdmin(token);
        if (!adminResult.isSuccess()) {
            return AppResultBuilder.buildFailedMessageResult(RestConstants
                    .NO_TOKEN);
        }
        // 判断是否有权限
        final Admin tokenAdmin = this.adminService.findByLoginName
                (adminResult.getData().getLoginName());
        if (tokenAdmin == null) {
            return AppResultBuilder.buildFailedMessageResult(RestConstants
                    .NO_ADMIN);
        }
        if (!adminService.hasPermission(tokenAdmin, "admin:list")) {
            return AppResultBuilder.buildFailedMessageResult(RestConstants
                    .NO_PERMISSION);
        }
        try {
            final List<Admin> admins = this.adminService.findAllAdmins
                    (pageIndex, pageSize, orderByField, pageNoLimit,
                            search);
            admins.stream().forEach(admin -> {
                admin.setPassword(DESPlus.getDefault().decrypt(admin
                        .getPassword()));
            });
            final long count;
            if (!FormatUtil.isNullOrEmpty(search)) {
                count = this.adminService.countByNameLike(search);
            } else {
                count = this.adminService.allCount();
            }

            return AppResultBuilder.buildSuccessMessageResult(admins,
                    RestConstants.FIND_SUCCESS, count);

        } catch (final Exception e) {
            return AppResultBuilder.buildFailedMessageResult(SysConstants
                    .PARAM_UNLEGAL);
        }
    }

    @GetMapping("/login")
    @ApiOperation(value = "登录（后台）")
    public AppResult<JSONObject> login(@RequestParam final String loginName,
                                       @RequestParam final String password,
                                       HttpServletResponse response) {
        final AppResult<JSONObject> appResult = new AppResult<>();

        final Admin dbAdmin = this.adminService.findByLoginName(loginName);
        if (dbAdmin == null) {
            appResult.setMessage(RestConstants.NO_ADMIN);
            return appResult;
        }
        if (dbAdmin.getEntityStatus().equals(EntityStatus.DISABLE)) {
            return AppResultBuilder.buildFailedMessageResult(RestConstants
                    .DISABLE_LOGINNAME);
        }
        if (!dbAdmin.getPassword().equals(Md5Util.MD5(password))) {
            appResult.setMessage(RestConstants.ERROR_PASSWORD);
            return appResult;
        }
        dbAdmin.setLastLoginDate(new Date());
        this.adminService.merge(dbAdmin);

        appResult.setSuccess(true);
        appResult.setMessage(SysConstants.OPERATION_SUCCESS);
        final String token = Jwts.builder().setSubject(loginName + "," +
                System.currentTimeMillis())
                .signWith(SignatureAlgorithm.HS512, SysConstants.TOKEN_KEY)
                .compact();
        try {
            String tokenBill = Jwts.builder().setSubject(loginName)
                    .signWith(SignatureAlgorithm.HS512, SysConstants
                            .TOKEN_KEY).compact();
            sq.bean.Admin admin = copyToRedisAdmin(dbAdmin);
            saveTokenAndTimeOut(token, tokenBill, admin);// redis保存token设置过期时间
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", token);
        jsonObject.put("name", dbAdmin.getName());
        jsonObject.put("id", dbAdmin.getId());
        appResult.setData(jsonObject);
        return appResult;
    }

    public static void main(String[] args) {
        String result =  Jwts.parser().setSigningKey(SysConstants.TOKEN_KEY)
                .parseClaimsJws("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0MSJ9.49SPbdSGqiYgQJKObDqRbr97gLCgIkeRoYdiMer_VCmR389m3NI0yHhnrZ9kezQTaUAzJ2mC6BREtuhlAYkXjw").getBody()
                .getSubject();
        System.out.println(result);
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
        Set<Permission> permissions = new HashSet<>();
        Set<sq.bean.Role> roles = new HashSet<>();
        dbAdmin.getRoles().stream().forEach(role -> {
            sq.bean.Role redisRole = new sq.bean.Role();
            redisRole.setId(role.getId());
            redisRole.setRoleName(role.getRoleName());
            redisRole.setDescription(role.getDescription());
            redisRole.setName(role.getName());
            roles.add(redisRole);
            role.getPermissions().stream().forEach(permission -> {
                Permission redisPermission = new Permission();
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

    private void saveTokenAndTimeOut(String token, String tokenBill, sq
            .bean.Admin
            admin) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        HashOperations<String, Object, Object> hash = redisTemplate
                .opsForHash();
        ops.set(token, tokenBill);
        hash.put("ADMIN", tokenBill, admin);
        redisTemplate.expire(token, Integer.parseInt(SysConstants
                .ADMIN_TOKEN_DEADLINE), TimeUnit.DAYS);
        redisTemplate.expire("ADMIN", Integer.parseInt(SysConstants
                .ADMIN_TOKEN_DEADLINE), TimeUnit.DAYS);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "获取单个管理员（后台）")
    public AppResult<Admin> findById(@RequestHeader("x-access-token") final
                                     String token, @PathVariable final
                                     long id) {
        final AppResult<Admin> result = new AppResult<>();

        // 判断admin登录
        final AppResult<Admin> adminResult = getAdmin(token);
        if (!adminResult.isSuccess()) {
            result.setMessage(RestConstants.NO_TOKEN);
            return result;
        }
        // 判断是否有权限
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        String tokenBill = ops.get(token);
        final String loginName = Jwts.parser().setSigningKey(SysConstants
                .TOKEN_KEY).parseClaimsJws(tokenBill).getBody()
                .getSubject();
        final Admin tokenAdmin = this.adminService.findByLoginName(loginName);
        if (tokenAdmin == null) {
            return AppResultBuilder.buildFailedMessageResult(RestConstants
                    .NO_ADMIN);
        }
        if (!adminService.hasPermission(tokenAdmin, "admin:edit")) {
            return AppResultBuilder.buildFailedMessageResult(RestConstants
                    .NO_PERMISSION);
        }
        // 业务逻辑判断
        final Admin adminInDb = this.adminService.findById(id);
        if (adminInDb == null) {
            result.setMessage(RestConstants.NO_RESULT);
            return result;
        }
        result.setData(adminInDb);
        result.setTotal(1);
        result.setSuccess(true);
        result.setMessage(RestConstants.FIND_SUCCESS);
        return result;
    }

    @PostMapping
    @ApiOperation(value = "创建管理员（后台）")
    public AppResult<Admin> save(@RequestHeader("x-access-token") final
                                 String token, @RequestBody final Admin
                                         admin) {
        final AppResult<Admin> appResult = new AppResult<>();

        if (StringUtils.isEmpty(token)) {
            appResult.setSuccess(false);
            appResult.setMessage(RestConstants.NO_LOGIN);
            return appResult;
        }
        if (!admin.getPassword().matches(SysConstants.PASSWORD_ZHENGZE)) {
            appResult.setSuccess(false);
            appResult.setMessage(RestConstants.PASSWORD_UNABLE);
            return appResult;
        }
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        String tokenBill = ops.get(token);
        String loginName = Jwts.parser().setSigningKey(SysConstants
                .TOKEN_KEY).parseClaimsJws(tokenBill).getBody()
                .getSubject();
        final Admin tokenAdmin = this.adminService.findByLoginName(loginName);
        if (tokenAdmin == null) {
            return AppResultBuilder.buildFailedMessageResult(RestConstants
                    .NO_ADMIN);
        }
        if (!adminService.hasPermission(tokenAdmin, "admin:edit")) {
            return AppResultBuilder.buildFailedMessageResult(RestConstants
                    .NO_PERMISSION);
        }
        Admin dbAdmin = this.adminService.findByLoginName(admin.getLoginName());
        if (loginName != null && !loginName.trim().isEmpty()) {
            if (admin.getLoginName().trim().equals(loginName)) {
                appResult.setSuccess(false);
                appResult.setMessage(RestConstants.UNABLE_EDIT);
                return appResult;
            } else {
                if (FormatUtil.isNullOrEmpty(admin.getName()) || FormatUtil
                        .isNullOrEmpty(admin.getLoginName())) {
                    appResult.setSuccess(false);
                    appResult.setMessage(SysConstants.PARAM_UNLEGAL);
                    return appResult;
                }
                if (dbAdmin != null) {
                    appResult.setSuccess(false);
                    appResult.setMessage(RestConstants.EXIST_LOGIN);
                    return appResult;
                } else {

                    this.adminService.saveAndRalation(admin);

                    appResult.setSuccess(true);
                    appResult.setMessage(SysConstants.OPERATION_SUCCESS);
                    appResult.setData(null);
                    appResult.setTotal(1);
                }

            }
        } else {
            appResult.setSuccess(false);
            appResult.setMessage(RestConstants.NO_TOKEN);
            return appResult;
        }

        return appResult;
    }

    @PostMapping("/create")
    @ApiIgnore
    public AppResult<Admin> create(@RequestBody final Admin admin) {
        final AppResult<Admin> appResult = new AppResult<>();
        final String loginName = admin.getLoginName();

        if (loginName != null && !loginName.trim().isEmpty()) {
            final Admin dbAdmin = this.adminService.findByLoginName(admin
                    .getLoginName());
            if (dbAdmin != null) {
                appResult.setSuccess(false);
                appResult.setMessage(RestConstants.EXIST_LOGIN);
            } else {
                admin.setPassword(DESPlus.getDefault().encrypt(admin
                        .getPassword()));
                this.adminService.save(admin);
                appResult.setSuccess(true);
                appResult.setMessage(SysConstants.OPERATION_SUCCESS);
                appResult.setData(null);
                appResult.setTotal(1);
            }
        } else {
            appResult.setSuccess(false);
            appResult.setMessage(RestConstants.NO_TOKEN);
        }

        return appResult;
    }

    @PostMapping("/{id}")
    @ApiIgnore
    public AppResult<Admin> update(@RequestHeader("x-access-token") final
                                   String token, @PathVariable final long
                                           id,
                                   @RequestBody final Admin admin) {
        final AppResult<Admin> appResult = new AppResult<>();

        if (StringUtils.isEmpty(token)) {
            appResult.setSuccess(false);
            appResult.setMessage(RestConstants.NO_LOGIN);
            return appResult;
        }
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        String tokenBill = ops.get(token);
        final String loginName = Jwts.parser().setSigningKey(SysConstants
                .TOKEN_KEY).parseClaimsJws(tokenBill).getBody()
                .getSubject();
        final Admin tokenAdmin = this.adminService.findByLoginName(loginName);
        if (tokenAdmin == null) {
            return AppResultBuilder.buildFailedMessageResult(RestConstants
                    .NO_ADMIN);
        }
        if (!adminService.hasPermission(tokenAdmin, "admin:edit")) {
            return AppResultBuilder.buildFailedMessageResult(RestConstants
                    .NO_PERMISSION);
        }
        final Admin findAdmin = this.adminService.findById(id);
        if (findAdmin != null) {
            if (admin.getName() != null && admin.getName().equals(loginName)) {
                appResult.setSuccess(false);
                appResult.setMessage(RestConstants.EXIST_LOGIN);
            } else {
                if (admin.getName() != null) {
                    findAdmin.setName(admin.getName());
                }
                findAdmin.setLoginName(admin.getLoginName());
                findAdmin.setPassword(Md5Util.MD5(admin.getPassword()));
                if (admin.getLastLoginDate() != null) {
                    findAdmin.setLastLoginDate(admin.getLastLoginDate());
                }

                this.adminService.merge(findAdmin);

                appResult.setSuccess(true);
                appResult.setMessage(SysConstants.OPERATION_SUCCESS);
                appResult.setData(null);
            }
        } else {
            appResult.setSuccess(false);
            appResult.setMessage(RestConstants.FIND_ERROR);
        }
        return appResult;
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除单个管理员（后台）")
    public AppResult<Admin> delete(@RequestHeader("x-access-token") final
                                   String token, @PathVariable final long
                                           id) {
        final AppResult<Admin> appResult = new AppResult<>();

        if (StringUtils.isEmpty(token)) {
            appResult.setSuccess(false);
            appResult.setMessage(RestConstants.NO_LOGIN);
            return appResult;
        }
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        String tokenBill = ops.get(token);
        final String loginName = Jwts.parser().setSigningKey(SysConstants
                .TOKEN_KEY).parseClaimsJws(tokenBill).getBody()
                .getSubject();

        final Admin tokenAdmin = this.adminService.findByLoginName(loginName);
        if (tokenAdmin == null) {
            return AppResultBuilder.buildFailedMessageResult(RestConstants
                    .NO_ADMIN);
        }
        if (!adminService.hasPermission(tokenAdmin, "admin:delete")) {
            return AppResultBuilder.buildFailedMessageResult(RestConstants
                    .NO_PERMISSION);
        }
        final Admin deleteAdmin = this.adminService.findById(id);
        if (deleteAdmin != null) {
            if (adminService.isSuperAdmin(id)) {
                return AppResultBuilder.buildFailedMessageResult
                        (RestConstants.UNABLE_EDIT);
            }
            if (deleteAdmin.getLoginName().equals(loginName)) {
                appResult.setSuccess(false);
                appResult.setMessage(RestConstants.UNABLE_EDIT);
            } else {
                String adminToken = Jwts.builder().setSubject(deleteAdmin
                        .getLoginName())
                        .signWith(SignatureAlgorithm.HS512, SysConstants
                                .TOKEN_KEY).compact();
                redisTemplate.expire(adminToken, 0, TimeUnit.DAYS);
                this.adminService.delete(deleteAdmin);
                appResult.setSuccess(true);
                appResult.setMessage(SysConstants.OPERATION_SUCCESS);
                appResult.setTotal(1);
            }
        } else {
            appResult.setSuccess(false);
            appResult.setMessage(RestConstants.FIND_ERROR);
        }
        return appResult;
    }

    @PostMapping("/{id}/roles")
    @ApiOperation(value = "为用户设定角色（后台）")
    @ApiImplicitParams({@ApiImplicitParam(name = "jsonObject", value =
            "参数结构为{'data':[{'id':''},{'id':''}]}")})
    public AppResult<Admin> setRoles(@RequestHeader("x-access-token") final
                                     String token, @PathVariable final
                                     long id,
                                     @RequestBody JSONObject jsonObject) {
        final AppResult<Admin> appResult = new AppResult<>();

        if (StringUtils.isEmpty(token)) {
            appResult.setSuccess(false);
            appResult.setMessage(RestConstants.NO_LOGIN);
            return appResult;
        }
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        String tokenBill = ops.get(token);
        final String loginName = Jwts.parser().setSigningKey(SysConstants
                .TOKEN_KEY).parseClaimsJws(tokenBill).getBody()
                .getSubject();
        final Admin tokenAdmin = this.adminService.findByLoginName(loginName);
        if (tokenAdmin == null) {
            return AppResultBuilder.buildFailedMessageResult(RestConstants
                    .NO_ADMIN);
        }
        if (!adminService.hasPermission(tokenAdmin, "admin:edit")) {
            return AppResultBuilder.buildFailedMessageResult(RestConstants
                    .NO_PERMISSION);
        }
        final Admin findAdmin = this.adminService.findById(id);
        if (findAdmin != null) {
            Set<Role> adminsRoles = new CopyOnWriteArraySet();
            JSONArray a = jsonObject.getJSONArray("data");

            for (int i = 0; i < a.size(); i++) {
                final JSONObject json = JSONObject.parseObject(a.getString(i));
                json.getString("id");
                final Role role = this.roleService.findById(Long.parseLong
                        (json.getString("id")));
                if (role != null) {
                    this.roleService.merge(role);
                    adminsRoles.add(role);
                } else {
                    appResult.setSuccess(false);
                    appResult.setMessage(RestConstants.NO_ROLE);
                    return appResult;
                }
            }
            findAdmin.setRoles(adminsRoles);
            this.adminService.merge(findAdmin);
            sq.bean.Admin redisAdmin = copyToRedisAdmin(findAdmin);
            List<sq.bean.Admin> redisAdmins = new ArrayList<>();
            redisAdmins.add(redisAdmin);
            PermissionUtil.getInstance(redisTemplate).updatePermission
                    (redisAdmins);
            appResult.setData(null);
            appResult.setSuccess(true);
            appResult.setMessage(RestConstants.SAVE_SUCCESS);
            appResult.setTotal(1);
        } else {
            appResult.setSuccess(false);
            appResult.setMessage(RestConstants.FIND_ERROR);
        }
        return appResult;
    }

    @GetMapping("/hasPermission/{permission}")
    @ApiIgnore
    public AppResult<Boolean> hasPermission(@RequestHeader("x-access-token")
                                                final String token,
                                            @PathVariable final String
                                                    permission) {
        if (StringUtils.isEmpty(token)) {
            return AppResultBuilder.buildFailedMessageResult(RestConstants
                    .NO_LOGIN);
        }
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        String tokenBill = ops.get(token);
        if (tokenBill == null) {
            return AppResultBuilder.buildFailedMessageResult(RestConstants
                    .NO_TOKEN);
        }
        final String loginName = Jwts.parser().setSigningKey(SysConstants
                .TOKEN_KEY).parseClaimsJws(tokenBill).getBody()
                .getSubject();
        System.out.println("loginName= " + loginName);

        if (loginName == null || loginName.trim().isEmpty()) {
            return AppResultBuilder.buildFailedMessageResult(false,
                    RestConstants.NO_LOGINNAME);
        }

        final Admin admin = this.adminService.findByLoginName(loginName);
        if (admin == null) {
            return AppResultBuilder.buildFailedMessageResult(false,
                    RestConstants.NO_ADMIN);
        }
        if (admin.getRoles().stream().filter(filter -> filter.getRoleName()
                .equals(SysConstants.SUPERADMIN)).count() > 0) {
            System.out.println("超级管理员:" + admin.getLoginName() + ",检查权限成功！");
            return AppResultBuilder.buildSuccessMessageResult(true,
                    SysConstants.OPERATION_SUCCESS);
        }
        if (this.adminService.hasPermission(admin, permission)) {
            System.out.println("管理员:" + admin.getLoginName() + ",检查权限成功！权限:"
                    + permission);
            return AppResultBuilder.buildSuccessMessageResult(true,
                    SysConstants.OPERATION_SUCCESS);
        } else {
            return AppResultBuilder.buildFailedMessageResult(false,
                    RestConstants.NO_PERMISSION);
        }
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "修改管理员（后台）")
    public AppResult<Admin> merge(@RequestHeader("x-access-token") final
                                  String token, @RequestBody Admin admin,
                                  @PathVariable("id") String id) {
        if (StringUtils.isEmpty(token)) {
            return AppResultBuilder.buildFailedMessageResult(RestConstants
                    .NO_LOGIN);
        }
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        String tokenBill = ops.get(token);
        final String loginName = Jwts.parser().setSigningKey(SysConstants
                .TOKEN_KEY).parseClaimsJws(tokenBill).getBody()
                .getSubject();
        final Admin tokenAdmin = this.adminService.findByLoginName(loginName);
        if (tokenAdmin == null) {
            return AppResultBuilder.buildFailedMessageResult(RestConstants
                    .NO_ADMIN);
        }
        Admin adminInDb = adminService.findById(Long.parseLong(id));
        if (!adminService.isSuperAdmin(tokenAdmin.getId()) && !id.equals
                (adminInDb.getId() + "")) {
            return AppResultBuilder.buildFailedMessageResult(SysConstants
                    .PARAM_UNLEGAL);
        }
        if (!adminService.hasPermission(tokenAdmin, "admin:edit")) {
            return AppResultBuilder.buildFailedMessageResult(RestConstants
                    .NO_PERMISSION);
        }
        StringWriter message = new StringWriter();

        try {

            if (adminInDb.getLoginName() != null) {
                if (adminService.isSuperAdmin(admin.getId())) {
                    if (!FormatUtil.isNullOrEmpty(admin.getPassword())) {
                        adminInDb.setPassword(Md5Util.MD5(admin.getPassword()));
                        adminService.save(adminInDb);
                    }
                    return AppResultBuilder.buildSuccessMessageResult
                            (SysConstants.OPERATION_SUCCESS);
                }
            }
            if (!admin.getLoginName().equals(adminInDb.getLoginName())
                    && adminService.findByLoginName(admin.getLoginName()) !=
                    null) {
                return AppResultBuilder.buildFailedMessageResult
                        (RestConstants.EXIST_LOGIN);
            }
            if (adminInDb != null) {
                adminService.updateAndRoleRalation(admin, adminInDb);
            }
            sq.bean.Admin redisAdmin = copyToRedisAdmin(adminInDb);
            List<sq.bean.Admin> redisAdmins = new ArrayList<>();
            redisAdmins.add(redisAdmin);
            PermissionUtil.getInstance(redisTemplate).updatePermission
                    (redisAdmins);
        } catch (Exception e) {
            e.printStackTrace(new PrintWriter(message));
        }
        return AppResultBuilder.buildSuccessMessageResult(SysConstants
                .OPERATION_SUCCESS);
    }

    // @GetMapping("/cache")
    public String cache() {
        System.out.println("cach");
        AppResult<String> result = new AppResult<>();
        ValueOperations<String, String> ops = redisTemplate.opsForValue();

        ops.set("test" + new Date().getTime(), "value" + new Date().getTime()
        );// 1分钟过期
        ops.set("hello", "world");
        redisTemplate.expire("hello", 0, TimeUnit.SECONDS);

        String message = ops.get("hello");

        System.out.println("=============================");
        System.out.println(message +
                "========================================");

        result.setSuccess(true);
        result.setMessage(SysConstants.OPERATION_SUCCESS);
        return JsonUtil.toJson(result);
    }

    // @GetMapping("/cacheResult")
    public void getCache() {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        String message = ops.get(
                "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiJ9.ylWqzV1tVdH23JbqYCj7sI9j9zNUFqWZjyb0woYk78hwNnH5vgGObma4ugG_wK-sdJ2c_lbT7yUY3ARFvhHc4A");
        System.out.println(message + "?????????????????????????");

    }


    @GetMapping("/logout")
    @ApiOperation(value = "退出账户（后台）")
    public AppResult<String> layout(@RequestHeader("x-access-token") final
                                    String token) {
        // 判断admin登录
        AppResult<String> result = new AppResult<String>();
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        String redisToken = ops.get(token);
        if (redisToken == null) {// 判断token是否过期
            result.setMessage(RestConstants.NO_TOKEN);
            result.setSuccess(false);
            return result;
        }
        redisTemplate.expire(token, 0, TimeUnit.DAYS);//
        result.setSuccess(true);
        result.setMessage(SysConstants.OPERATION_SUCCESS);
        result.setData(SysConstants.OPERATION_SUCCESS);
        return result;
    }

    @PostMapping("/init")
    public AppResult<Boolean> init(@RequestParam String password) {
        if (!password.equals("Sq888")) {
            return AppResultBuilder.buildFailedMessageResult(SysConstants
                    .OPERATION_FAIL);
        }
        Admin superAdmin = adminService.findByLoginName(SysConstants.SUPERADMIN);
        if (superAdmin == null) {
            superAdmin = new Admin();
            superAdmin.setLoginName(SysConstants.SUPERADMIN);
            superAdmin.setPassword(Md5Util.MD5(SysConstants.SUPERADMIN));
            superAdmin.setDescription(SysConstants.SUPERADMIN);
            superAdmin.setName(SysConstants.SUPERADMIN);
        }
        Role superAdminRole = roleService.findByRoleName(SysConstants.SUPERADMIN);
        Set<Role> roles = new HashSet<>();
        if (superAdminRole == null) {
            superAdminRole = new Role();
            superAdminRole.setRoleName(SysConstants.SUPERADMIN);
            superAdminRole.setName(SysConstants.SUPERADMIN);
            roleService.save(superAdminRole);
        }
        roles.add(superAdminRole);
        superAdmin.setRoles(roles);
        this.adminService.save(superAdmin);
        return AppResultBuilder.buildSuccessMessageResult(true, SysConstants
                .OPERATION_SUCCESS);
    }


    @PutMapping("/forceDownLine")
    @ApiOperation(value = "退出账户（后台）")
    @ApiIgnore
    public AppResult<Admin> logoutAdmins(@RequestHeader("x-access-token")
                                             final String token,
                                         @RequestBody List<Admin> admins) {
        AppResult<Admin> adminResult = getAdmin(token);

        if (!adminResult.isSuccess()) {
            return AppResultBuilder.buildFailedMessageResult(RestConstants
                    .NO_ADMIN);
        }

        // 判断是否有权限
        if (!this.adminService.hasPermission(adminResult.getData(),
                "admin:edit")) {
            return AppResultBuilder.buildFailedMessageResult(RestConstants
                    .NO_PERMISSION);

        }

        for (

                Admin admin : admins) {
            if (adminService.isSuperAdmin(admin.getId())) {
                return AppResultBuilder.buildFailedMessageResult
                        (RestConstants.UNABLE_EDIT);
            }
            if (adminService.findByLoginName(admin.getLoginName()) == null) {
                return AppResultBuilder.buildFailedMessageResult
                        (RestConstants.NO_RESULT);

            }
            String tokenBill = Jwts.builder().setSubject(admin.getLoginName())
                    .signWith(SignatureAlgorithm.HS512, SysConstants
                            .TOKEN_KEY).compact();
            redisTemplate.delete(tokenBill);
        }
        return AppResultBuilder.buildSuccessMessageResult(SysConstants
                .OPERATION_SUCCESS);
    }
}
