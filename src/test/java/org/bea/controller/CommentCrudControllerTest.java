package org.bea.controller;

import org.bea.config.CommonControllerTest;
import org.bea.db.dao.CommentDao;
import org.bea.db.entity.CommentEntity;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CommentCrudControllerTest extends CommonControllerTest{

    @MockBean
    CommentDao commentDao;

    @Test
    public void testAddComment() throws Exception {
        UUID postId = UUID.randomUUID();
        String commentText = "Test comment";

        mockMvc.perform(post("/posts/{postId}/comments", postId)
                        .param("text", commentText))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/posts/" + postId));

        verify(commentDao).setIdAndInsert(ArgumentMatchers.any(CommentEntity.class));
    }

    @Test
    void editComment_ShouldUpdateAndRedirect() throws Exception {
        UUID postId = UUID.fromString("550e8400-e29b-41d4-a716-446655440001");
        UUID commentId = UUID.randomUUID();
        String updatedText = "Updated comment text";

        mockMvc.perform(post("/posts/{postId}/comments/{commentId}", postId, commentId)
                        .param("text", updatedText))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/posts/" + postId));

        verify(commentDao).update(argThat(comment ->
                comment.getId().equals(commentId) &&
                        comment.getPostId().equals(postId) &&
                        comment.getContent().equals(updatedText)
        ));
    }

    @Test
    void deleteComment_ShouldDeleteAndRedirect() throws Exception {
        UUID postId = UUID.fromString("550e8400-e29b-41d4-a716-446655440001");
        UUID commentId = UUID.randomUUID();

        mockMvc.perform(post("/posts/{postId}/comments/{commentId}/delete", postId, commentId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/posts/" + postId));

        verify(commentDao).delete(eq(commentId), eq("id"));
    }

}
