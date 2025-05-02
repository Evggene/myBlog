package org.bea.controller;

import lombok.RequiredArgsConstructor;
import org.bea.db.repository.PostAggregateRepository;
import org.bea.service.FindPostHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class FindPostController {

    private final PostAggregateRepository postAggregateRepository;
    private final FindPostHandler findPostHandler;

    @GetMapping(path = "/posts")
    public String findByTags(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "action", required = false) String action,
            @RequestParam(value = "postSize", required = false) Integer postSize,
            @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
            Model model) {
        var res = findPostHandler.findByTags(search, postSize, pageNumber);
        model.addAttribute("posts", res.posts());
        model.addAttribute("paging", res.pageOfPosts());
        return "posts";
        }

    @GetMapping("/posts/{id}")
    public String getPostById(@PathVariable("id") UUID id, Model model) {
        var post = postAggregateRepository.findById(id);
        model.addAttribute("post", post);
        return "post";
    }
}
