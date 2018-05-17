package sq.enumeration;

import java.util.LinkedHashMap;
import java.util.Map;

public enum AppInventoryCode {
	QUERY_SUCCESS("7000", "查询成功", ""), PARAM_UNLEGAL("7001", "参数不合法！", ""), NO_PERMISSION(
			"7003", "没有权限", ""), NO_RESULT("7004", "没有找到匹配的实体", ""), Name_Exist(
			"7005", "名字已存在！", ""), ILLEGAL_REQUEST("7006", "非法请求!", ""), QUERY_EMPTY(
			"7007", "查询数据为空！", ""), NUMBER_EXIST("7009", "库位或箱位号已存在！", ""), OPERATION_SUCCESS(
			"7100", "操作成功", ""), OPERATION_FAIL("7101", "操作失败", ""),
	LOGIC_FAIL("7102", "执行异常", ""),STOCK_LOCK("7103", "请关闭自动拣货开关", ""),
	SITE_NULL("7104","SITE服务未开启",""),SITE_ERROR("7105","SITE服务异常",""),EXCEL_ERROR("7106","excel解析异常",""),
	BOX_SKU_HAVEN("7200", "箱子里存在库存，不能删除", ""),BOX_SHELF_LOST("7201", "箱子存在，库位不存在", ""),
	BOX_SHELF_ALL("7202", "箱子和库位都存在", ""),BOX_SKU_STOCK("7203", "库存小于0", ""),BOX_SHELF_FAIL("7204", "箱位或库位不一致", ""),
	BOX_SKU_NONE("7205", "商品变体在该箱子里无库存", ""),BOX_SKU_RELATION("7206", "箱子和库位不存在关联", ""),BOX_SKU_NULL("7206", "仓库中暂无该商品", ""),
	SHELF_NUMBER("7301", "库位编码已存在", ""),SHELF_NUMBER_NULL("7302", "库位编码不存在", ""),
	BOX_NUMBER("7401", "箱位编码已存在", ""),BOX_NUMBER_NULL("7402", "不存在此箱号", ""),
	PICKING_HAVEN("7500", "有货", ""),
	PICKING_WAIT("7501", "待捡货", ""),PICKING_LOST("7502", "缺货", ""),PICKING_FINISH("7503", "完成", ""),
	PICKING_OUT_FAIL("7504", "存在缺货情况，暂不能出库", ""),
	SKU_NUMBER("7601", "商品变体编码已存在", ""),
	SKU_NUMBER_NULL("7602", "商品变体编码不存在", ""),DOWNLOAD_BACKGROUND("7603", "正在后台下载", "download_backgroud")
	;
	private String code;
	private String message;
	private String EnMessage;

	private AppInventoryCode(String code, String message, String enMessage) {
		this.code = code;
		this.message = message;
		EnMessage = enMessage;
	}

	public static Map<String, AppInventoryCode> getCodeMap() {
		Map<String, AppInventoryCode> result = new LinkedHashMap<String, AppInventoryCode>();
		for (AppInventoryCode discountType : AppInventoryCode.values()) {
			result.put(discountType.getCode() + "", discountType);
		}
		return result;
	}

	public static Map<String, AppInventoryCode> getMessageMap() {
		Map<String, AppInventoryCode> result = new LinkedHashMap<String, AppInventoryCode>();
		for (AppInventoryCode discountType : AppInventoryCode.values()) {
			result.put(discountType.getMessage() + "", discountType);
		}
		return result;
	}

	public static Map<String, AppInventoryCode> getEnMessageMap() {
		Map<String, AppInventoryCode> result = new LinkedHashMap<String, AppInventoryCode>();
		for (AppInventoryCode discountType : AppInventoryCode.values()) {
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
