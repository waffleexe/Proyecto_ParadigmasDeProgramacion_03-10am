package cli;

import picocli.CommandLine.Command;


import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;

@Command(name = "build",
        description = "Compilation of the generated Java file (creates .class)",
        mixinStandardHelpOptions = true)
public class Build extends BaseCommand {



    @Override
    public void run() {
        File javaFile = generateJavaFile();

        if (javaFile == null)
            return;

        createOutputDirectory();

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

        if (compiler == null) {
            System.out.println("Compiler not found. Are you using a JRE instead of a JDK?");
            return;
        }

        if (verbose) System.out.println("\nCompiling Java file...");
        int result = compiler.run(null, null, null, "-d", directory.getAbsolutePath(), javaFile.getPath());

        if (result == 0) {
            if (verbose) System.out.println(".class file generated in " + directory);
        } else {
            System.out.println("Compilation error. Code: " + result);
        }

    }
}