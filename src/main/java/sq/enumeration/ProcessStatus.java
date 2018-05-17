package sq.enumeration;

public enum ProcessStatus {
	START(0), PRINT(1), GLAZING(2), DECORATE(3), CHECK(4), PACKING(5), STORAGE(
			6), SEND(7), FINISH(8);
	private int index;

	private ProcessStatus(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

	public static ProcessStatus getStatus(int index) {
		switch (index) {
		case 0:
			return START;
		case 1:
			return PRINT;
		case 2:
			return GLAZING;
		case 3:
			return DECORATE;
		case 4:
			return CHECK;
		case 5:
			return PACKING;
		case 6:
			return STORAGE;
		case 7:
			return SEND;
		case 8:
			return FINISH;
		case -1:
			return null;

		}
		return null;
	}

	public String getName() {
		switch (index) {
		case 0:
			return "确认订单";
		case 1:
			return "画面输出";
		case 2:
			return "画面上光";
		case 3:
			return "装裱组装";
		case 4:
			return "入箱前总检";
		case 5:
			return "装箱";
		case 6:
			return "入库";
		case 7:
			return "已发货";
		case 8:
			return "完成";
		}
		return "未知";
	}

}
