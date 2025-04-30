package org.bea;

import org.bea.validator.AddPostValidator;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.ModelMap;

public class AddPostValidatorTest {

    @Test
    void validatePostRequest_emptyTitle_throw() {
        AddPostValidator.validatePostRequest(null);
    }


}
