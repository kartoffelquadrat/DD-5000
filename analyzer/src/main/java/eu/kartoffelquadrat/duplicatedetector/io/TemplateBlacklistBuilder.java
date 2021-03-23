package eu.kartoffelquadrat.duplicatedetector.io;

import eu.kartoffelquadrat.duplicatedetector.HeadlessLauncher;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Util class to identify ecore identifiers that must be excluded from analysis.
 *
 * @author Maximilian Schiedermeier
 */
public class TemplateBlacklistBuilder {

    private static Logger logger = LogManager.getLogger(HeadlessLauncher.class);

    /**
     * Helper method that deep searches a provided directory for RAM files and extracts all ecore identifiers featured
     * in those files.
     *
     * @param templateFolder as the folder representing the TouchCORE solution template. Can be empty.
     * @return set of all unique ecore identifiers detected in all detected ram files.
     */
    public static Set<String> extractBlacklistedEcoreIdentifiersFromTemplateFolder(File templateFolder) {

        // Prepare target result set.
        Set<String> blacklistEcoreIdentifiers = new LinkedHashSet<>();

        // Get location of all ram files of template
        Collection<File> ramTemplateFiles = RamFileLocator.locateDeepRamFiles(templateFolder);
        logger.info("Located " + ramTemplateFiles.size() + " template RAM files in template directory " + templateFolder);

        // Extract all identifiers to ignore
        for (File ramTemplateFile : ramTemplateFiles) {
            blacklistEcoreIdentifiers.addAll(RamFileTokenExtractor.extractEcoreIdentifiers(ramTemplateFile));
        }
        logger.info("Extracted " + blacklistEcoreIdentifiers.size() + " ecore identifiers to blacklist from RAM templates.");

        return blacklistEcoreIdentifiers;
    }
}
