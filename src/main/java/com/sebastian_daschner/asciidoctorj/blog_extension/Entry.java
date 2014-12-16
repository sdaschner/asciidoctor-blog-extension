package com.sebastian_daschner.asciidoctorj.blog_extension;

import java.util.Objects;

/**
 * @author Sebastian Daschner
 */
public class Entry {

    private final String abstractContent;
    private final String title;
    private final String date;
    private final String link;

    public Entry(final String abstractContent, final String title, final String date, final String link) {
        Objects.requireNonNull(abstractContent);
        Objects.requireNonNull(title);
        Objects.requireNonNull(date);
        Objects.requireNonNull(link);

        this.abstractContent = abstractContent;
        this.title = title;
        this.date = date;
        this.link = link;
    }

    public String getAbstractContent() {
        return abstractContent;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getLink() {
        return link;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Entry entry = (Entry) o;

        if (!abstractContent.equals(entry.abstractContent)) return false;
        if (!date.equals(entry.date)) return false;
        if (!link.equals(entry.link)) return false;
        return title.equals(entry.title);
    }

    @Override
    public int hashCode() {
        int result = abstractContent.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + date.hashCode();
        result = 31 * result + link.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Entry{" +
                "abstractContent='" + abstractContent + '\'' +
                ", title='" + title + '\'' +
                ", date='" + date + '\'' +
                ", link='" + link + '\'' +
                '}';
    }

}
