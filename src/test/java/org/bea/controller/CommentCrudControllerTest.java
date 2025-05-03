package org.bea.controller;

import org.bea.config.DataSourceConfigurationTest;
import org.bea.config.WebConfigurationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class CommentCrudControllerTest extends CommonControllerContext{

    @Test
    void addComment() throws Exception {
        mockMvc.perform(get("/posts/add"))
                .andExpect(status().isOk());
    }

    /**
     * примеры
     *     @Test
     *     void getUsers_shouldReturnHtmlWithUsers() throws Exception {
     *         mockMvc.perform(get("/users"))
     *                 .andExpect(status().isOk())
     *                 .andExpect(content().contentType("text/html;charset=UTF-8"))
     *                 .andExpect(view().name("users"))
     *                 .andExpect(model().attributeExists("users"))
     *                 .andExpect(xpath("//table/tbody/tr").nodeCount(2))
     *                 .andExpect(xpath("//table/tbody/tr[1]/td[2]").string("Иван"));
     *     }
     *
     *     @Test
     *     void save_shouldAddUserToDatabaseAndRedirect() throws Exception {
     *         mockMvc.perform(post("/users")
     *                         .param("id", "4")
     *                         .param("firstName", "Анна")
     *                         .param("lastName", "Смирнова")
     *                         .param("age", "28")
     *                         .param("active", "true"))
     *                 .andExpect(status().is3xxRedirection())
     *                 .andExpect(redirectedUrl("/users"));
     *     }
     */



}
