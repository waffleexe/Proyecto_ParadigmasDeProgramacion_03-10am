package cli;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Option;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

@Command(name = "run",
        description = "Transpiles, compiles (build), and runs the .java program",
        mixinStandardHelpOptions = true)
public class Run implements Runnable {

    @Parameters(paramLabel = "File", description = ".expresso file")
    File executableFile;

    @Option(names = { "-o", "--out" }, description = "Defines the output folder where the file is saved")
    File directory = new File(".");

    @Option(names = { "-ver", "--verbose" }, description = "Indicates the steps being executed.")
    Boolean verbose = false;

    public void executeFile() {
        new Build() {{
            this.javaFile = executableFile;
            this.dir = Run.this.directory;
            this.verbose = Run.this.verbose;
            this.run();
            Run.this.executableFile = this.javaFile;
            Run.this.directory = this.dir;
        }};

        if (executableFile == null)
            return;

        URLClassLoader classLoader = null;
        try {
            classLoader = URLClassLoader.newInstance(new URL[]{directory.toURI().toURL()});
            String className = executableFile.getName().substring(0, executableFile.getName().lastIndexOf('.'));

            if (verbose) System.out.println("\nLoading class: " + className);
            Class<?> cls = Class.forName(className, true, classLoader);

            if (verbose) System.out.println("Creating main method...");
            Method main = cls.getMethod("main", String[].class);
            String[] params = null;

            System.out.println("\n\n==== ===== ====\n");
            System.out.println("\n\n==== " + className + ".java ====\n");
            main.invoke(null, (Object) params);

        } catch (MalformedURLException | ClassNotFoundException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void run() {
        executeFile();
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new Run()).execute(args);
        System.exit(exitCode);
    }
}
