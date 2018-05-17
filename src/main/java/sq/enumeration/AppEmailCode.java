package sq.enumeration;

import java.util.LinkedHashMap;
import java.util.Map;

public enum AppEmailCode {
	OPERATION_SUCCESS("5000", "操作成功", ""), OPERATION_FAIL("5001", "操作失败", ""), VALITATE_SUCCESS(
			"5100", "验证通过", ""), USER_REGISTERED("5101", "该用户已注册", ""), ERROR_PASSWORD(
			"5104", "密码不正确", ""), VALITATE_FAIL("5105", "验证未通过", ""), No_EMAIL(
			"5107", "邮箱不存在或不合法", ""), NO_PERMISSION("5202", "没有权限", ""), QUERY_SUCCESS(
			"5300", "查询成功", ""), ERROE_EMAIL("5301", "错误的邮件地址", ""), PARAM_UNLEGAL(
			"5302", "参数不合法！", ""), NO_RESULT("5303", "没有找到匹配的实体", ""), ERROR_UPLOAD(
			"5304", "上传失败", ""), SYSTEM_UNUSUAL("5305", "系统异常", ""), EXPIRED_EMAIL(
			"5306", "邮件已过期", ""), ILLEGAL_REQUEST("5307", "非法请求!", ""), NO_CODE(
			"5308", "验证码已过期，请重新登录", ""), DISABLE_USER("5309",
			"账户已禁用，如有疑问，请联系客服", "");
	private String code;
	private String message;
	private String EnMessage;

	private AppEmailCode(String code, String message, String enMessage) {
		this.code = code;
		this.message = message;
		EnMessage = enMessage;
	}

	public static Map<String, AppEmailCode> getCodeMap() {
		Map<String, AppEmailCode> result = new LinkedHashMap<String, AppEmailCode>();
		for (AppEmailCode discountType : AppEmailCode.values()) {
			result.put(discountType.getCode() + "", discountType);
		}
		return result;
	}

	public static Map<String, AppEmailCode> getMessageMap() {
		Map<String, AppEmailCode> result = new LinkedHashMap<String, AppEmailCode>();
		for (AppEmailCode discountType : AppEmailCode.values()) {
			result.put(discountType.getMessage() + "", discountType);
		}
		return result;
	}

	public static Map<String, AppEmailCode> getEnMessageMap() {
		Map<String, AppEmailCode> result = new LinkedHashMap<String, AppEmailCode>();
		for (AppEmailCode discountType : AppEmailCode.values()) {
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
