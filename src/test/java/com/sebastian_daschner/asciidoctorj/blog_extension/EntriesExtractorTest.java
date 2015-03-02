package com.sebastian_daschner.asciidoctorj.blog_extension;

import org.asciidoctor.Asciidoctor;
import org.junit.Before;
import org.junit.Test;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class EntriesExtractorTest {

    private EntriesExtractor classUnderTest;

    @Before
    public void setUp() {
        final Asciidoctor asciidoctor = Asciidoctor.Factory.create();
        classUnderTest = new EntriesExtractor(asciidoctor);
    }

    @Test
    public void testExtract() throws URISyntaxException {
        final Entry firstEntry = new Entry("Lorem ipsum dolor sit amet.", "First entry", "2014-12-01", "/entries/first_entry.adoc");
        final Entry secondEntry = new Entry("Lorem ipsum dolor sit amet `adipiscing` elit.", "Second entry", "2014-12-13", "/entries/second_entry.adoc");
        final Entry thirdEntry = new Entry("Lorem ipsum dolor sit amet, consectetur adipiscing elit. + \nCras ut pulvinar lectus.", "Third entry",
                "2014-12-20", "/entries/third_entry.adoc");

        // entries in reverse order (by date)
        final List<Entry> expectedEntries = Arrays.asList(thirdEntry, secondEntry, firstEntry);

        final Path entriesPath = TestUtils.getResourceFile("/asciidoc/entries").toPath();
        final List<Entry> actualEntries = classUnderTest.extract(entriesPath);

        assertEquals(expectedEntries, actualEntries);
    }

}