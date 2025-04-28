package org.bea.dao;

import org.springframework.web.multipart.MultipartFile;

public record PostRequest(
        String title,
        MultipartFile image,
        String tags,
        String text
) { }
