package sq.enumeration;

import java.util.LinkedHashMap;
import java.util.Map;

public enum Currency {

	NONE(0, "未知", "？", 0), CNY(1, "人民币", "￥", 1), USD(2, "美元", "$", 6.6296), CAD(
			3, "加币", "C$", 5.1944), GBP(4, "英镑", "£", 8.7458), EUR(5, "欧元",
			"€", 7.7991), THB(6, "泰铢", "฿", 0.2012),MXN(7,"墨西哥元","Mex$",0.3380);

	private String name;
	private int index;
	private String symbol;
	private double rate;// 对人民币汇率

	private Currency(int index, String name, String symbol, double rate) {
		this.index = index;
		this.name = name;
		this.symbol = symbol;
		this.rate = rate;
	}

	public static Map<String, Currency> getSymbolMap() {
		Map<String, Currency> result = new LinkedHashMap<String, Currency>();
		for (Currency discountType : Currency.values()) {
			result.put(discountType.getSymbol() + "", discountType);
		}
		return result;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

}