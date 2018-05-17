package sq.enumeration;

import java.util.LinkedHashMap;
import java.util.Map;

public enum AppEleveniaCode {
	OPERATION_SUCCESS("8000", "操作成功", ""), OPERATION_FAIL("8001", "操作失败",
			""), HAS_PUT_ELEVENIA("8002", "已经刊登至elevenia", "");
	;
	private String code;
	private String message;
	private String EnMessage;

	private AppEleveniaCode(String code, String message, String enMessage) {
		this.code = code;
		this.message = message;
		EnMessage = enMessage;
	}

	public static Map<String, AppEleveniaCode> getCodeMap() {
		Map<String, AppEleveniaCode> result = new LinkedHashMap<String, AppEleveniaCode>();
		for (AppEleveniaCode discountType : AppEleveniaCode.values()) {
			result.put(discountType.getCode() + "", discountType);
		}
		return result;
	}

	public static Map<String, AppEleveniaCode> getMessageMap() {
		Map<String, AppEleveniaCode> result = new LinkedHashMap<String, AppEleveniaCode>();
		for (AppEleveniaCode discountType : AppEleveniaCode.values()) {
			result.put(discountType.getMessage() + "", discountType);
		}
		return result;
	}

	public static Map<String, AppEleveniaCode> getEnMessageMap() {
		Map<String, AppEleveniaCode> result = new LinkedHashMap<String, AppEleveniaCode>();
		for (AppEleveniaCode discountType : AppEleveniaCode.values()) {
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
