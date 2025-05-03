package org.bea.controller;

import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

public class FindPostControllerTest extends CommonControllerContext{

    @Test
    void addComment() throws Exception {
        mockMvc.perform(get("/posts/550e8400-e29b-41d4-a716-446655440001"))
                .andExpect(status().isOk())
                .andExpect(xpath("/html/body/table/tr[2]/td/p[3]/span").string("#Путешествия "));
    }

}
