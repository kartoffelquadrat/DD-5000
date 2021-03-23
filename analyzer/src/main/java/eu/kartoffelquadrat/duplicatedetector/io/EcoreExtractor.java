package eu.kartoffelquadrat.duplicatedetector.io;

import eu.kartoffelquadrat.duplicatedetector.HeadlessLauncher;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import sun.awt.image.ImageWatched;

import java.io.File;
import java.util.Collection;
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
     *
     * @param blacklistedEcoreIdentifiers as set of Ecore Identifiers that were provided by template are should be
     *                                    excluded from analysis.
     * @param submissionBaseDir           as the main directory with all student subfolders.
     */
    public static void buildStudentBundles(Set<String> blacklistedEcoreIdentifiers, File submissionBaseDir) {
        // Obtain location of submission directory per student.
        Map<String, File> studentSubmissions = StudentDirectoryLocator.getSubmissionDirectories(submissionBaseDir);

        // Prepare filter set for blank students (have no RAM files) in submission
        Set<String> blankStudents = new LinkedHashSet<>();

        // For every student, extract all used ecore identifiers:
        for (String student : studentSubmissions.keySet()) {
            logger.info("Indexing files for " + student);

            // Prepare result set for all ecore identifiers
            Set<String> ecoreIdentifiers = new LinkedHashSet<>();

            // Locate all ram files submitted by student:
            Collection<File> ramFiles = RamFileLocator.locateDeepRamFiles(studentSubmissions.get(student));
            for (File ramFile : ramFiles) {
                ecoreIdentifiers.addAll(extractEcoreIdentifiers(ramFile));
            }

            // If no files found for student, stage for filtering
            if (ramFiles.isEmpty())
                blankStudents.add(student);
        }

        // Remove all blank students
        studentSubmissions.keySet().removeAll(blankStudents);

        logger.info("Imported ecore identifiers of " + studentSubmissions.keySet().size() + " students. " + blankStudents.size() + " students did not provide ram files and can not be analyzed.");
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
