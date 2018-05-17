package sq.news.admin.entity;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

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

@Entity
@Table(name = "sliders")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DynamicInsert
@DynamicUpdate
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE)
public class Slider extends BaseEntity {

	private static final long serialVersionUID = 1L;
	@JsonProperty
	private String name;
	private Set<Role> roles;
	@JsonProperty
	private String path;
	@JsonProperty
	private String title;
	@JsonProperty
	private String icon;
	private Slider parent;
	@JsonProperty
	private int sort = 999;
	@JsonProperty
	private Set<Slider> subMenus = new LinkedHashSet<Slider>();

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public String getName() {
		return name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "role_sliders", joinColumns = { @JoinColumn(name = "sliderId") }, inverseJoinColumns = {
			@JoinColumn(name = "roleId") })
	@Cascade({ org.hibernate.annotations.CascadeType.SAVE_UPDATE })
	@LazyCollection(LazyCollectionOption.EXTRA)

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "parent", orphanRemoval = true)
	@OrderBy("sort")
	public Set<Slider> getSubMenus() {
		return subMenus;
	}

	public void setSubMenus(Set<Slider> subMenus) {
		this.subMenus = subMenus;
	}

	@ManyToOne
	@JoinColumn(name = "parentId")
	public Slider getParent() {
		return parent;
	}

	public void setParent(Slider parent) {
		this.parent = parent;
	}

}
