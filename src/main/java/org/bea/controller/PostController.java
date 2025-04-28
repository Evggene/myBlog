package org.bea.controller;

import lombok.RequiredArgsConstructor;
import org.bea.dao.PageResponse;
import org.bea.dao.PostRequest;
import org.bea.model.Post;
import org.bea.repository.PostRepository;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostRepository postRepository;

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

    @GetMapping(path = "/posts/add")
    public String addPost() {
        return "add-post";
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

    @PostMapping(value = "/posts", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String addPost(
            @RequestParam("title") String title,
            @RequestParam("image") MultipartFile image,
            @RequestParam("tags") String tags,
            @RequestParam("text") String text) {
        return "redirect:/posts";
    }


}
