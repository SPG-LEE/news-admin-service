package sq.enumeration;

public enum PayMethod {
	DELIVER(0), ONLINE(1), MAIL(2), BANK(3), COMPANY(4), SELF(5), PREPAY(6),WEIXIN(7),ALIPAY(8);
	private int index;

	private PayMethod(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

	public static PayMethod getPayMethod(int index) {
		switch (index) {
		case 0:
			return DELIVER;
		case 1:
			return ONLINE;
		case 2:
			return MAIL;
		case 3:
			return BANK;
		case 4:
			return COMPANY;
		case 5:
			return SELF;
		case 6:
			return PREPAY;
		case 7:
			return WEIXIN;
		case 8:
			return ALIPAY;
		}
		return WEIXIN;
	}

	public String getName() {
		switch (index) {
		case 0:
			return "货到付款";
		case 1:
			return "在线支付";
		case 2:
			return "邮局汇款";
		case 3:
			return "银行卡转账";
		case 4:
			return "公司转账";
		case 5:
			return "客户自提";
		case 6:
			return "预付款支付";
		case 7:
			return "微信支付";
		case 8:
			return "支付宝支付";
		}
		return "微信支付";
	}
}
