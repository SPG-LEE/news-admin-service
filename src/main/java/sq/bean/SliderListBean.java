package sq.erp.admin.bean;

import java.util.LinkedHashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

import sq.base.BaseEntity;
import sq.news.admin.entity.Role;
import sq.news.admin.entity.Slider;

/**
 * 管理员
 * 
 * 
 * {name:"",loginName:"",lastLoginDate:""}
 */

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE)
public class SliderListBean extends BaseEntity {

	private static final long serialVersionUID = 1L;
	@JsonProperty
	private String name;

	@JsonProperty
	private String path;
	@JsonProperty
	private String title;
	@JsonProperty
	private String icon;
	@JsonProperty
	private Role role;
	private Slider parent;
	@JsonProperty
	private int sort = 999;
	@JsonProperty
	private Set<SliderListBean> subMenus = new LinkedHashSet<SliderListBean>();

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Role getRole() {
		return role;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Set<SliderListBean> getSubMenus() {
		return subMenus;
	}

	public void setSubMenus(Set<SliderListBean> subMenus) {
		this.subMenus = subMenus;
	}

	public Slider getParent() {
		return parent;
	}

	public void setParent(Slider parent) {
		this.parent = parent;
	}

}
