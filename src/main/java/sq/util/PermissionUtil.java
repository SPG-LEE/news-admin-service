package sq.util;

import sq.bean.Admin;
import sq.bean.Permission;
import sq.bean.Role;
import sq.news.admin.constants.PermissionConstants;
import sq.news.admin.constants.SysConstants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class PermissionUtil {
    private RedisTemplate redisTemplate;
    private static PermissionUtil cur;
    private final static String ADMIN_KEY = "ADMIN";

    public static PermissionUtil getInstance(RedisTemplate redisTemplate) {
        if (cur == null) {
            cur = new PermissionUtil(redisTemplate);
        }
        return cur;
    }

    private PermissionUtil(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public String createScheduleToken() {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        final String token = Jwts.builder().setSubject(PermissionConstants
                .SCHEDULE + "," +
                System.currentTimeMillis())
                .signWith(SignatureAlgorithm.HS512, SysConstants.TOKEN_KEY)
                .compact();
        String redisToken = Jwts.builder().setSubject(PermissionConstants
                .SCHEDULE)
                .signWith(SignatureAlgorithm.HS512, SysConstants
                        .TOKEN_KEY).compact();
        ops.set(token, redisToken);
        ops.set(redisToken, token);
        redisTemplate.expire(token, 1, TimeUnit.DAYS);
        redisTemplate.expire(redisToken, 1, TimeUnit.DAYS);
        return token;
    }

    public Admin getAdmin(String token) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        HashOperations<String, Object, Object> hash = redisTemplate
                .opsForHash();

        String redisToken = ops.get(token);
        if (redisToken == null) {
            return null;
        }
        Admin whiteList = whiteList(token, redisToken);
        if (whiteList != null) return whiteList;
        Admin admin = (Admin) hash.get(ADMIN_KEY, redisToken);
        if (admin == null) {
            return null;
        }
        redisTemplate.expire(token, Integer.parseInt(SysConstants
                .ADMIN_TOKEN_DEADLINE), TimeUnit.DAYS);
        redisTemplate.expire(redisToken, Integer.parseInt(SysConstants
                .ADMIN_TOKEN_DEADLINE), TimeUnit.DAYS);
        return admin;
    }

    private Admin whiteList(String token, String redisToken) {
        String key = Jwts.builder().setSubject(PermissionConstants
                .SCHEDULE)
                .signWith(SignatureAlgorithm.HS512, SysConstants
                        .TOKEN_KEY).compact();
        if (redisToken.equals(key)) {
            redisTemplate.expire(token, Integer.parseInt(SysConstants
                    .ADMIN_TOKEN_DEADLINE), TimeUnit.DAYS);
            redisTemplate.expire(redisToken, Integer.parseInt(SysConstants
                    .ADMIN_TOKEN_DEADLINE), TimeUnit.DAYS);
            Admin admin = new Admin();
            admin.setName(PermissionConstants.SCHEDULE);
            admin.setLoginName(PermissionConstants.SCHEDULE);
            return admin;
        }
        return null;
    }

    public boolean hasPermission(String token, String permission) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        HashOperations<String, Object, Object> hash = redisTemplate
                .opsForHash();
        String redisToken = ops.get(token);
        if (redisToken == null) {
            return false;
        }
        Admin whiteList = whiteList(token, redisToken);
        if (whiteList != null) return true;
        Admin admin = (Admin) hash.get(ADMIN_KEY, redisToken);
        if (admin == null) {
            return false;
        }
        List<String> roleList = admin.getRoles().stream().map
                (Role::getRoleName).collect(Collectors.toList());
        if (roleList.contains(SysConstants.SUPERADMIN)) {
            return true;
        }
        List<String> permissionList = admin.getPermissions().stream().map
                (Permission::getPermission).collect(Collectors.toList());
        return permissionList.contains(permission);
    }

    public void updatePermission(List<Admin> admins) {
        HashOperations<String, Object, Object> hash = redisTemplate
                .opsForHash();
        Map<String, Admin> redisAdmins = (Map) hash.entries(ADMIN_KEY);
        admins.stream().forEach(admin -> {
            String key = Jwts.builder().setSubject(admin.getLoginName())
                    .signWith(SignatureAlgorithm.HS512, SysConstants
                            .TOKEN_KEY).compact();
            Admin newAdmin = redisAdmins.get(key);
            if (newAdmin != null) {
                hash.put(ADMIN_KEY, key, admin);
            }
        });
    }
}
