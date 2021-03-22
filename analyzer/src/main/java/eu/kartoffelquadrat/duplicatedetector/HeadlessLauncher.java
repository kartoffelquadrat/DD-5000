package eu.kartoffelquadrat.duplicatedetector;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;

public class HeadlessLauncher {

    private static Logger logger = LogManager.getLogger(HeadlessLauncher.class);
    private static String pathToTemplateFolder;
    private static String pathToSubmissionFolder;

    public static void main(String[] args) {

        // Print welcome message
        System.out.println("Headless Launcher starting...");

        // Parse arguments
        parseArguments(args);

        // Extract blacklisted ecore IDs (all IDs in RAM files within template folder)
        Set<String> blacklistEcoreIdentifiers;
    }

/**
 * Verifies the correct amount of inputs is present and that they specify valid file system paths. On success sets the
 * correesponding private path variables.
 */
    private static void parseArguments(String[] args) {

        // Verify amount of arguments.
        if (args.length != 2)
            throw new RuntimeException("Expected exatly tow runtime arguments:\n1) Path to template folder.\n2) path to submission folder created by data preparation script.");
        logger.info("Correct mount of runtime arguments provided.");

        // Verify specified locations exist
        pathToTemplateFolder = args[0];
        pathToSubmissionFolder = args[1];
        if (Files.notExists(Paths.get(pathToTemplateFolder)))
            throw new RuntimeException("No folder at specified template location: "+pathToTemplateFolder);
        if (Files.notExists(Paths.get(pathToSubmissionFolder)))
            throw new RuntimeException("No folder at specified submission location: "+pathToSubmissionFolder);

        logger.info("Both input arguments specify valid directory locations.");
    }
 }