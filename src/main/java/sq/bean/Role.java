package sq.bean;

import sq.base.BaseEntity;
import sq.base.WithName;
import sq.news.admin.constants.SysConstants;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 角色
 * 
 * 
 *
 */
@ApiModel(value = "角色")
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

	@Override
	public String getName() {
		return this.name;
	}

	public static String getAdmin() {
		return admin;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}
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
