package eu.kartoffelquadrat.duplicatedetector;

import eu.kartoffelquadrat.duplicatedetector.analysis.CollisionDetector;
import eu.kartoffelquadrat.duplicatedetector.io.StudentBundleBuilder;
import eu.kartoffelquadrat.duplicatedetector.io.TemplateBlacklistBuilder;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.Map;
import java.util.Set;

/**
 * Command line launcher for the Duplicate Detector 5000. (DD-5000)
 *
 * @author Maximilian Schiedermeier
 */
public class HeadlessLauncher {

    private static Logger logger = LogManager.getLogger(HeadlessLauncher.class);
    private static File templateFolder;
    private static File submissionFolder;

    /**
     * Main entry point for the headless (console) launcher of the DD-5000.
     *
     * @param args
     */
    public static void main(String[] args) {

        // Print welcome message
        System.out.println("Headless Launcher starting...");

        // Ensure the arguments represent existing directories
        validateArgumentLocations(args);

        // Run the actual search for duplicated, based on the validated input directories.
        analyze(templateFolder, submissionFolder);
    }

    /**
     * Main analyzer control function.
     *
     * @param templateFolder   the validated template directory.
     * @param submissionFolder the validated submission directory.
     * @return collisionsMap indexing for every ecore identifier with more than one student occurrence the names of
     * colliding students.
     */
    public static Map<String, Set<String>> analyze(File templateFolder, File submissionFolder) {
        // Extract blacklisted ecore IDs (all IDs in RAM files within template folder)
        Set<String> blacklistEcoreIdentifiers = TemplateBlacklistBuilder.extractBlacklistedEcoreIdentifiersFromTemplateFolder(templateFolder);

        // Extract analyzer input dataset (Per student: set of all used ecore identifiers.)
        Map<String, Set<String>> ecoreIdentifiersPerStudent = StudentBundleBuilder.buildStudentBundles(blacklistEcoreIdentifiers, submissionFolder);

        // Search for collisions
        return CollisionDetector.indexByEcoreIdentifierOccurrence(ecoreIdentifiersPerStudent);
    }

    /**
     * Verifies the correct amount of inputs is present and that they specify valid file system paths.
     */
    public static void validateArgumentLocations(String[] args) {

        // Verify amount of arguments.
        if (args.length != 2)
            throw new RuntimeException("Expected exactly two runtime arguments:\n1) Path to template folder.\n2) Path to submission folder created by data preparation script.");
        logger.info("Correct amount of runtime arguments provided.");

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