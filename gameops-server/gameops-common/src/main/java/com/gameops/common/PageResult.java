package com.gameops.common;

import java.util.List;

public class PageResult<T> {
    private long total;
    private int pageNum;
    private int pageSize;
    private List<T> records;

    public PageResult(long total, int pageNum, int pageSize, List<T> records) {
        this.total = total;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.records = records;
    }
    public long getTotal() { return total; }
    public int getPageNum() { return pageNum; }
    public int getPageSize() { return pageSize; }
    public List<T> getRecords() { return records; }
}
