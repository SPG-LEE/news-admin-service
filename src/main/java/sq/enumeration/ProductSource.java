package sq.enumeration;

import java.util.HashMap;
import java.util.Map;

public enum ProductSource {

	NONE(0, "未知"), Ali_1688(1, "1688"), ALI_EXPRESS(1, "速卖通"), AMAZON(1, "亚马逊"), LEKANI(
			1, "莱卡尼");

	private String name;
	private int index;

	private static Map<String, ProductSource> valueMap;

	private ProductSource(int index, String name) {
		this.index = index;
		this.name = name;
	}

	public static ProductSource getSource(String name) {
		if (valueMap == null) {
			init();
		}
		return valueMap.get(name) == null ? NONE : valueMap.get(name);
	}

	private static synchronized void init() {
		if (valueMap == null) {
			valueMap = new HashMap<String, ProductSource>();
			valueMap.put(Ali_1688.name, Ali_1688);
			valueMap.put(ALI_EXPRESS.name, ALI_EXPRESS);
			valueMap.put(AMAZON.name, AMAZON);
			valueMap.put(LEKANI.name, LEKANI);
		}
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
}