package com.sebastian_daschner.asciidoctorj.blog_extension;

import org.asciidoctor.Asciidoctor;
import org.asciidoctor.ast.DocumentHeader;
import org.asciidoctor.ast.StructuredDocument;

import java.io.File;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Extracts the {@link Entry}s from the entries directory.
 *
 * @author Sebastian Daschner
 */
public class EntriesExtractor {

    /**
     * The AsciiDoc ID of the abstract content blog.
     */
    private static final String ABSTRACT_CONTENT_ID = "abstract";
    private final Asciidoctor asciidoctor;

    public EntriesExtractor(final Asciidoctor asciidoctor) {
        this.asciidoctor = asciidoctor;
    }

    public List<Entry> extract(final Path target) {
        final File entriesDir = target.toFile();

        if (!entriesDir.isDirectory())
            throw new IllegalArgumentException("Entries target '" + target.toFile().toString() + "' is no directory");

        final File[] entryFiles = entriesDir.listFiles((dir, name) -> name.endsWith(".adoc"));

        return Stream.of(entryFiles).map(e -> createEntry(e, entriesDir)).sorted(Comparator.comparing(Entry::getDate).reversed())
                .collect(Collectors.toList());
    }

    private Entry createEntry(final File entryFile, final File directory) {
        final DocumentHeader documentHeader = asciidoctor.readDocumentHeader(entryFile);

        final String title = documentHeader.getDocumentTitle().getMain();
        final String date = documentHeader.getRevisionInfo().getDate();

        final StructuredDocument structuredDocument = asciidoctor.readDocumentStructure(entryFile, new HashMap<>());

        final String abstractContent = structuredDocument.getPartById(ABSTRACT_CONTENT_ID).getContent();
        final String filteredContent = filterContent(abstractContent);

        final String link = '/' + directory.getName() + '/' + entryFile.getName();

        return new Entry(filteredContent, title, date, link);
    }

    private static String filterContent(final String abstractContent) {
        // dirty hack to remove HTML tags which are already included in ContentPart#getContent
        // TODO need better solution
        return abstractContent.replace("<br>", " + ").replaceAll("</?code>", "`");

    }

}
