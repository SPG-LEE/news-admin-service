package sq.enumeration;

public enum SplitMethod {

	LEFT("左"), RIGHT("右");
	private String name;

	private SplitMethod(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
