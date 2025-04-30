package org.bea.controller;

import lombok.RequiredArgsConstructor;
import org.bea.dto.AddPostRequest;
import org.bea.service.AddPostHandler;
import org.bea.util.FileStorageService;
import org.bea.util.SafeNull;
import org.bea.validator.AddPostValidator;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class AddPostController {

    private final AddPostHandler addPostHandler;
    private final FileStorageService fileStorageService;

    @GetMapping(path = "/posts/add")
    public String addPost() {
        return "add-post";
    }

    @PostMapping(value = "/posts", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String addPost(@ModelAttribute AddPostRequest addPostRequest, Model model) {

        var ex = AddPostValidator.validatePostRequest(addPostRequest);
        if (!ex.isBlank()) {
            model.addAttribute("error", ex);
            return "error-page";
        }
        fileStorageService.copyImageToResources(addPostRequest.image());
        addPostHandler.addPost(
                addPostRequest.title(),
                addPostRequest.text(),
                addPostRequest.tags(),
                SafeNull.getOrNull(() -> addPostRequest.image().getOriginalFilename()));
        return "redirect:/posts";
    }

    @GetMapping("/posts/{id}/edit")
    public String getAndEdit(@PathVariable("id") UUID id, Model model) {
//        var post = postRepository.getById(id);
//        model.addAttribute("post", post);
        return "add-post";
    }
}
