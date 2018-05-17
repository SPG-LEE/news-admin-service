package sq.bean;

import java.util.HashSet;
import java.util.Set;

import sq.base.BaseEntity;
import sq.base.WithName;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE)
public class Province extends BaseEntity implements WithName {

	private static final long serialVersionUID = 1L;
	@JsonProperty
	private String name = "";// 名称
	private Set<City> citys = new HashSet<City>();// 城市
	private Set<Address> addresses = new HashSet<Address>();// 地址
	private String expressPriceId;// 快递计费价格

	public String getExpressPriceId() {
		return expressPriceId;
	}

	public void setExpressPriceId(String expressPriceId) {
		this.expressPriceId = expressPriceId;
	}

	public Set<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(Set<Address> addresses) {
		this.addresses = addresses;
	}

	public Set<City> getCitys() {
		return citys;
	}

	public void setCitys(Set<City> citys) {
		this.citys = citys;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
