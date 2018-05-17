package sq.enumeration;

public enum PurchasingTableName {
    DEMAND("需求单"), PURCHASING("采购单"), INSTORE("入库单");

    private String name;

    private PurchasingTableName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
