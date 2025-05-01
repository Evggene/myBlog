package org.bea.dto;

import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class PageOfPostsResponse {

    private long count;
    private int pageNumber = 1; // offser -1
    private int postSize = 10;  // limit
    public boolean hasNext() {
        return true;
    }
    public boolean hasPrevious() {
        return true;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public void setPostSize(int postSize) {
        this.postSize = postSize;
    }
}
