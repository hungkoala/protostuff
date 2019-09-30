package io.protostuff.generator.html.json.pages;

import static io.protostuff.generator.html.HtmlGenerator.PAGES;

import io.protostuff.compiler.model.Module;
import io.protostuff.generator.OutputStreamFactory;
import io.protostuff.generator.html.StaticPage;
import io.protostuff.generator.html.json.AbstractJsonGenerator;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import org.apache.commons.io.FilenameUtils;

public class JsonPagesIndexGenerator extends AbstractJsonGenerator {

    @Inject
    public JsonPagesIndexGenerator(OutputStreamFactory outputStreamFactory) {
        super(outputStreamFactory);
    }

    @Override
    public void compile(Module module) {
        @SuppressWarnings("unchecked")
        List<StaticPage> pages = (List<StaticPage>) module.getOptions().get(PAGES);
        if (pages != null) {
            List<PageIndexItem> root = pages.stream()
                    .map(page -> ImmutablePageIndexItem.builder()
                            .name(page.getName())
                            .ref(FilenameUtils.getBaseName(page.getFile().getName()))
                            .build())
                    .collect(Collectors.toList());
            write(module, "data/pages.json", root);
        }
    }
}
