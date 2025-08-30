package cli;
import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(name = "expressor",
        version = "express 1.0",
        //--help
        mixinStandardHelpOptions = true,
        subcommands = {Transpile.class ,Run.class, Build.class})
public class Expressor implements Runnable {
    @Override
    public void run() {
        System.out.println("-Expressor CLI- \n" +
                "Subcommands: \n" +
                "Transpile: \n" +
                "Saves in archivo.java the simulation of a fake transpilation from " +
                "a source archivo.expresso.\n" +
                "Build: \n" +
                "Fakes the transpilation and then compiles archivo.java into archivo.class.\n" +
                "Run: \n" +
                "Equivalent to the sequence of the previous two plus the execution of the class.");
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new Expressor()).execute(args);
        System.exit(exitCode);
    }
}
