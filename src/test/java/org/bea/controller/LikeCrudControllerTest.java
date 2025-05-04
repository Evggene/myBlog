package org.bea.controller;

import org.bea.service.LikeActionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LikeCrudControllerTest extends CommonControllerContext{

    @Mock
    LikeActionHandler likeActionHandler;
    @InjectMocks
    LikeCrudController likeCrudController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(likeCrudController).build();
    }

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
