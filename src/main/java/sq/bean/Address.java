package sq.bean;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.PropertyUtils;

import sq.base.BaseEntity;
import sq.base.WithName;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE)
public class Address extends BaseEntity implements WithName {

	private static final long serialVersionUID = 1L;

	@JsonProperty
	private String name = "";// 地址名称

	private boolean asMale = true; // 未知
	@JsonProperty
	private String address = ""; // 详细地址
	@JsonProperty
	private String mobilePhone = ""; // 手机号
	@JsonProperty
	private boolean asDefault = false;// 是否为默认地址

	private String telephone = "";// 电话
	@JsonProperty
	private Province province;// 省
	@JsonProperty
	private District district;// 区
	@JsonProperty
	private City city;// 市
	@JsonProperty
	private User user;// 用户
	@JsonProperty
	private String email = "";// 邮箱
	@JsonProperty
	private String postCode = "";// 邮编
	@JsonProperty
	private String provinceJson;

	public String getProvinceJson() {
		return provinceJson;
	}

	public void setProvinceJson(String provinceJson) {
		this.provinceJson = provinceJson;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getAddress() {
		return address;
	}

	public boolean isAsDefault() {
		return asDefault;
	}

	public String getTelephone() {
		return telephone;
	}

	public User getUser() {
		return user;
	}

	public Province getProvince() {
		return province;
	}

	public void setProvince(Province province) {
		this.province = province;
	}

	public District getDistrict() {
		return district;
	}

	public void setDistrict(District district) {
		this.district = district;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	@Override
	public String getName() {
		return name;
	}

	public boolean isAsMale() {
		return asMale;
	}

	public void setAsMale(boolean asMale) {
		this.asMale = asMale;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setAsDefault(boolean asDefault) {
		this.asDefault = asDefault;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public void setUser(User users) {
		user = users;
	}

	public Address cloneMe() {
		Address address = new Address();
		try {
			PropertyUtils.copyProperties(address, this);
		} catch (IllegalAccessException | InvocationTargetException
				| NoSuchMethodException e) {
			e.printStackTrace();
		}
		address.setId(null);
		return address;
	}

	public String getInfo() {
		String info = "";
		if (name != null) {
			info += name + " ";
		}
		info += getCityInfo();
		if (address != null) {
			info += address;
		}
		return info;
	}

	public String getCityInfo() {
		String info = "";

		if (province != null) {
			info += province.getName();
		}

		if (city != null) {
			info += city.getName();
		}
		if (district != null) {
			info += district.getName();
		}
		return info;
	}

	public String getTitle() {
		String sep = ",";
		StringBuilder sb = new StringBuilder();
		if (name != null) {
			sb.append(name).append(sep);
		}
		if (province != null) {
			sb.append(province.getName()).append(sep);
		}
		if (city != null) {
			sb.append(city.getName()).append(sep);
		}
		if (district != null) {
			sb.append(district.getName()).append(sep);
		}

		if (address != null) {
			sb.append(address).append(sep);
		}
		if (postCode != null) {
			sb.append(postCode).append(sep);
		}
		if (telephone != null) {
			sb.append(telephone).append(sep);
		}
		if (mobilePhone != null) {
			sb.append(mobilePhone).append(sep);
		}

		String title = sb.toString();
		if (title.endsWith(sep)) {
			title = title.substring(0, title.length() - 1);
		}
		return title;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

}
