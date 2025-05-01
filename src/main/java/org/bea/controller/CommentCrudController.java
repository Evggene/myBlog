package org.bea.controller;

import lombok.RequiredArgsConstructor;
import org.bea.db.dao.CommentRepository;
import org.bea.model.Comment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class CommentCrudController {

    private final CommentRepository commentRepository;

    @PostMapping("/posts/{postId}/comments")
    public String addComment(
            @PathVariable("postId") UUID postId,
            @RequestParam("text") String text) {
        var newComment = Comment.builder()
                .postId(postId)
                .content(text)
                .build();
        commentRepository.setIdAndInsert(newComment);
        return "redirect:/posts/" + postId;
    }

    @PostMapping("/posts/{postId}/comments/{commentId}")
    public String editComment(
            @PathVariable("postId") UUID postId,
            @PathVariable("commentId") UUID commentId,
            @RequestParam("text") String text) {
        var newComment = Comment.builder()
                .id(commentId)
                .postId(postId)
                .content(text)
                .build();
        commentRepository.update(newComment);
        return "redirect:/posts/" + postId;
    }

    @PostMapping("/posts/{postId}/comments/{commentId}/delete")
    public String deleteComment(
            @PathVariable("postId") UUID postId,
            @PathVariable("commentId") UUID commentId) {
        commentRepository.delete(commentId, "id");
        return "redirect:/posts/" + postId;
    }

}
