package sq.enumeration;

public enum OrderStatus {
	OPEN("待付款"), PAY("待发货"), UNDISPOSED("待处理"), SORTING("拣货包装"), SEND("已发货"), REFUND(
			"退款退货"), FINISH("已完成"), CANCEL("已取消");
	private String name;

	private OrderStatus(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public OrderStatus getNextStatus(OrderStatus orderStatus) {
		switch (orderStatus) {
		case OPEN:
			return PAY;
		case PAY:

			return SEND;
		case SEND:

			return FINISH;
		default:
			return orderStatus;
		}
	}
}
