package eu.kartoffelquadrat.duplicatedetector;

import eu.kartoffelquadrat.duplicatedetector.io.EcoreExtractor;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.Set;

/**
 * Command line launcher for the Duplicate Detector 5000.
 *
 * @author Maximilian Schiedermeier
 */
public class HeadlessLauncher {

    private static Logger logger = LogManager.getLogger(HeadlessLauncher.class);
    private static File templateFolder;
    private static File submissionFolder;

    public static void main(String[] args) {

        // Print welcome message
        System.out.println("Headless Launcher starting...");

        // Ensure the arguments represent existing directories
        validateArgumentLocations(args);

        // Extract blacklisted ecore IDs (all IDs in RAM files within template folder)
        Set<String> blacklistEcoreIdentifiers = null; //ToDo implement.

        // Extract analyzer input dataset (Per student, which ecores occur.)
        EcoreExtractor.buildStudentBundles(blacklistEcoreIdentifiers, submissionFolder);
    }

    /**
     * Verifies the correct amount of inputs is present and that they specify valid file system paths.
     */
    private static void validateArgumentLocations(String[] args) {

        // Verify amount of arguments.
        if (args.length != 2)
            throw new RuntimeException("Expected exactly two runtime arguments:\n1) Path to template folder.\n2) Path to submission folder created by data preparation script.");
        logger.info("Correct mount of runtime arguments provided.");

        // Verify specified locations exist
        templateFolder = new File(args[0]);
        submissionFolder = new File(args[1]);
        if (!templateFolder.exists() || !templateFolder.isDirectory())
            throw new RuntimeException("No folder at specified template location: " + args[0]);
        if (!submissionFolder.exists() || !submissionFolder.isDirectory())
            throw new RuntimeException("No folder at specified submission location: " + args[1]);

        logger.info("Both input arguments specify valid directory locations.");
    }
}