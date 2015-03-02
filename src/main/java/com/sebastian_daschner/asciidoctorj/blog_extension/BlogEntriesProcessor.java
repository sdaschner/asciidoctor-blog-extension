package com.sebastian_daschner.asciidoctorj.blog_extension;

import org.asciidoctor.Asciidoctor;
import org.asciidoctor.ast.AbstractBlock;
import org.asciidoctor.ast.Block;
import org.asciidoctor.extension.BlockMacroProcessor;
import org.jruby.RubySymbol;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Renders the {@code entries::<path_to_entries>[]} macro.
 * The entries which are located under the given path are extracted as teasers and inserted at the current location.
 * Optional attributes are "oneLine" for one link without a teaser and "size" for the maximum entry size.
 *
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
            final Path entriesDir = getEntriesDir(abstractBlock, target);
            final List<Entry> entries = extractor.extract(entriesDir);

            final boolean oneLine = determineOneLine(attributes);
            final int size = determineSize(attributes);

            Stream<Entry> entryStream = entries.stream();
            if (size > 0)
                entryStream = entryStream.limit(size);

            content = entryStream.map(e -> generator.generate(e, oneLine)).collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("Could not render entries, reason: " + e.getMessage());
            throw new RuntimeException(e);
        }

        return createBlock(abstractBlock, "pass", content, attributes, getConfig());
    }

    private Path getEntriesDir(final AbstractBlock abstractBlock, final String target) {
        final Map<Object, Object> documentOptions = abstractBlock.document().getOptions();

        final String currentDir = documentOptions.entrySet().stream()
                .filter(e -> "base_dir".equals(RubySymbol.objectToSymbolString((RubySymbol) e.getKey())))
                .map(e -> (String) e.getValue())
                .findFirst().orElse("");

        return Paths.get(currentDir + '/' + target);
    }

    private int determineSize(final Map<String, Object> attributes) {
        final String text = ((String) attributes.getOrDefault("text", ""));
        final String replacedSize = text.replaceFirst(".*size=\"?(\\d+).*", "$1");
        if (replacedSize.matches("\\d+"))
            return Integer.parseInt(replacedSize);
        return 0;
    }

    private boolean determineOneLine(final Map<String, Object> attributes) {
        return ((String) attributes.getOrDefault("text", "")).contains("oneLine");
    }

}
