package sq.enumeration;

public enum PayAgent {
	NONE(0), ALIPAY(1), HUIFU(2), WEIXIN(3);
	private int index;

	private PayAgent(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

	public static PayAgent getPayMethod(int index) {
		switch (index) {
		case 0:
			return NONE;
		case 1:
			return ALIPAY;
		case 2:
			return HUIFU;
		case 3:
			return WEIXIN;
		}

		return NONE;
	}

	public String getName() {
		switch (index) {
		case 0:
			return "未知";
		case 1:
			return "支付宝";
		case 2:
			return "汇付天下";
		case 3:
			return "微信";
		}
		return "未知";
	}
}
