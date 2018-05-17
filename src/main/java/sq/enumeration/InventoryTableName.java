package sq.enumeration;

public enum InventoryTableName {
    OTHER_INSTORE("其它入库单"), OTHER_OUTSTORE("其它出库单"), TRANSFER("调拨单"),CHECK("盘点单");
    private String name;
    private InventoryTableName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
