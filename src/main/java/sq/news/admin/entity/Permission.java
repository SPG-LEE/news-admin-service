package sq.news.admin.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonProperty;

import sq.base.BaseEntity;
import sq.base.WithName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 权限
 * 
 * 
 *
 */
@ApiModel(value = "权限")
@Entity
@Table(name = "permissions")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DynamicInsert
@DynamicUpdate
public class Permission extends BaseEntity implements WithName {

	private static final long serialVersionUID = 1L;
	public static final String NAME = "权限";
	@ApiModelProperty(value = "权限名称")
	@JsonProperty
	private String name;// 权限名称
	@JsonProperty
	@ApiModelProperty(value = "权限字段")
	private String permission;// 权限字段
	@JsonProperty
	private String description;// 描述
	private Set<Role> roles = new HashSet<>();// 角色

	@ManyToMany(mappedBy = "permissions", fetch = FetchType.LAZY)
	@LazyCollection(LazyCollectionOption.EXTRA)
	public Set<Role> getRoles() {
		return this.roles;
	}

	public void setRoles(final Set<Role> roles) {
		this.roles = roles;
	}

	@Override
	@Column(length = 100, unique = true)
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@Column(length = 255, unique = true)
	public String getPermission() {
		return this.permission;
	}

	public void setPermission(final String permission) {
		this.permission = permission;
	}

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "text")
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return this.name;
	}

}
