package sq.enumeration;

public enum Platform {

	WISH(0, "Wish"), AMAZON(1, "Amazon"), EBAY(2, "Ebay"), OTHER(3,"其他"),HAND_CREATE(4,"手动创建");
	private String name;
	private int index;

	private Platform(int index, String name) {
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