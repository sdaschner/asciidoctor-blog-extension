package com.sebastian_daschner.asciidoctorj.blog_extension;

import org.asciidoctor.Asciidoctor;
import org.asciidoctor.ast.AbstractBlock;
import org.asciidoctor.ast.Block;
import org.asciidoctor.extension.BlockMacroProcessor;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Sebastian Daschner
 */
public class BlogEntriesProcessor extends BlockMacroProcessor {

    private final EntriesExtractor extractor;
    private final ContentGenerator generator;

    public BlogEntriesProcessor(final String macroName, final Map<String, Object> config) {
        super(macroName, config);

        extractor = new EntriesExtractor(Asciidoctor.Factory.create());
        generator = new ContentGenerator(Asciidoctor.Factory.create());
    }

    @Override
    public Block process(final AbstractBlock abstractBlock, final String target, final Map<String, Object> attributes) {
        final List<String> content;

        try {
            final Path entriesDir = Paths.get(System.getProperty("user.dir") + "/src/main/asciidoc" + target);
            final List<Entry> entries = extractor.extract(entriesDir);

            content = entries.stream().map(generator::generate).collect(Collectors.toList());

        } catch (Exception e) {
            System.err.println("Could not render entries, reason: " + e.getMessage());
            throw new RuntimeException(e);
        }

        return createBlock(abstractBlock, "pass", content, attributes, getConfig());
    }

}
