package org.bea.validator;

import org.bea.dto.AddPostRequest;

public class AddPostValidator {

    public static String validatePostRequest(AddPostRequest addPostRequest) {
        if (addPostRequest == null) {
            return "empty request";
        }
        if (addPostRequest.title() == null || addPostRequest.title().isBlank()) {
            return "Empty title";
        }
        if (addPostRequest.text() == null || addPostRequest.text().isBlank()) {
            return "Empty text";
        }
        return "";
    }
}
