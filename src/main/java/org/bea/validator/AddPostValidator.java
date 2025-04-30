package org.bea.validator;

import org.bea.dto.PostRequest;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

public class AddPostValidator {

    public static void validatePostRequest(PostRequest postRequest, Model model) {
        try {
            if (postRequest.title().isBlank()) {
                throw new RuntimeException("Empty title");
            }
            if (postRequest.text().isBlank()) {
                throw new RuntimeException("Empty text");
            }
        } catch (RuntimeException ex) {
            model.addAttribute("error", ex.getMessage());
            throw ex;
        }
    }
}
