package org.bea.dao;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PageRequest {

    private int postSize = 10; // limit
    private int pageNumber = 1; // offset

}
