package org.bea.controller;

import lombok.RequiredArgsConstructor;
import org.bea.configuration.ResourceRootPathConfiguration;
import org.bea.dto.PostRequest;
import org.bea.model.Post;
import org.bea.service.AddPostHandler;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Controller
@RequiredArgsConstructor
public class AddPostController {

    private final ResourceRootPathConfiguration rootPath;
    private final AddPostHandler addPostHandler;

    @GetMapping(path = "/posts/add")
    public String addPost() {
        return "add-post";
    }

    @PostMapping(value = "/posts", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String addPost(@ModelAttribute PostRequest postRequest) {
        try {
            var path = Path.of(
                    rootPath.getRootPathTo(ResourceRootPathConfiguration.IMAGES)
                            + File.separator
                            + postRequest.image().getOriginalFilename());
            Files.copy(postRequest.image().getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            addPostHandler.addPost(
                    postRequest.title(),
                    postRequest.text(),
                    postRequest.tags(),
                    postRequest.image().getOriginalFilename());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/posts";
    }

}
