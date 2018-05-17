package sq.enumeration;

public enum LogType {

	ACTIVITY(0), PRODUCT(1), USER(2);

	private final int index;

	private LogType(final int index) {
		this.index = index;
	}

	public int getIndex() {
		return this.index;
	}

	public static LogType getByIndex(final int index) {
		switch (index) {
			case 0 :
				return ACTIVITY;
			case 1 :
				return PRODUCT;
			case 2 :
				return USER;
		}
		return ACTIVITY;
	}

	public String getName() {
		switch (this.index) {
			case 0 :
				return "巡展操作";
			case 1 :
				return "商品操作";
			case 2 :
				return "用户操作";
		}
		return "巡展操作";
	}

}
