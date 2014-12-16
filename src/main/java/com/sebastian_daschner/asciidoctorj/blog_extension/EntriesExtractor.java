package com.sebastian_daschner.asciidoctorj.blog_extension;

import org.asciidoctor.Asciidoctor;
import org.asciidoctor.ast.ContentPart;
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
 * @author Sebastian Daschner
 */
public class EntriesExtractor {

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

        return Stream.of(entryFiles).map(e -> createEntry(e, entriesDir)).sorted(Comparator.comparing(Entry::getDate))
                .collect(Collectors.toList());
    }

    private Entry createEntry(final File entryFile, final File directory) {
        final DocumentHeader documentHeader = asciidoctor.readDocumentHeader(entryFile);

        final String title = documentHeader.getDocumentTitle().getMain();
        final String date = documentHeader.getRevisionInfo().getDate();

        final StructuredDocument structuredDocument = asciidoctor.readDocumentStructure(entryFile, new HashMap<>());

        final String abstractContent = structuredDocument.getParts().stream().filter(p -> ABSTRACT_CONTENT_ID.equals(p.getId()))
                .map(ContentPart::getContent).findAny().orElse("");

        final String link = directory.getName() + '/' + entryFile.getName();

        return new Entry(abstractContent, title, date, link);
    }

}
