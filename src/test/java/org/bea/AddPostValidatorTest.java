package org.bea;

import org.bea.validator.AddPostValidator;
import org.junit.jupiter.api.Test;

public class AddPostValidatorTest {

    @Test
    void validatePostRequest_emptyTitle_throw() {
        AddPostValidator.validatePostRequest(null);
    }


}
