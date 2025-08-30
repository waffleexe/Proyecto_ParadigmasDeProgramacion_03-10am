package cli;

import picocli.CommandLine.Command;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

@Command(name = "run",
        description = "Transpiles, compiles (build), and runs the .java program",
        mixinStandardHelpOptions = true)
public class Run extends BaseCommand {

    @Override
    public void run() {
        //compile the java file
        Build build = new Build();
        build.file = file;
        build.directory = directory;
        build.verbose = verbose;
        build.run();
        if (file == null) return;

        URLClassLoader classLoader = null;
        try {
            classLoader = URLClassLoader.newInstance(new URL[]{directory.toURI().toURL()});
            String className = file.getName().substring(0, file.getName().lastIndexOf('.'));

            if (verbose) System.out.println("\nLoading class: " + className);
            Class<?> cls = Class.forName(className, true, classLoader);

            if (verbose) System.out.println("Creating main method...");
            Method main = cls.getMethod("main", String[].class);
            String[] params = null;

            System.out.println("\n\n==== ===== ==== === ===");
            System.out.println("==== " + className + ".java ====\n");
            main.invoke(null, (Object) params);
            System.out.println("\n\n==== ===== ==== === ===");

        } catch (MalformedURLException | ClassNotFoundException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }

}
