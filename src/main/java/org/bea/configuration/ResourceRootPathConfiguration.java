package org.bea.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;

@Service
@RequiredArgsConstructor
public class ResourceRootPathConfiguration {

    private final ResourceLoader resourceLoader;

    public static final String IMAGES = "resources/images";

    public Path getRootPathTo(String folderName) throws IOException {
        var resource = resourceLoader.getResource(folderName);
        return Path.of(resource.getFile().getAbsolutePath());
    }
}
