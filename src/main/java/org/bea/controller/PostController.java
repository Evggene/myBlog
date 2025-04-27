package org.bea.controller;

import lombok.RequiredArgsConstructor;
import org.bea.model.User;
import org.bea.service.PostService;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/")
    public String users(Model model) {
        var res = postService.findAll();
        model.addAttribute("posts", res);
        return "posts";
    }
}
