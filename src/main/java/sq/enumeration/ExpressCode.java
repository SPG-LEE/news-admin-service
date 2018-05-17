package sq.enumeration;

public enum ExpressCode {
	EMS(0), STO(1), YTO(2), ZTO(3), SF(4), YD(5), HHTT(6), ZJS(7), YZPY(8), HTKY(
			9), QFKD(10);

	private int index;

	private ExpressCode(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

	public static ExpressCode getStatus(int index) {
		switch (index) {
		case 0:
			return EMS;
		case 1:
			return STO;
		case 2:
			return YTO;
		case 3:
			return ZTO;
		case 4:
			return SF;
		case 5:
			return YD;
		case 6:
			return HHTT;
		case 7:
			return ZJS;
		case 8:
			return YZPY;
		case 9:
			return HTKY;
		case 10:
			return QFKD;
		}
		return null;
	}

	public String getName() {
		switch (index) {
		case 0:
			return "EMS";
		case 1:
			return "申通快递";
		case 2:
			return "圆通快递";
		case 3:
			return "中通速递";
		case 4:
			return "顺丰快递";
		case 5:
			return "韵达快递";
		case 6:
			return "天天快递";
		case 7:
			return "宅急送";
		case 8:
			return "邮政平邮/小包";
		case 9:
			return "百世汇通";
		case 10:
			return "全峰快递";
		}
		return "未知";
	}

	public static int getCodeIndex(String code) {
		for (ExpressCode expressCode : ExpressCode.values()) {
			if (expressCode.toString().equalsIgnoreCase(code)) {
				return expressCode.index;
			}
		}
		return -1;
	}

}
