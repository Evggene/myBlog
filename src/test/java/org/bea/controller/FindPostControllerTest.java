package org.bea.controller;

import org.bea.config.CommonControllerTest;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

public class FindPostControllerTest extends CommonControllerTest {

    @Test
    public void testFindByTags_WithSearchParam() throws Exception {
        mockMvc.perform(get("/posts")
                        .param("search", "Здоровье")
                        .param("postSize", "10")
                        .param("pageNumber", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("posts"))
                .andExpect(xpath("/html/body/table/tr[2]/td/h2")
                        .string("Как улучшить качество сна: 5 научно доказанных методов"))
                .andExpect(model().attributeExists("posts"))
                .andExpect(model().attributeExists("paging"));
    }

    @Test
    public void testFindByTags_WithoutSearchParam() throws Exception {
        mockMvc.perform(get("/posts")
                        .param("search", "error")
                        .param("postSize", "10")
                        .param("pageNumber", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("error-page"));
    }

    @Test
    public void testGetPostById_Success() throws Exception {
        mockMvc.perform(get("/posts/550e8400-e29b-41d4-a716-446655440001"))
                .andExpect(status().isOk())
                .andExpect(view().name("post"))
                .andExpect(model().attributeExists("post"));
    }
}
