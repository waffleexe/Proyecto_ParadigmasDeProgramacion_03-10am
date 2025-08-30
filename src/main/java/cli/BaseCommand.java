package cli;

import picocli.CommandLine.Parameters;
import picocli.CommandLine.Option;

import java.io.File;

abstract class BaseCommand implements Runnable{
    //file type
    @Parameters(paramLabel = "File", description = ".expresso file")
    File file;

    //folder name
    @Option(names = { "-o", "--out" }, description = "Defines the output folder where the file is saved.", defaultValue = ".")
    File directory = new File(".");

    //details steps
    @Option(names = { "-ver", "--verbose" }, description = "Indicates the steps being executed.")
    Boolean verbose = false;

}
