package cli;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Option;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

//expressor transpile --out output HelloWorld.Expresso
//command transpile
@Command(name = "transpile",
        description = "Transpilation (translation) from Expresso to Java (.expresso -> .java)",
        mixinStandardHelpOptions = true)
public class Transpile extends BaseCommand {
    @Override
    public void run() {
        generateJavaFile();
    }

}