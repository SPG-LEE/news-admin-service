package sq.news.admin.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import sq.news.admin.constants.SysConstants;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

import sq.base.BaseEntity;
import sq.base.WithName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 角色
 * 
 * 
 *
 */
@ApiModel(value = "角色")
@Entity
@Table(name = "roles")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DynamicInsert
@DynamicUpdate
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE)
public class Role extends BaseEntity implements WithName {

	private static final long serialVersionUID = 1L;
	public static final String NAME = "角色";
	@ApiModelProperty(value = "角色名")
	@JsonProperty
	private String name;// 角色名
	@JsonProperty
	@ApiModelProperty(value = "角色字段")
	private String roleName;// 角色字段
	@ApiModelProperty(value = "角色描述")
	@JsonProperty
	private String description;// 角色描述
	@ApiModelProperty(value = "权限")
	@JsonProperty
	private Set<Permission> permissions = new HashSet<>();// 权限
	private Set<Admin> admins = new HashSet<>();// 管理员
	@JsonProperty
	private Set<Slider> menus = new HashSet<>();// 导航栏列表

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "admin_roles", joinColumns = { @JoinColumn(name = "roleId") }, inverseJoinColumns = {
			@JoinColumn(name = "adminId") })
	@Cascade({ org.hibernate.annotations.CascadeType.SAVE_UPDATE })
	@LazyCollection(LazyCollectionOption.EXTRA)
	public Set<Admin> getAdmins() {
		return this.admins;
	}

	public void setAdmins(final Set<Admin> admins) {
		this.admins = admins;
	}

	@Override
	@Column(length = 100, unique = true)
	public String getName() {
		return this.name;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "role_sliders", joinColumns = { @JoinColumn(name = "roleId") }, inverseJoinColumns = {
			@JoinColumn(name = "sliderId") })
	@Cascade({ org.hibernate.annotations.CascadeType.SAVE_UPDATE })
	@LazyCollection(LazyCollectionOption.EXTRA)
	public Set<Slider> getMenus() {
		return menus;
	}

	public void setMenus(Set<Slider> menus) {
		this.menus = menus;
	}

	public static String getAdmin() {
		return admin;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@Column(columnDefinition = "TEXT")
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "roles_permissions", joinColumns = { @JoinColumn(name = "roleId") }, inverseJoinColumns = {
			@JoinColumn(name = "permissionId") })
	@LazyCollection(LazyCollectionOption.EXTRA)
	public Set<Permission> getPermissions() {
		return this.permissions;
	}

	public void setPermissions(final Set<Permission> permissions) {
		this.permissions = permissions;
	}

	@Column(length = 100)
	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(final String value) {
		this.roleName = value;
	}

	@Override
	public String toString() {
		return this.name;
	}

	public static final String manage = "manage";

	public static final String admin = SysConstants.SUPERADMIN;
}
