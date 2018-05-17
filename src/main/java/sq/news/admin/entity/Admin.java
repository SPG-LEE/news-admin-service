package sq.news.admin.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

import sq.base.BaseEntity;
import sq.base.WithName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 管理员
 * 
 * 
 * {name:"",loginName:"",lastLoginDate:""}
 */
@ApiModel(value = "管理员")
@Entity
@Table(name = "admins")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DynamicInsert
@DynamicUpdate
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE)
public class Admin extends BaseEntity implements WithName {

	private static final long serialVersionUID = 1L;
	public static final String NAME = "管理员";
	@ApiModelProperty(value = "管理员昵称")
	@JsonProperty
	private String name;// 管理员昵称
	@ApiModelProperty(value = "登录名")
	@JsonProperty
	private String loginName;// 登录名
	@ApiModelProperty(value = "登录密码")
	private String password;// 登录密码
	@ApiModelProperty(value = "最后一次登陆时间")
	@JsonProperty
	private Date lastLoginDate;// 最近一次登录时间
	@ApiModelProperty(value = "登陆时间")
	@JsonProperty
	private Date loginDate;// 登录时间
	@JsonProperty
	@ApiModelProperty(value = "描述")
	private String description;// 描述
	@ApiModelProperty(value = "角色列表")
	@JsonProperty
	private Set<Role> roles = new HashSet<>();// 角色列表
	@JsonProperty
	private String headImage;

	public String getHeadImage() {
		return headImage;
	}

	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}

	private Set<Permission> permissions = new HashSet<>();// 权限列表

	public Date getLastLoginDate() {
		return this.lastLoginDate;
	}

	public void setLastLoginDate(final Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public Date getLoginDate() {
		return this.loginDate;
	}

	public void setLoginDate(final Date loginDate) {
		this.loginDate = loginDate;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "admin_roles", joinColumns = { @JoinColumn(name = "adminId") }, inverseJoinColumns = {
			@JoinColumn(name = "roleId") })
	@Cascade({ org.hibernate.annotations.CascadeType.SAVE_UPDATE })
	@LazyCollection(LazyCollectionOption.EXTRA)
	public Set<Role> getRoles() {
		return this.roles;
	}

	public void setRoles(final Set<Role> roles) {
		this.roles = roles;
	}

	@Override
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getLoginName() {
		return this.loginName;
	}

	public void setLoginName(final String loginName) {
		this.loginName = loginName;
	}

	@Lob
	@Type(type = "text")
	public String getPassword() {
		return this.password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "admin_permissions", joinColumns = { @JoinColumn(name = "adminId") }, inverseJoinColumns = {
			@JoinColumn(name = "permissionId") })
	@LazyCollection(LazyCollectionOption.EXTRA)
	public Set<Permission> getPermissions() {
		return this.permissions;
	}

	public void setPermissions(final Set<Permission> permissions) {
		this.permissions = permissions;
	}

	@Column(columnDefinition = "TEXT")
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

}
