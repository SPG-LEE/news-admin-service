package sq.enumeration;

import java.util.LinkedHashMap;
import java.util.Map;

public enum AppCommonCode {
	NO_TOKEN("0001", "管理员账户长时间未使用，请重新登录", ""), NO_ADMIN("0002", "没有找到此管理员", ""), USER_UNREGISTER(
			"0004", "该用户未注册", ""), USER_NO_TOKEN("0005", "用户账户长时间未使用，请重新登录", "");
	private String code;
	private String message;
	private String EnMessage;

	private AppCommonCode(String code, String message, String enMessage) {
		this.code = code;
		this.message = message;
		EnMessage = enMessage;
	}

	public static Map<String, AppCommonCode> getCodeMap() {
		Map<String, AppCommonCode> result = new LinkedHashMap<String, AppCommonCode>();
		for (AppCommonCode discountType : AppCommonCode.values()) {
			result.put(discountType.getCode() + "", discountType);
		}
		return result;
	}

	public static Map<String, AppCommonCode> getMessageMap() {
		Map<String, AppCommonCode> result = new LinkedHashMap<String, AppCommonCode>();
		for (AppCommonCode discountType : AppCommonCode.values()) {
			result.put(discountType.getMessage() + "", discountType);
		}
		return result;
	}

	public static Map<String, AppCommonCode> getEnMessageMap() {
		Map<String, AppCommonCode> result = new LinkedHashMap<String, AppCommonCode>();
		for (AppCommonCode discountType : AppCommonCode.values()) {
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
