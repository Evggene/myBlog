package org.bea.controller;

import lombok.RequiredArgsConstructor;
import org.bea.configuration.ResourceRootPathConfiguration;
import org.bea.db.entity.PostAggregate;
import org.bea.db.repository.PostAggregateRepository;
import org.bea.dto.PageOfPostsResponse;
import org.bea.model.Post;
import org.bea.db.dao.PostDao;
import org.bea.service.FindPostHandler;
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
import java.util.ArrayList;
import java.util.List;
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
