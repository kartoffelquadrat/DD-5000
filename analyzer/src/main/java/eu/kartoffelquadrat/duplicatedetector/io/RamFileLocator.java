package eu.kartoffelquadrat.duplicatedetector.io;

import eu.kartoffelquadrat.duplicatedetector.HeadlessLauncher;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.File;

/**
 * Util class for convenient location of RAM files.
 *
 * @author Maximilian Schiedermeier
 */
public class RamFileLocator {

    private static Logger logger = LogManager.getLogger(HeadlessLauncher.class);

    /**
     * Util method that deep searches a provided base directory for files ending on "*.ram".
     *
     * @param basedir as the directory to search. (Must exist, must not be an actual file.)
     * @return an array of file objects, listing all ram files found in a deep search of the given directory.
     */
    public static File[] locateDeepRamFiles(File basedir) {

        // verify the basedir exists
        if(!basedir.exists())
            throw new RuntimeException("Can not search what does not exist.");

        // verify the basedir is actually a directory
        if(!basedir.isDirectory())
            throw new RuntimeException("Can not deep search a non-directory.");

        logger.info("Running deep search for RAM files on "+basedir.getAbsolutePath());

        return basedir.listFiles(new RamFileFilter());
    }
}
