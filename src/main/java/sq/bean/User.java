package sq.bean;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

import sq.base.BaseEntity;
import sq.base.WithName;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE)
public class User extends BaseEntity implements WithName {

	private static final long serialVersionUID = 1L;
	public static final String NAME = "用户";
	@JsonProperty
	private String name;// 昵称

	private String loginName;// 登录名
	private String password;// 登录密码

	private Date lastLoginDate;// 最近一次登录时间

	private String ip;// ip地址

	private String email = "";// 邮箱
	private String description;// 描述
	@JsonProperty
	private String mobilePhone;// 手机号
	private Date lastOrderDate;// 最后一次下单时间
	// --add by zhangshenglong 2015-12-16
	@JsonProperty
	private String sign;// 签名
	@JsonProperty
	private String sex;// 性别
	@JsonProperty
	private String birthday;// 生日
	@JsonProperty
	private String headImg;// 头像
	@JsonProperty
	private String qrcode;// 二维码
	private boolean regularCustomer;// 新老用户，false新用户，true老用户
	// add by zhangsl 2016-02-18
	@JsonProperty
	private String orderCount;// 订单数量
	@JsonProperty
	private String favoriteCount;// 收藏数量
	@JsonProperty
	private String deliverCount;// 取货记录数量
	@JsonProperty
	private String cartCount;// 购物车数量
	@JsonProperty
	private String language;// 语言类型
	@JsonProperty
	private String couponCount;// 优惠券数量
	private String machineNumber;// 注册设备号

	public Date getLastOrderDate() {
		return lastOrderDate;
	}

	public void setLastOrderDate(Date lastOrderDate) {
		this.lastOrderDate = lastOrderDate;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getMachineNumber() {
		return machineNumber;
	}

	public void setMachineNumber(String machineNumber) {
		this.machineNumber = machineNumber;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	public String getQrcode() {
		return qrcode;
	}

	public void setQrcode(String qrcode) {
		this.qrcode = qrcode;
	}

	public boolean isRegularCustomer() {
		return regularCustomer;
	}

	public void setRegularCustomer(boolean regularCustomer) {
		this.regularCustomer = regularCustomer;
	}

	public String getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(String orderCount) {
		this.orderCount = orderCount;
	}

	public String getFavoriteCount() {
		return favoriteCount;
	}

	public void setFavoriteCount(String favoriteCount) {
		this.favoriteCount = favoriteCount;
	}

	public String getDeliverCount() {
		return deliverCount;
	}

	public void setDeliverCount(String deliverCount) {
		this.deliverCount = deliverCount;
	}

	public String getCartCount() {
		return cartCount;
	}

	public void setCartCount(String cartCount) {
		this.cartCount = cartCount;
	}

	public String getCouponCount() {
		return couponCount;
	}

	public void setCouponCount(String couponCount) {
		this.couponCount = couponCount;
	}

}
