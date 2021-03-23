package eu.kartoffelquadrat.duplicatedetector.io;

import eu.kartoffelquadrat.duplicatedetector.HeadlessLauncher;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.*;

/**
 * Util class to conveniently extract all ECORE object identifiers per student of the provided base directories.
 *
 * @author Maximilian Schiedermeier
 */
public class StudentBundleBuilder {

    private static Logger logger = LogManager.getLogger(HeadlessLauncher.class);

    /**
     * Based on all provided RAM files, builds a per student set of ecore identifiers used ( + their contexts).
     *
     * @param blacklistedEcoreIdentifiers as set of Ecore Identifiers that were provided by template are should be
     *                                    excluded from analysis.
     * @param submissionBaseDir           as the main directory with all student subfolders.
     */
    public static Map<String, Set<String>> buildStudentBundles(Set<String> blacklistedEcoreIdentifiers, File submissionBaseDir) {
        Map<String, Set<String>> ecoreIdentifiersByStudent = new LinkedHashMap<>();

        // Obtain location of submission directory per student.
        Map<String, File> studentSubmissions = StudentDirectoryLocator.getSubmissionDirectories(submissionBaseDir);

        // For every student, extract all used ecore identifiers:
        for (String student : studentSubmissions.keySet()) {
            logger.info("Indexing files for " + student);

            // Prepare result set for all ecore identifiers
            Set<String> ecoreIdentifiers = new LinkedHashSet<>();

            // Locate all ram files submitted by student, extract ecores, save in map.
            Collection<File> ramFiles = RamFileLocator.locateDeepRamFiles(studentSubmissions.get(student));
            for (File ramFile : ramFiles) {
                ecoreIdentifiers.addAll(RamFileTokenExtractor.extractEcoreIdentifiers(ramFile));
            }

            // Remove all balcklisted ecore identifiers
            ecoreIdentifiers.removeAll(blacklistedEcoreIdentifiers);

            // If at least one ecore id remains, stage student for further analysis
            if (!ecoreIdentifiers.isEmpty()) {
                ecoreIdentifiersByStudent.put(student, ecoreIdentifiers);
            }
        }

        logger.info("Imported ecore identifiers of " + ecoreIdentifiersByStudent.keySet().size() + "/" + studentSubmissions.keySet().size() + " students.");
        return ecoreIdentifiersByStudent;
    }
}
