package sq.bean;

import java.util.HashSet;
import java.util.Set;

import sq.base.BaseEntity;
import sq.base.WithName;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE)
public class District extends BaseEntity implements WithName {

	private static final long serialVersionUID = 1L;

	@JsonProperty
	private String name = "";// 区域名称

	private City city;// 省份
	private Set<Address> addresses = new HashSet<Address>();// 地址

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public Set<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(Set<Address> addresses) {
		this.addresses = addresses;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
