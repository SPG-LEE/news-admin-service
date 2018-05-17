package sq.enumeration;

import java.util.LinkedHashMap;
import java.util.Map;

public enum AppUserCode {

	OPERATION_SUCCESS("2000", "操作成功", ""), OPERATION_FAIL("2001", "操作失败", ""), VALITATE_SUCCESS("2100", "验证通过",
			""), USER_REGISTERED("2101", "该用户已注册", ""), ERROR_PASSWORD("2104", "密码不正确", ""), VALITATE_FAIL("2105",
					"验证码不正确，请重新输入", ""), No_NAME("2106", "昵称不存在", ""), No_EMAIL("2107", "邮箱不存在或不合法", ""), SAME_PASSWORD(
							"2019", "密码与原密码相同",
							""), NO_PERMISSION("2202", "没有权限", ""), QUERY_SUCCESS("2300", "查询成功", ""), ERROE_EMAIL(
									"2301", "错误的邮件地址", ""), PARAM_UNLEGAL("2302", "参数不合法！", ""), NO_RESULT("2303",
											"没有找到匹配的实体", ""), ERROR_UPLOAD("2304", "上传失败", ""), SYSTEM_UNUSUAL("2305",
													"系统异常", ""), EXPIRED_EMAIL("2306", "邮件已过期", ""), ILLEGAL_REQUEST(
															"2307", "非法请求!",
															""), NO_CODE("2308", "验证码已过期，请重新获取", ""), DISABLE_USER(
																	"2309", "账户已禁用，如有疑问，请联系客服",
																	""), EXSIT_FAVORITE("2401", "收藏已存在",
																			""), PASSWORD_UNABLE("2402", "密码格式不正确",
																					""), LOGINNAME_ERROR("2403",
																							"用户名输入错误",
																							""), CONNECT_ERROR("2404",
																									"链接已失效",
																									""), CONNECT_TIME_OUT(
																											"2405",
																											"链接超时", ""),EMAIL_REGISTERED("2406", "该邮箱已注册", ""),PHONE_REGISTERED("2407", "该手机已注册", ""),USERNAME_UNABLE("2408", "用户名不合法", ""),PICTURE_VALITATE_FAIL("2409",
																													"图片验证码不正确，请重新输入", ""),PHONE_VALITATE_FAIL("2501",
																															"手机验证码不正确，请重新输入", ""),PICTURE_NO_CODE("2502", "图片验证码已过期，请重新获取", ""),PHONE_NO_CODE("2503", "手机验证码已过期，请重新获取", ""),NOT_NULL_TOGETHER("2504", "手机号和邮箱不能同时为空", "");
	private String code;
	private String message;
	private String EnMessage;

	private AppUserCode(String code, String message, String enMessage) {
		this.code = code;
		this.message = message;
		EnMessage = enMessage;
	}

	public static Map<String, AppUserCode> getCodeMap() {
		Map<String, AppUserCode> result = new LinkedHashMap<String, AppUserCode>();
		for (AppUserCode discountType : AppUserCode.values()) {
			result.put(discountType.getCode() + "", discountType);
		}
		return result;
	}

	public static Map<String, AppUserCode> getMessageMap() {
		Map<String, AppUserCode> result = new LinkedHashMap<String, AppUserCode>();
		for (AppUserCode discountType : AppUserCode.values()) {
			result.put(discountType.getMessage() + "", discountType);
		}
		return result;
	}

	public static Map<String, AppUserCode> getEnMessageMap() {
		Map<String, AppUserCode> result = new LinkedHashMap<String, AppUserCode>();
		for (AppUserCode discountType : AppUserCode.values()) {
			result.put(discountType.getEnMessage() + "", discountType);
		}
		return result;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getEnMessage() {
		return EnMessage;
	}

	public void setEnMessage(String enMessage) {
		EnMessage = enMessage;
	}

}
