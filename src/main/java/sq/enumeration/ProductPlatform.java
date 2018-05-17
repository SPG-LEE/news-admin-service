package sq.enumeration;

public enum ProductPlatform {

	WISH(0, "Wish"), AMAZON(1, "Amazon"), EBAY(2, "Ebay"), ALI_EXPRESS(3, "速卖通"),HAND_CREATE(4,"手动创建");
	private String name;
	private int index;

	private ProductPlatform(int index, String name) {
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