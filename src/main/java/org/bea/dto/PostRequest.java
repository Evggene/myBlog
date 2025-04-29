package org.bea.dto;

import org.springframework.web.multipart.MultipartFile;

public record PostRequest(
        String title,
        MultipartFile image,
        String tags,
        String text
) { }
