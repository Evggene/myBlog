package org.bea.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Builder
@Getter
@Data
public class PageOfPostsResponse {

    private long count;
    private int pageNumber; // offset -1
    private int postSize;  // limit
    public boolean hasNext() {
        return count - ((long) pageNumber * postSize) > 0;
    }
    public boolean hasPrevious() {
        return pageNumber != 1;
    }

}
