package org.bea.validator;

import org.bea.dto.AddEditPostRequest;

public class AddPostValidator {

    public static String validatePostRequest(AddEditPostRequest addEditPostRequest) {
        if (addEditPostRequest == null) {
            return "empty request";
        }
        if (addEditPostRequest.title() == null || addEditPostRequest.title().isBlank()) {
            return "Empty title";
        }
        if (addEditPostRequest.text() == null || addEditPostRequest.text().isBlank()) {
            return "Empty text";
        }
        return "";
    }
}
