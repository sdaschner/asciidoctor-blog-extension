package com.sebastian_daschner.asciidoctorj.blog_extension;

import org.asciidoctor.Asciidoctor;
import org.asciidoctor.AttributesBuilder;
import org.asciidoctor.OptionsBuilder;

/**
 * Generates the content which is inserted for the entries.
 *
 * @author Sebastian Daschner
 */
public class ContentGenerator {

    /**
     * The published-on date prefix text.
     */
    private static final String DATE_PREFIX = "Published on ";

    /**
     * The "more" link text.
     */
    private static final String MORE_TITLE = "more";

    /**
     * The CSS class on the published-on span.
     */
    private static final String DATE_CSS_CLASS = "note";

    /**
     * The CSS class on the "more" link.
     */
    private static final String MORE_CSS_CLASS = "more";

    private final Asciidoctor asciidoctor;

    public ContentGenerator(final Asciidoctor asciidoctor) {
        this.asciidoctor = asciidoctor;
    }

    public String generate(final Entry entry) {
        final AttributesBuilder attributes = AttributesBuilder.attributes().linkAttrs(true);
        final OptionsBuilder options = OptionsBuilder.options().attributes(attributes);

        return asciidoctor.convert(generateContent(entry), options);
    }

    private static String generateContent(final Entry entry) {
        final StringBuilder builder = new StringBuilder();
        final String link = convertLink(entry.getLink());

        appendTitle(builder, entry.getTitle(), link);
        appendDate(builder, entry.getDate());
        appendContent(builder, entry.getAbstractContent());
        appendMoreLink(builder, link);

        return builder.toString();
    }

    private static String convertLink(final String link) {
        if (link.endsWith(".adoc"))
            return link.replace(".adoc", "");
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
        builder.append("++++\n<span class=\"");
        builder.append(DATE_CSS_CLASS);
        builder.append("\">");
        builder.append(DATE_PREFIX);
        builder.append(date);
        builder.append("</span>\n++++\n");
    }

    private static void appendContent(final StringBuilder builder, final String abstractContent) {
        builder.append(abstractContent);
        builder.append(" + \n");
    }

    private static void appendMoreLink(final StringBuilder builder, final String link) {
        builder.append("link:");
        builder.append(link);
        builder.append("[\"");
        builder.append(MORE_TITLE);
        builder.append("\", role=\"");
        builder.append(MORE_CSS_CLASS);
        builder.append("\"]");
        builder.append('\n');
    }

}
