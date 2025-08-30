package cli;

import picocli.CommandLine;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Option;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

abstract class BaseCommand implements Runnable{
    //file type
    @Parameters(paramLabel = "File", description = ".expresso file")
    File expressoFile;

    //folder name
    @Option(names = { "-o", "--out" }, description = "Defines the output folder where the file is saved.", defaultValue = ".", required = false)
    File directory = new File(".");

    //details steps
    @Option(names = { "-ver", "--verbose" }, description = "Indicates the steps being executed.")
    Boolean verbose = false;

    public boolean isExpressoFile(){
        return expressoFile.getName().toLowerCase().endsWith(".expresso");
    }

    public boolean isValidFile() {
        return expressoFile.exists() && expressoFile.length() > 0;
    }

    public File createOutputDirectory(){
        String rootPath = ".";

        //if the directory doesnt exist
        if(directory == null)
            directory = new File(rootPath);
        else {
            directory = new File(rootPath, directory.getPath());
            if (!directory.exists()) {
                directory.mkdirs();
            }
        }
        return directory;
    }

    public File generateJavaFile(){
        if(isValidFile() && isExpressoFile()) {
            if(verbose) System.out.println("\nCreating .java file...");

            //nombre completo de la extension, lo toma desde el inicio hasta encontrar el punto
            String fileName = expressoFile.getName().substring(0, expressoFile.getName().lastIndexOf('.'));
            createOutputDirectory();

            File javaOutputFile = new File(directory.getPath(), fileName + ".java");
            try {
                if(verbose) System.out.println("Reading expresso content...");
                String content = Files.readString(expressoFile.toPath());

                if(verbose) System.out.println("Transpiling expresso -> java...");
                Files.write(javaOutputFile.toPath(), content.getBytes());

                return javaOutputFile;
            } catch (IOException e) {
                System.out.println("Error while creating the file.");
                e.printStackTrace();
            }
        }
        else {
            System.out.println("Only existing non-empty '.expresso' files are allowed.");
        }
        return null;
    }
}
