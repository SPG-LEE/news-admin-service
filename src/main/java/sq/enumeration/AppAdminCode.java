package sq.enumeration;

import java.util.LinkedHashMap;
import java.util.Map;

public enum AppAdminCode {
	OPERATION_SUCCESS("1000", "操作成功", ""), NO_LOGIN("1002", "请先登录", ""), LOGIN_UNUSUAL(
			"1003", "登录异常", ""), ERROR_PASSWORD("1004", "您输入的管理员密码有误", ""), NO_PERMISSION(
			"1006", "没有管理员权限", ""), NO_RESULT("1007", "没有找到匹配的实体", ""), UNABLE_EDIT(
			"1008", "此登录名[admin]无法创建和修改", ""), EXIST_LOGIN("1009", "该登录名已存在",
			""), FIND_SUCCESS("1010", "查找成功", ""), ERROR_TOKEN("1011",
			"TOKEN不正确", ""), NO_ROLE("1012", "没有查询到该角色", ""), NO_LOGINNAME(
			"1013", "登录名不能为空", ""), PARAM_UNLEGAL("1014", "参数不合法！", ""), EXIST_PERMISSION(
			"1015", "该权限名称已存在", ""), OPERATION_FAIL("1016", "操作失败", ""), EXIST_ROLE(
			"1017", "该角色已存在", ""), ERROR_UPLOAD("1018", "上传失败", ""), SAVE_SUCCESS(
			"1020", "保存成功", ""), FIND_ERROR("1021", "查找失败", ""), PASSWORD_UNABLE(
			"1022", "密码不能全为数字，或字母，长度必须在6-20", ""),DISABLE_LOGINNAME("1023", "账户异常，请联系管理员", "");
	private String code;
	private String message;
	private String EnMessage;

	private AppAdminCode(String code, String message, String enMessage) {
		this.code = code;
		this.message = message;
		EnMessage = enMessage;
	}

	public static Map<String, AppAdminCode> getCodeMap() {
		Map<String, AppAdminCode> result = new LinkedHashMap<String, AppAdminCode>();
		for (AppAdminCode discountType : AppAdminCode.values()) {
			result.put(discountType.getCode() + "", discountType);
		}
		return result;
	}

	public static Map<String, AppAdminCode> getMessageMap() {
		Map<String, AppAdminCode> result = new LinkedHashMap<String, AppAdminCode>();
		for (AppAdminCode discountType : AppAdminCode.values()) {
			result.put(discountType.getMessage() + "", discountType);
		}
		return result;
	}

	public static Map<String, AppAdminCode> getEnMessageMap() {
		Map<String, AppAdminCode> result = new LinkedHashMap<String, AppAdminCode>();
		for (AppAdminCode discountType : AppAdminCode.values()) {
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
