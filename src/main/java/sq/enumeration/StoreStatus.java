package sq.enumeration;

public enum StoreStatus {

	UNREVIEWED("待审核"), REVIEWED("已审核"), CANCEL("已作废");
	private String name;

	private StoreStatus(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
