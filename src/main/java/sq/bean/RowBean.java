package sq.erp.admin.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RowBean {
    @JsonProperty
    private long id;
    @JsonProperty
    private String width;
    @JsonProperty
    private int sort;

    public long getId() {
        return id;
    }

    public RowBean setId(long id) {
        this.id = id;
        return this;
    }

    public String getWidth() {
        return width;
    }

    public RowBean setWidth(String width) {
        this.width = width;
        return this;
    }

    public int getSort() {
        return sort;
    }

    public RowBean setSort(int sort) {
        this.sort = sort;
        return this;
    }
}
