package sq.enumeration;

import java.util.LinkedHashMap;
import java.util.Map;

public enum AppNotificationCode {
	OPERATION_SUCCESS("7000", "操作成功", ""), NO_LOGIN("7002", "请先登录", ""), LOGIN_UNUSUAL("7003", "登录异常",
			""), ERROR_PASSWORD("7004", "您输入的管理员密码有误", ""), NO_PERMISSION("7006", "没有管理员权限", ""), NO_RESULT("7007",
					"没有找到匹配的实体", ""), UNABLE_EDIT("7008", "此登录名[admin]无法创建和修改", ""), EXIST_LOGIN("7009", "该登录名已存在",
							""), FIND_SUCCESS("7010", "查找成功", ""), ERROR_TOKEN("7011", "TOKEN不正确", ""), NO_LOGINNAME(
									"7013", "登录名不能为空", ""), EXIST_PERMISSION("7015", "该权限名称已存在", ""), OPERATION_FAIL(
											"7016", "操作失败", ""), EXIST_ROLE("7017", "该角色已存在", ""), ERROR_UPLOAD("7018",
													"上传失败", ""), SAVE_SUCCESS("7020", "保存成功", ""), FIND_ERROR("7021",
															"查找失败", ""), OUT_TIMES("7022", "今日已经提醒3次，请改日再提醒", "");
	private String code;
	private String message;
	private String EnMessage;

	private AppNotificationCode(String code, String message, String enMessage) {
		this.code = code;
		this.message = message;
		EnMessage = enMessage;
	}

	public static Map<String, AppNotificationCode> getCodeMap() {
		Map<String, AppNotificationCode> result = new LinkedHashMap<String, AppNotificationCode>();
		for (AppNotificationCode discountType : AppNotificationCode.values()) {
			result.put(discountType.getCode() + "", discountType);
		}
		return result;
	}

	public static Map<String, AppNotificationCode> getMessageMap() {
		Map<String, AppNotificationCode> result = new LinkedHashMap<String, AppNotificationCode>();
		for (AppNotificationCode discountType : AppNotificationCode.values()) {
			result.put(discountType.getMessage() + "", discountType);
		}
		return result;
	}

	public static Map<String, AppNotificationCode> getEnMessageMap() {
		Map<String, AppNotificationCode> result = new LinkedHashMap<String, AppNotificationCode>();
		for (AppNotificationCode discountType : AppNotificationCode.values()) {
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
