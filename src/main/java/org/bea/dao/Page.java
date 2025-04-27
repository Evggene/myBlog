package org.bea.dao;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Page<T> {

    private long count;
    private int pageNumber = 1;
    private int postSize = 10;
    public boolean hasNext() {
        return true;
    }
    public boolean hasPrevious() {
        return true;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPostSize() {
        return postSize;
    }

    public void setPostSize(int postSize) {
        this.postSize = postSize;
    }
}
