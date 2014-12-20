package com.sebastian_daschner.asciidoctorj.blog_extension;

import org.asciidoctor.Asciidoctor;
import org.asciidoctor.extension.spi.ExtensionRegistry;

/**
 * Registers the {@link BlogEntriesProcessor}.
 *
 * @author Sebastian Daschner
 */
public class BlogEntriesProcessorExtension implements ExtensionRegistry {

    @Override
    public void register(final Asciidoctor asciidoctor) {
        asciidoctor.javaExtensionRegistry().blockMacro("entries", BlogEntriesProcessor.class);
    }

}
