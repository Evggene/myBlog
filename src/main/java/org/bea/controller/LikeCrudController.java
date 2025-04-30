package org.bea.controller;

import lombok.RequiredArgsConstructor;
import org.bea.db.dao.LikeRepository;
import org.bea.model.Comment;
import org.bea.service.LikeHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class LikeCrudController {

    private final LikeHandler likeHandler;

    @PostMapping("/posts/{postId}/like")
    public String likeIncDec(
            @PathVariable("postId") UUID postId,
            @RequestParam("like") boolean isIncrement) {
        likeHandler.handle(postId, isIncrement);
        return "redirect:/posts/" + postId;
    }
}
