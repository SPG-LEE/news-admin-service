package sq.enumeration;

public enum StoreType {
	PURCHASING_INSTORE("采购入库"), REJECT_INSTORE("驳回入库"), BACKGOODS_INSTORE(
			"退货入库 "), TRANSFER_INSTORE("调拨入库 "), CHECK_INSTORE(
					"盘点入库"), OTHER_INSTORE("其它入库"), ORDER_OUTSTORE(
							"订单出库"), DEMAGE_OUTSTORE("报损出库"), MODEL_OUTSTORE(
									"拿样出库 "), FIX_OUTSTORE(
											"返修出库"), TRANSFER_OUTSTORE(
													"调拨出库 "), CHECK_OUTSTORE(
															"盘点出库"), OTHER_OUTSTORE(
																	"其它出库");
	private String name;

	// 已完成 finish 待拣货 wait 缺货lost 被撤销 reback ->枚举 默认是待拣货
	private StoreType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
