package sq.enumeration;

public enum RechargeStatus {
	OPEN(0), PAY(1), SUCCESS (2), FAILURE(3),CANCEL(4);
	private int index;

	private RechargeStatus(int index) {
		this.setIndex(index);
	}

	public static RechargeStatus getStatus(int index) {
		switch (index) {
		case 0:
			return OPEN;
		case 1:
			return PAY;
		case 2:
			return SUCCESS;
		case 3:
			return FAILURE;
		case 4:
			return CANCEL;
		}
		return null;
	}
	public String getName() {
		switch (index) {
		case 0:
			return "等待付款";
		case 1:
			return "等待充值";
		case 2:
			return "充值成功";
		case 3:
			return "充值失败";
		case 4:
			return "交易关闭";
		}
		return null;
	}
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

}
