package org.bea.controller;

import lombok.RequiredArgsConstructor;
import org.bea.dto.PostRequest;
import org.bea.service.AddPostHandler;
import org.bea.util.FileStorageService;
import org.bea.util.SafeNull;
import org.bea.validator.AddPostValidator;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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
    public String addPost(@ModelAttribute PostRequest postRequest, Model model) {

        var ex = AddPostValidator.validatePostRequest(postRequest);
        if (!ex.isBlank()) {
            model.addAttribute("error", ex);
            return "error-page";
        }
        fileStorageService.copyImageToResources(postRequest.image());
        addPostHandler.addPost(
                postRequest.title(),
                postRequest.text(),
                postRequest.tags(),
                SafeNull.getOrNull(() -> postRequest.image().getOriginalFilename()));
        return "redirect:/posts";
    }
}
