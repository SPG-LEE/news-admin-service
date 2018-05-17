package sq.enumeration;

import java.util.LinkedHashMap;
import java.util.Map;

public enum AppSiteCode {
	OPERATION_SUCCESS("6000", "操作成功", ""), LOGIN_UNUSUAL("6003", "登录异常", ""), ERROR_PASSWORD("6004", "您输入的密码有误", ""), NO_PERMISSION("6006", "没有权限", ""), NO_RESULT("6007", "没有找到匹配的实体",
			""), UNABLE_EDIT("6008", "此登录名[admin]无法创建和修改", ""), EXIST_LOGIN("6009", "该登录名已存在", ""), FIND_SUCCESS("6010", "查找成功", ""), ERROR_TOKEN("6011", "TOKEN不正确", ""), NO_ROLE("6012", "没有查询到该角色",
					""), NO_LOGINNAME("6013", "登录名不能为空", ""), PARAM_UNLEGAL("6014", "参数不合法！", ""), EXIST_PERMISSION("6015", "该权限名称已存在", ""), OPERATION_FAIL("6016", "操作失败", ""), EXIST_ROLE("6017",
							"该角色已存在", ""), BIG_COUNT("6018", "最大10条", ""), SAVE_SUCCESS("6020", "保存成功", ""), FIND_ERROR("6021", "查找失败",
									""), EXIST_BANKINFORMATION("6022", "银行账号已存在", ""), EXIST_EXPRESSNAME("6024", "快递名称已存在", ""), EXIST_SMSTYPENAME("6023", "短信类型名已存在", "");
	private String code;
	private String message;
	private String EnMessage;

	private AppSiteCode(String code, String message, String enMessage) {
		this.code = code;
		this.message = message;
		EnMessage = enMessage;
	}

	public static Map<String, AppSiteCode> getCodeMap() {
		Map<String, AppSiteCode> result = new LinkedHashMap<String, AppSiteCode>();
		for (AppSiteCode discountType : AppSiteCode.values()) {
			result.put(discountType.getCode() + "", discountType);
		}
		return result;
	}

	public static Map<String, AppSiteCode> getMessageMap() {
		Map<String, AppSiteCode> result = new LinkedHashMap<String, AppSiteCode>();
		for (AppSiteCode discountType : AppSiteCode.values()) {
			result.put(discountType.getMessage() + "", discountType);
		}
		return result;
	}

	public static Map<String, AppSiteCode> getEnMessageMap() {
		Map<String, AppSiteCode> result = new LinkedHashMap<String, AppSiteCode>();
		for (AppSiteCode discountType : AppSiteCode.values()) {
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
