package sq.enumeration;

public enum ProductStage {

	CRAWL(0, "采集库"), BASIC(1, "商品库"), LISTING(2, "LISTING"), TEMP(4, "WISHTENP"), READY(
			8, "待发布");

	private String name;
	private int index;

	private ProductStage(int index, String name) {
		this.index = index;
		this.name = name;
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