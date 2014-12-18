package com.sebastian_daschner.asciidoctorj.blog_extension;

import org.asciidoctor.Asciidoctor;
import org.asciidoctor.OptionsBuilder;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Paths;

public class BlogEntriesProcessorTest {

    private Asciidoctor asciidoctor;

    @Before
    public void setUp() {
        asciidoctor = Asciidoctor.Factory.create();
        asciidoctor.javaExtensionRegistry().blockMacro("entries", BlogEntriesProcessor.class);
    }

    @Test
    @Ignore
    public void testProcess() throws Exception {
        final File file = getResourceFile("/asciidoc/index/index.adoc");
        final File baseDir = getResourceFile("/asciidoc/index/");
        final File templateDir = getResourceFile("/templates/index/");

        final String output = asciidoctor.convertFile(file, OptionsBuilder.options().baseDir(baseDir).templateDir(templateDir));
        System.out.println(output);
    }

    private File getResourceFile(final String fileName) throws URISyntaxException {
        return Paths.get(getClass().getResource(fileName).toURI()).toFile();
    }

}