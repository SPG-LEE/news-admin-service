package sq.enumeration;

import java.util.LinkedHashMap;
import java.util.Map;

public enum AppOrderCode {
	OPERATION_SUCCESS("4000", "操作成功", ""), OPERATION_FAIL("4001", "操作失败",
			""), SERVICE_BUSY("4002", "服务繁忙", ""), VALITATE_SUCCESS("4100",
					"验证通过",
					""), USER_REGISTERED("4101", "该用户已注册", ""), ERROR_PASSWORD(
							"4104", "密码不正确",
							""), VALITATE_FAIL("4105", "验证未通过", ""), No_NAME(
									"4106", "昵称不存在", ""), NO_PERMISSION("4202",
											"没有权限", ""), QUERY_SUCCESS("4300",
													"查询成功", ""), PARAM_UNLEGAL(
															"4302", "参数不合法！",
															""), NO_RESULT(
																	"4303",
																	"没有找到匹配的实体",
																	""), ERROR_UPLOAD(
																			"4304",
																			"上传失败",
																			""), SYSTEM_UNUSUAL(
																					"4305",
																					"系统异常",
																					""), ILLEGAL_REQUEST(
																							"4307",
																							"非法请求!",
																							""), OUT_CART_COUNT(
																									"4308",
																									"购物车数量已达上线",
																									""), CHECKOUT_ERROR(
																											"4309",
																											"订单校验失败",
																											""), OUT_TIMES(
																													"4310",
																													"今日已经提醒3次，请改日再提醒",
																													""), NO_STOCK(
																															"4311",
																															"没有库存",
																															""), HAS_PRODUCT_OFF(
																																	"4401",
																																	"存在商品下架",
																																	""), NO_ENOUGH_INVENTORY(
																																			"4402",
																																			"没有足够库存",
																																			""), FORBID_REVOCATION(
																																					"4403",
																																					"不能撤销检货",
																																					""), IS_NOT_OFFLINEORDER(
																																							"4404",
																																							"不是线下订单",
																																							""), EXIST_ENOUGH_INVENTORY(
																																									"4405",
																																									"库存足够不用拆分订单",
																																									""), HAS_SORTING(
																																											"4406",
																																											"订单已处理，拒绝修改操作",
																																											""), EXIST_THESAME_SKU(
																																													"4407",
																																													"已存在相同的sku",
																																													""), EXIST_UNSEND_PACKAGE(
																																															"4408",
																																															"存在未发货包裹",
																																															""), HAS_SEND(
																																																	"4409",
																																																	"存在已发货包裹",
																																																	""), ERROR_DATA(
																																																			"4410",
																																																			"错误数据，请联系管理员",
																																																			"");
	;
	private String code;
	private String message;
	private String EnMessage;

	private AppOrderCode(String code, String message, String enMessage) {
		this.code = code;
		this.message = message;
		EnMessage = enMessage;
	}

	public static Map<String, AppOrderCode> getCodeMap() {
		Map<String, AppOrderCode> result = new LinkedHashMap<String, AppOrderCode>();
		for (AppOrderCode discountType : AppOrderCode.values()) {
			result.put(discountType.getCode() + "", discountType);
		}
		return result;
	}

	public static Map<String, AppOrderCode> getMessageMap() {
		Map<String, AppOrderCode> result = new LinkedHashMap<String, AppOrderCode>();
		for (AppOrderCode discountType : AppOrderCode.values()) {
			result.put(discountType.getMessage() + "", discountType);
		}
		return result;
	}

	public static Map<String, AppOrderCode> getEnMessageMap() {
		Map<String, AppOrderCode> result = new LinkedHashMap<String, AppOrderCode>();
		for (AppOrderCode discountType : AppOrderCode.values()) {
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
