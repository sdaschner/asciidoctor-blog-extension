package com.sebastian_daschner.asciidoctorj.blog_extension;

import org.asciidoctor.Asciidoctor;
import org.asciidoctor.AttributesBuilder;
import org.asciidoctor.OptionsBuilder;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ContentGeneratorTest {

    private Asciidoctor asciidoctor;
    private ContentGenerator classUnderTest;

    @Before
    public void setUp() throws Exception {
        asciidoctor = Asciidoctor.Factory.create();
        classUnderTest = new ContentGenerator(asciidoctor);
    }

    @Test
    public void testGenerate() throws Exception {
        final String expectedSource = "== link:entries/test_entry[Test entry]\n" +
                "++++\n<span class=\"note\">Published on 2014-12-12</span>\n++++\n" +
                "Lorem ipsum dolor sit amet + \n" +
                "link:entries/test_entry[\"more\", role=\"more\"]\n";
        final OptionsBuilder options = OptionsBuilder.options().attributes(AttributesBuilder.attributes().linkAttrs(true));
        final String expectedOutput = asciidoctor.convert(expectedSource, options);

        final String actualOutput = classUnderTest.generate(new Entry("Lorem ipsum dolor sit amet", "Test entry", "2014-12-12", "entries/test_entry"));

        assertEquals(expectedOutput, actualOutput);
    }

}