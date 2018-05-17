package sq.enumeration;

public enum SendTime {
	ANYTIME(0), WEEKEND(1), WORKTIME(2);
	private int index;

	private SendTime(int index) {
		this.index = index;

	}

	public int getIndex() {
		return index;
	}

	public static SendTime getSendTime(int index) {
		switch (index) {
		case 0:
			return ANYTIME;
		case 1:
			return WEEKEND;
		case 2:
			return WORKTIME;
		}
		return ANYTIME;
	}

	public String getName() {
		switch (index) {
		case 0:
			return "没有特别的时间要求";
		case 1:
			return "需要在周六和周日任何时间送货";
		case 2:
			return "需要在周一到周五工作时间送货";
		}
		return "没有特别的时间要求";
	}
}
