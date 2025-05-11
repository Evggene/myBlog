package org.bea.controller;

import org.bea.config.CommonControllerTest;
import org.bea.service.LikeActionHandler;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LikeCrudControllerTest extends CommonControllerTest {

    @MockitoBean
    LikeActionHandler likeActionHandler;

    @Test
    public void testLikeIncrement() throws Exception {
        UUID postId = UUID.randomUUID();

        mockMvc.perform(post("/posts/{postId}/like", postId)
                        .param("like", "true"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/posts/" + postId));

        verify(likeActionHandler).handle(postId, true);
    }

    @Test
    public void testLikeDecrement() throws Exception {
        UUID postId = UUID.randomUUID();

        mockMvc.perform(post("/posts/{postId}/like", postId)
                        .param("like", "false"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/posts/" + postId));

        verify(likeActionHandler).handle(postId, false);
    }

}
