package sq.bean;

import sq.base.BaseEntity;
import sq.base.WithName;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE,
        getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility
        = JsonAutoDetect.Visibility.NONE)
public class Admin extends BaseEntity implements WithName {

    private static final long serialVersionUID = 1214518718746497616L;
    public static final String NAME = "管理员";
    @JsonProperty
    private String name;// 管理员昵称
    @JsonProperty
    private String loginName;// 登录名

    @JsonProperty
    private String password;// 登录密码

    @JsonProperty
    private Date lastLoginDate;// 最近一次登录时间

    @JsonProperty
    private Date loginDate;// 登录时间

    @JsonProperty
    private String description;// 描述

    @JsonProperty
    private Set<Permission> permissions = new HashSet<>();// 权限

    @JsonProperty
    private Set<Role> roles = new HashSet<>();// 角色

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
