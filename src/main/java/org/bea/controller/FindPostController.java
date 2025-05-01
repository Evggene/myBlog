package org.bea.controller;

import lombok.RequiredArgsConstructor;
import org.bea.configuration.ResourceRootPathConfiguration;
import org.bea.db.entity.PostAggregate;
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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class FindPostController {

    private final PostAggregateRepository postAggregateRepository;
    private final PostDao postDao;

    @GetMapping(path = "/posts")
    public String findByTags(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "action", required = false) String action,
            @RequestParam(value = "postSize", required = false) Integer postSize,
            @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
            Model model) {
        var customPostSize = postSize == null ? 10 : postSize;
        var customSearch = search == null ? "" : search;
        var customPageNumber = pageNumber == null ? 0 : pageNumber - 1;
        List<PostAggregate> result = new ArrayList<>();
        long count = 0;
        if (customSearch.isBlank()) {
            result = postAggregateRepository.findAll(customPageNumber * customPostSize, customPostSize);
            count = postDao.getCount();
        } else {

        }
        var page = new PageOfPostsResponse();
        page.setCount(count);
        page.setPageNumber(customPageNumber + 1);
        page.setPostSize(customPostSize);
        model.addAttribute("posts", result);
        model.addAttribute("paging", page);
        return "posts";
        }

    @GetMapping("/posts/{id}")
    public String getPostById(@PathVariable("id") UUID id, Model model) {
        var post = postAggregateRepository.findById(id);
        model.addAttribute("post", post);
        return "post";
    }
}
