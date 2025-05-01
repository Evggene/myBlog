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
    private final ResourceRootPathConfiguration resourceRootPathConfiguration;

    @GetMapping("posts")
    public String posts(Model model) {
        var res = postAggregateRepository.findAll(0);
        var count = postDao.getCount();
        var page = new PageOfPostsResponse<Post>();
        page.setCount(count);
        model.addAttribute("posts", res);
        model.addAttribute("paging", page);
        return "posts";
    }

    @GetMapping(path = "/", params = {"search", "postSize", "pageNumber"})
    public String handleSearch(@RequestParam("search") String search, Model model) {
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

    @GetMapping("/images/{id}")
    public ResponseEntity<Resource> getImage(@PathVariable("id") UUID id) throws IOException {
        var post = postDao.getById(id);
        var rootPath = resourceRootPathConfiguration.getRootPathTo(ResourceRootPathConfiguration.IMAGES);
        Path imagePath = Paths.get(rootPath + File.separator + post.getImagePath());
        Resource resource = new UrlResource(imagePath.toUri());
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(resource);
    }


}
