package cli;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Option;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;

@Command(name = "build",
        description = "Compilation of the generated Java file (creates .class)",
        mixinStandardHelpOptions = true)
public class Build implements Runnable {

    @Parameters(paramLabel = "File", description = ".expresso file")
    File javaFile;

    @Option(names = { "-o", "--out" }, description = "Defines the output folder where the file is saved.")
    File dir;

    @Option(names = { "-ver", "--verbose" }, description = "Indicates the steps being executed.")
    Boolean verbose = false;

    public void compileJava() {
        new Transpile() {{
            this.expressoFile = Build.this.javaFile;
            this.directory = Build.this.dir;
            this.verbose = Build.this.verbose;
            Build.this.javaFile = this.generateJavaFile();
            Build.this.dir = this.directory;
        }};

        if (javaFile == null)
            return;

        if (!dir.exists()) {
            dir.mkdirs();
        }

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

        if (compiler == null) {
            System.out.println("Compiler not found. Are you using a JRE instead of a JDK?");
            return;
        }

        if (verbose) System.out.println("\nCompiling Java file...");
        int result = compiler.run(null, null, null, "-d", dir.getAbsolutePath(), javaFile.getPath());

        if (result == 0) {
            if (verbose) System.out.println(".class file generated in " + dir);
        } else {
            System.out.println("Compilation error. Code: " + result);
        }
    }

    @Override
    public void run() {
        compileJava();
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new Build()).execute(args);
        System.exit(exitCode);
    }
}