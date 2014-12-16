package com.sebastian_daschner.asciidoctorj.blog_extension;

import org.asciidoctor.Asciidoctor;
import org.asciidoctor.Options;

/**
 * @author Sebastian Daschner
 */
public class ContentGenerator {

    /**
     * The pushlished-on date prefix text.
     */
    private static final String DATE_PREFIX = "Published on ";

    /**
     * Represents the CSS class on the published-on span.
     */
    private static final String DATE_ROLE = "note";
    private final Asciidoctor asciidoctor;

    public ContentGenerator(final Asciidoctor asciidoctor) {
        this.asciidoctor = asciidoctor;
    }

    public String generate(final Entry entry) {
        return asciidoctor.convert(generateContent(entry), new Options());
    }

    private static String generateContent(final Entry entry) {
        StringBuilder builder = new StringBuilder();

        appendTitle(builder, entry.getTitle(), convertLink(entry.getLink()));
        appendDate(builder, entry.getDate());
        appendContent(builder, entry.getAbstractContent());

        return builder.toString();
    }

    private static String convertLink(final String link) {
        if (link.endsWith(".adoc"))
            return link.replace(".adoc", ".html");
        return link;
    }

    private static void appendTitle(final StringBuilder builder, final String title, final String link) {
        // generate level 1
        builder.append("== link:");
        builder.append(link);
        builder.append('[');
        builder.append(title);
        builder.append(']');
        builder.append('\n');
    }

    private static void appendDate(final StringBuilder builder, final String date) {
        builder.append("[");
        builder.append(DATE_ROLE);
        builder.append("]#");
        builder.append(DATE_PREFIX);
        builder.append(date);
        builder.append("#\n");
    }

    private static void appendContent(final StringBuilder builder, final String abstractContent) {
        builder.append(abstractContent);
        builder.append('\n');
    }

}
