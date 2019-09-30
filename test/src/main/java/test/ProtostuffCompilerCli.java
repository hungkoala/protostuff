package test;

import io.protostuff.compiler.model.ImmutableModuleConfiguration;
import io.protostuff.compiler.model.ModuleConfiguration;
import io.protostuff.compiler.parser.ParserException;
import io.protostuff.generator.GeneratorException;
import io.protostuff.generator.ProtostuffCompiler;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Protostuff compiler command-line interface.
 *
 * @author Kostiantyn Shchepanovskyi
 */
public class ProtostuffCompilerCli {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProtostuffCompilerCli.class);

    public static void main(String[] args) {
        run();
    }

    private static void run() {

        ProtostuffCompiler compiler = new ProtostuffCompiler();

        ImmutableModuleConfiguration.Builder builder = ImmutableModuleConfiguration.builder();
        builder.name("main");
        builder.generator("java");
        builder.output("/Users/hung/project/java/test/test/src/main/java");

        List<Path> includePaths = new ArrayList<>();
        includePaths.add(Paths.get("/Users/hung/project/java/test/test/src/main/proto"));
        builder.includePaths(includePaths);

        List<String> protoFiles = Arrays.asList("search.proto", "wrappers.proto");
        builder.protoFiles(protoFiles);


        LOGGER.info("Version={}", ProtostuffCompiler.class.getPackage().getImplementationVersion());

        ModuleConfiguration configuration = builder.build();

        if (configuration.getProtoFiles().isEmpty()) {
            LOGGER.error("Missing input file.");
            return;
        }
        if (configuration.getGenerator() == null) {
            LOGGER.error("Missing generator directives.");
            return;
        }
        try {
            compiler.compile(configuration);
        } catch (GeneratorException | ParserException e) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.error("Compilation error", e);
            } else {
                LOGGER.error(e.getMessage());
            }
        }
    }

}
