package cli;

import picocli.CommandLine.Command;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

//expressor transpile --out output HelloWorld.Expresso
//command transpile
@Command(name = "transpile",
        description = "Transpilation (translation) from Expresso to Java (.expresso -> .java)",
        mixinStandardHelpOptions = true)
public class Transpile extends BaseCommand {
    //template
    final private String templateFile = "template/HelloWorld.java";

    public boolean isExpressoFile(){
        return file.getName().toLowerCase().endsWith(".expresso");
    }

    public boolean isValidFile() {
        return file.exists() && file.length() > 0;
    }

    public boolean isValidName(String expresso) {
        String name = templateFile.substring(templateFile.lastIndexOf('/') + 1, templateFile.lastIndexOf('.'));
        return name.equals(expresso);
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
            // Extracts the base name of the file by removing its extension.
            String fileName = file.getName().substring(0, file.getName().lastIndexOf('.'));
            if(!isValidName(fileName)) {
                System.out.println("Invalid name file.");
            }

            createOutputDirectory();
            if(verbose) System.out.println("\nCreating .java file...");

            File javaOutputFile = new File(directory.getPath(), fileName + ".java");
            try {
                if(verbose) System.out.println("Reading expresso content...");
                InputStream inputStream = getClass().getClassLoader().getResourceAsStream(templateFile);
                String content = new String(inputStream.readAllBytes());

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
    @Override
    public void run() {
        generateJavaFile();
    }

}