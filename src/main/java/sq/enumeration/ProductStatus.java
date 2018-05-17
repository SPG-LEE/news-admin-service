package sq.enumeration;

public enum ProductStatus {
	UP(1, "上架"), OFF(2, "下架"), OPTION(3, "审核中"), UNOPTION(4, "审核未通过");
	private int index;
	private String name;

	private ProductStatus(int index, String name) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
