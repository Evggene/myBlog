package org.bea.validator;

import org.bea.dto.PostRequest;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

public class AddPostValidator {

    public static String validatePostRequest(PostRequest postRequest) {
        if (postRequest == null) {
            return "empty request";
        }
        if (postRequest.title() == null || postRequest.title().isBlank()) {
            return "Empty title";
        }
        if (postRequest.text() == null || postRequest.text().isBlank()) {
            return "Empty text";
        }
        return "";
    }
}
