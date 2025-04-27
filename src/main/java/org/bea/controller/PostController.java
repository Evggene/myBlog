package org.bea.controller;

import lombok.RequiredArgsConstructor;
import org.bea.dao.Page;
import org.bea.model.Post;
import org.bea.repository.PostRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostRepository postRepository;

    @GetMapping("/")
    public String users(Model model) {
        var res = postRepository.findAll(0);
        var count = postRepository.getCount();
        var page = new Page<Post>();
        page.setCount(count);
        model.addAttribute("posts", res);
        model.addAttribute("paging", page);
        return "posts";
    }

    @GetMapping(path = "/", params = {"search", "action"})
    public String handleSearch(@RequestParam("search") String search, Model model) {
        if (search != null && search.isBlank()) {
            var res = postRepository.findAll(0);
            var count = postRepository.getCount();
            var page = new Page<Post>();
            page.setCount(count);
            model.addAttribute("posts", res);
            model.addAttribute("paging", page);
        }
        return "posts";
    }
}
