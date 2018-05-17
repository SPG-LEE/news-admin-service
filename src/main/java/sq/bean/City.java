package sq.bean;

import java.util.HashSet;
import java.util.Set;

import sq.base.BaseEntity;
import sq.base.WithName;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE)
public class City extends BaseEntity implements WithName {

	private static final long serialVersionUID = 1L;

	@JsonProperty
	private String name = "";// 城市名称
	private String zipCode = "";// 邮编
	private Province province;// 省份
	private Set<District> districts = new HashSet<District>();// 区
	private Set<Address> addresses = new HashSet<Address>();// 地址

	public Set<District> getDistricts() {
		return districts;
	}

	public void setDistricts(Set<District> districts) {
		this.districts = districts;
	}

	public Province getProvince() {
		return province;
	}

	public void setProvince(Province province) {
		this.province = province;
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

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

}
