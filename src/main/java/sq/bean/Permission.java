package sq.bean;

import sq.base.BaseEntity;
import sq.base.WithName;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.DynamicUpdate;

import java.util.HashSet;
import java.util.Set;

/**
 * 权限
 * 
 * 
 *
 */
@ApiModel(value = "权限")
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

	@Override
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getPermission() {
		return this.permission;
	}

	public void setPermission(final String permission) {
		this.permission = permission;
	}
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
