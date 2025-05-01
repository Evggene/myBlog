package org.bea.controller;

import lombok.RequiredArgsConstructor;
import org.bea.configuration.ResourceRootPathConfiguration;
import org.bea.db.repository.PostAggregateRepository;
import org.bea.dto.PageOfPostsResponse;
import org.bea.model.Post;
import org.bea.db.dao.PostDao;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class FindPostController {

    private final PostAggregateRepository postAggregateRepository;
    private final PostDao postDao;

    @GetMapping("posts")
    public String findAll(Model model) {
        var res = postAggregateRepository.findAll(0);
        var count = postDao.getCount();
        var page = new PageOfPostsResponse<Post>();
        page.setCount(count);
        model.addAttribute("posts", res);
        model.addAttribute("paging", page);
        return "posts";
    }

    @GetMapping(path = "/", params = {"search", "postSize", "pageNumber"})
    public String findByTags(@RequestParam("search") String search, Model model) {
        if (search != null && search.isBlank()) {
            var res = postAggregateRepository.findAll(0);
            var count = postDao.getCount();
            var page = new PageOfPostsResponse<Post>();
            page.setCount(count);
            model.addAttribute("posts", res);
            model.addAttribute("paging", page);
        }
        return "posts";
    }

    @GetMapping("/posts/{id}")
    public String getPostById(@PathVariable("id") UUID id, Model model) {
        var post = postAggregateRepository.findById(id);
        model.addAttribute("post", post);
        return "post";
    }
}
