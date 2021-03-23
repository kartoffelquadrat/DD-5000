package eu.kartoffelquadrat.duplicatedetector.io;

import eu.kartoffelquadrat.duplicatedetector.HeadlessLauncher;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import sun.awt.image.ImageWatched;

import java.io.File;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * Util class to conveniently extract all ECORE object identifiers of the provided base directories.
 *
 * @author Maximilian Schiedermeier
 */
public class EcoreExtractor {

    private static Logger logger = LogManager.getLogger(HeadlessLauncher.class);

    /**
     * Based on all provided RAM files, builds a per student set of ecore identifiers used ( + their contexts).
     * @param blacklistedEcoreIdentifiers as set of Ecore Identifiers that were provided by template are should be excluded from analysis.
     * @param submissionBaseDir as the main directory with all student subfolders.
     */
    public static void buildStudentBundles(Set<String> blacklistedEcoreIdentifiers, File submissionBaseDir)
    {
        // Obtain location of submission directory per student.
        Map<String, File> studentSubmissions = StudentDirectoryLocator.getSubmissionDirectories(submissionBaseDir);

        // For every student, extract all used ecore identifiers:
        for(String student : studentSubmissions.keySet())
        {
            // Prepare result set for all ecore identifiers
            Set<String> ecoreIdentifiers = new LinkedHashSet<>();

            // Locate all ram files submitted by student:
            File[] ramFiles = RamFileLocator.locateDeepRamFiles(studentSubmissions.get(student));
            for(int f = 0; f < ramFiles.length; f++)
            {
                logger.info("Indexing ecore identifiers of "+ramFiles[f].getAbsolutePath());
                ecoreIdentifiers.addAll(extractEcoreIdentifiers(ramFiles[f]));
            }
        }
    }

    /**
     * Util method that extracts all Ecore Identifiers found in a specific RAM file.
     *
     * @param ramFile as the file to interpret.
     * @return a set with all ecore identifiers found in the provided file.
     */
    public static Set<String> extractEcoreIdentifiers(File ramFile) {

        logger.info("Searching " + ramFile.getAbsolutePath() + " for Ecore Identifiers.");
        return new LinkedHashSet<>();

        //TODO: Implement.
    }
}
