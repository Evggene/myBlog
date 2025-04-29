package org.bea.controller;

import lombok.RequiredArgsConstructor;
import org.bea.configuration.ResourceRootPathConfiguration;
import org.bea.dto.PageResponse;
import org.bea.model.Post;
import org.bea.db.repository.PostRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostRepository postRepository;
    private final ResourceRootPathConfiguration resourceRootPathConfiguration;

    @GetMapping("/")
    public String redirectToPosts() {
        return "redirect:/posts";
    }

    @GetMapping("posts")
    public String posts(Model model) {
        var res = postRepository.findAll(0);
        var count = postRepository.getCount();
        var page = new PageResponse<Post>();
        page.setCount(count);
        model.addAttribute("posts", res);
        model.addAttribute("paging", page);
        return "posts";
    }

    @GetMapping(path = "/", params = {"search", "postSize", "pageNumber"})
    public String handleSearch(@RequestParam("search") String search, Model model) {
        if (search != null && search.isBlank()) {
            var res = postRepository.findAll(0);
            var count = postRepository.getCount();
            var page = new PageResponse<Post>();
            page.setCount(count);
            model.addAttribute("posts", res);
            model.addAttribute("paging", page);
        }
        return "posts";
    }

    @GetMapping("/posts/{id}")
    public String getPostById(@PathVariable("id") UUID id, Model model) {
        var post = postRepository.getById(id);
        model.addAttribute("post", post);
        return "post";
    }

    @GetMapping("/posts/{id}/edit")
    public String getAndEdit(@PathVariable("id") UUID id, Model model) {
        var post = postRepository.getById(id);
        model.addAttribute("post", post);
        return "add-post";
    }

    @GetMapping("/images/{id}")
    public String getImage(@PathVariable("id") UUID id, Model model) {
        var post = postRepository.getById(id);
        model.addAttribute("post", post);
        return "add-post";
    }


}
