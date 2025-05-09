package org.bea.controller;

import lombok.RequiredArgsConstructor;
import org.bea.db.repository.PostRepository;
import org.bea.dto.AddEditPostRequest;
import org.bea.service.AddPostHandler;
import org.bea.service.DeletePostHandler;
import org.bea.service.EditPostHandler;
import org.bea.util.FileStorageService;
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
public class PostCrudController {

    private final DeletePostHandler deletePostHandler;
    private final AddPostHandler addPostHandler;
    private final EditPostHandler editPostHandler;
    private final PostRepository postRepository;
    private final FileStorageService fileStorageService;

    @GetMapping(path = "/posts/add")
    public String addPost() {
        return "add-post";
    }

    @PostMapping(value = "/posts", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String addPost(@ModelAttribute AddEditPostRequest addEditPostRequest, Model model) {

        var ex = AddPostValidator.validatePostRequest(addEditPostRequest);
        if (!ex.isBlank()) {
            model.addAttribute("error", ex);
            return "error-page";
        }
        fileStorageService.copyImageToResources(addEditPostRequest.image());
        addPostHandler.addPost(
                addEditPostRequest.title(),
                addEditPostRequest.text(),
                addEditPostRequest.tags(),
                addEditPostRequest.image().getOriginalFilename());
        return "redirect:/posts";
    }

    @GetMapping("/posts/{id}/edit")
    public String getToEdit(@PathVariable("id") UUID id, Model model) {
       var post = postRepository.findByIdFullMode(id);
       model.addAttribute("post", post);
       return "add-post";
    }

    @PostMapping(value = "/posts/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String editPost(
            @PathVariable("id") UUID id,
            @ModelAttribute AddEditPostRequest addEditPostRequest,
            Model model) {
        var ex = AddPostValidator.validatePostRequest(addEditPostRequest);
        if (!ex.isBlank()) {
            model.addAttribute("error", ex);
            return "error-page";
        }
        if (!addEditPostRequest.image().getOriginalFilename().isEmpty()) {
            fileStorageService.copyImageToResources(addEditPostRequest.image());
        }
        editPostHandler.editPost(
                id,
                addEditPostRequest.title(),
                addEditPostRequest.text(),
                addEditPostRequest.tags(),
                addEditPostRequest.image().getOriginalFilename());
        return "redirect:/posts";
    }

    @PostMapping(value = "/posts/{id}/delete")
    public String deletePost(
            @PathVariable("id") UUID id) {
        deletePostHandler.deletePost(id);
        return "redirect:/posts";
    }
}
