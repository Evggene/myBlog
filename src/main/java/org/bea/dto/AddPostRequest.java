package org.bea.dto;

import org.springframework.web.multipart.MultipartFile;

public record AddPostRequest(
        String title,
        MultipartFile image,
        String tags,
        String text
) { }
