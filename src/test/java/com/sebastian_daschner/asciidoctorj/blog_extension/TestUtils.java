package com.sebastian_daschner.asciidoctorj.blog_extension;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Paths;

public final class TestUtils {

    private TestUtils() {
        // no instances allowed
        throw new UnsupportedOperationException();
    }

    public static File getResourceFile(final String fileName) throws URISyntaxException {
        return Paths.get(TestUtils.class.getResource(fileName).toURI()).toFile();
    }

}
