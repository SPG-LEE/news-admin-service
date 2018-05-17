package sq.enumeration;

import java.util.LinkedHashMap;
import java.util.Map;

public enum AppProductCode {
	QUERY_SUCCESS("3000", "查询成功", ""), PARAM_UNLEGAL("3001", "参数不合法！", ""), NO_PERMISSION(
			"3003", "没有权限", ""), NO_RESULT("3004", "没有找到匹配的实体", ""), Name_Exist(
			"3005", "名字已存在！", ""), ILLEGAL_REQUEST("3006", "非法请求!", ""), QUERY_EMPTY(
			"3007", "查询数据为空！", ""), OPERATION_SUCCESS("3100", "操作成功", ""), OPERATION_FAIL(
			"3101", "操作失败", ""), PRODUCT_SKU_EXISTS("3401", "SKU已存在", ""), CATALOG_NUMBER_EXIST(
			"3402", "类目编码或类目名称已存在！", ""), COLOR_SET_NAME_EXISTS("3403",
			"颜色模板类型名已存在", ""), SIZE_SET_NAME_EXISTS("3404", "尺寸模板类型名已存在", ""), PRODUCT_ITEM_SKU_EXISTS(
			"3405", "ParentSku或SKU已存在", ""), COLOR_SET_COMMON_TYPE_EXISTS(
			"3406", "颜色模板通用类型已存在", ""), SIZE_SET_COMMON_TYPE_EXISTS("3407",
			"尺寸模板通用类型已存在", ""), PRODUCT_FAVORITE_SUCCESS("3500", "商品收藏成功", ""), PRODUCT_FAVORITE_FAIL(
			"3501", "商品收藏失败", ""), PRODUCT_CATALOG_DELETE_FAIL("3502",
			"产品和类目存在关联，删除失败", ""), PRODUCT_CATALOG_UPDATE_FAIL("3503",
			"产品和类目存在关联，更新失败", ""),PRODUCT_IMAGES_EMPTY("3504","商品图片不能为空，请上传",""),EnName_Exist(
							"3505", "英文名字已存在！", ""),
	PRODUCT_ITEM_EMPTY("3506","商品变体不存在",""),PRODUCT_NAME_EMPTY("3507","商品标题不存在","")
	,PRODUCT_DESCRIPTION_EMPTY("3508","商品描述不存在",""),PRODUCT_COLOR_EMPTY("3509","商品颜色不存在","")
	,PRODUCT_SIZE_EMPTY("3510","商品尺寸不存在",""),PRODUCT_MSRP_EMPTY("3511","商品MSRP不存在","")
	,PRODUCT_ITEM_WIEGHT_EMPTY("3512","商品变体重量不存在",""),PRODUCT_ITEM_PRICE_EMPTY("3513","商品变体价格不存在","")
	,PRODUCT_ITEM_IMAGE_EMPTY("3514","商品变体图片不存在",""),PRODUCT_ITEM_STOCK_EMPTY("3515","商品变体数量不存在",""),PRODUCT_NUMBER_EMPTY("3516","商品编码不存在","")
	,PRODUCT_CATALOG_EMPTY("3517","商品类目不存在",""),PRODUCT_ITEM_NUMBER_EMPTY("3518","商品变体编码不存在",""),
	PRODUCT_PRICES_UNLEGAL("3519","商品阶梯价格不合法",""),PRODUCT_ITEM_NUMBER_REPEAT("3520","商品变体编码相同",""),
	PRODUCT_LADDER_PRICE_QUANTITY_NOSAME("3521","商品阶梯价格或数量不同时存在或不存在",""),
	PRODUCT_LADDER_EXCEED("3522","商品阶梯参数传入不符合梯度高低规则",""),
	PRODUCT_EXCEL_DOWNLOAD("3523","商品下载成功，请到批处理任务查询界面中查询","")
	;

//	批处理任务查询
	private String code;
	private String message;
	private String EnMessage;

	// public static final String QUERY_EMPTY = "查询数据为空！";
	private AppProductCode(String code, String message, String enMessage) {
		this.code = code;
		this.message = message;
		EnMessage = enMessage;
	}

	public static Map<String, AppProductCode> getCodeMap() {
		Map<String, AppProductCode> result = new LinkedHashMap<String, AppProductCode>();
		for (AppProductCode discountType : AppProductCode.values()) {
			result.put(discountType.getCode() + "", discountType);
		}
		return result;
	}

	public static Map<String, AppProductCode> getMessageMap() {
		Map<String, AppProductCode> result = new LinkedHashMap<String, AppProductCode>();
		for (AppProductCode discountType : AppProductCode.values()) {
			result.put(discountType.getMessage() + "", discountType);
		}
		return result;
	}

	public static Map<String, AppProductCode> getEnMessageMap() {
		Map<String, AppProductCode> result = new LinkedHashMap<String, AppProductCode>();
		for (AppProductCode discountType : AppProductCode.values()) {
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
