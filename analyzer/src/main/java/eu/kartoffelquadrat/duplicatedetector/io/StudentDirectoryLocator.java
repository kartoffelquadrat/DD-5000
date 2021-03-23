package eu.kartoffelquadrat.duplicatedetector.io;

import eu.kartoffelquadrat.duplicatedetector.HeadlessLauncher;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Provides a map of all Student directories (indexed by student name) present in a provided submission base folder.
 *
 * @author Maximilian Schiedermeier
 */
public class StudentDirectoryLocator {

    private static Logger logger = LogManager.getLogger(HeadlessLauncher.class);

    public static Map<String, File> getSubmissionDirectories(File submissionBaseDir)
    {
        // result map preparation
        Map studentSubmissions = new LinkedHashMap<String, File>();

        // Get all direct subdirectories
        File[] directories = submissionBaseDir.listFiles(File::isDirectory);

        // Index subdirectories by student name
        for(int i = 0; i < directories.length; i++)
        {
            studentSubmissions.put(directories[i].getName(), directories[i]);
        }

        logger.info("Indexed "+directories.length + " student folders by name.");
        return studentSubmissions;
    }
}
