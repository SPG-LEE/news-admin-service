package sq.enumeration;

public enum SendMethod {
	JNE(0), POSTINDONESIA(1), JNT(2), NOEXPRESS(3);
	private int index;

	private SendMethod(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

	public static SendMethod getSendMethod(int index) {
		switch (index) {
		case 0:
			return JNE;
		case 1:
			return POSTINDONESIA;
		case 2:
			return JNT;
		case 3:
			return NOEXPRESS;

		}
		return NOEXPRESS;
	}

	public String getName() {
		switch (index) {
		case 0:
			return "JNE";
		case 1:
			return "POSTINDONESIA";
		case 2:
			return "JNT";
		case 3:
			return "无需物流";
		}
		return "无需物流";
	}
}
