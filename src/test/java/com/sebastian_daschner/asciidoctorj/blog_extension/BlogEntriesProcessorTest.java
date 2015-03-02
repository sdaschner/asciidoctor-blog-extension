package com.sebastian_daschner.asciidoctorj.blog_extension;

import org.asciidoctor.Asciidoctor;
import org.asciidoctor.OptionsBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class BlogEntriesProcessorTest {

    private Asciidoctor asciidoctor;

    @Before
    public void setUp() {
        asciidoctor = Asciidoctor.Factory.create();
    }

    @Test
    public void testProcessIndex() throws URISyntaxException, IOException {
        final File file = TestUtils.getResourceFile("/asciidoc/index/index.adoc");
        final File baseDir = TestUtils.getResourceFile("/asciidoc/index/");
        final File templateDir = TestUtils.getResourceFile("/templates/");

        asciidoctor.convertFile(file, OptionsBuilder.options().baseDir(baseDir).templateDir(templateDir));

        final Path expectedFile = TestUtils.getResourceFile("/asciidoc/index/expectedIndex.html").toPath();
        final Path actualFile = TestUtils.getResourceFile("/asciidoc/index/index.html").toPath();

        final String expectedOutput = Files.lines(expectedFile).collect(Collectors.joining());
        final String actualOutput = Files.lines(actualFile).collect(Collectors.joining());

        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testProcessEntries() throws URISyntaxException, IOException {
        final File file = TestUtils.getResourceFile("/asciidoc/index/entries.adoc");
        final File baseDir = TestUtils.getResourceFile("/asciidoc/index/");
        final File templateDir = TestUtils.getResourceFile("/templates/");

        asciidoctor.convertFile(file, OptionsBuilder.options().baseDir(baseDir).templateDir(templateDir));

        final Path expectedFile = TestUtils.getResourceFile("/asciidoc/index/expectedEntries.html").toPath();
        final Path actualFile = TestUtils.getResourceFile("/asciidoc/index/entries.html").toPath();

        final String expectedOutput = Files.lines(expectedFile).collect(Collectors.joining());
        final String actualOutput = Files.lines(actualFile).collect(Collectors.joining());

        assertEquals(expectedOutput, actualOutput);
    }

}