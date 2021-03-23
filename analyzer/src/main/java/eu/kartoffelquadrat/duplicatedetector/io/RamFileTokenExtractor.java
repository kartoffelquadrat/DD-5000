package eu.kartoffelquadrat.duplicatedetector.io;

import eu.kartoffelquadrat.duplicatedetector.HeadlessLauncher;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Helper class allows loading a file from disk and extraction of all words with a certain substring.
 *
 * @author Maximilian Schiedermeier
 */
public class RamFileTokenExtractor {

    private static Logger logger = LogManager.getLogger(HeadlessLauncher.class);

    /**
     * Load file content to string
     *
     * @param file as the input location
     * @return a string with the content of the file, in US_ASCII encoding.
     */
    private static String loadFromDisk(File file) {
        try {
            return Files.readString(file.toPath(), StandardCharsets.US_ASCII);
        } catch (IOException e) {
            String message = "Failed to parse input file: " + file.getAbsolutePath();
            logger.fatal(message);
            throw new RuntimeException(message);
        }
    }

    /**
     * Splits a ram file at all occurrences of a given marker (any sequence of whitespaces, including linebreaks)
     *
     * @param ramFile as the file to be tokenized
     * @return trimmed tokens of ram file as string array
     */
    private static String[] tokenize(String ramFile) {

        // Split at any sequence of whitespace characters.
        return ramFile.split("\\s+");
    }

    /**
     * Loads a ram file from disk and extracts all ecore identifiers used.
     *
     * @param ramFile as the file to analyze
     * @return a set with all unique identifiers found in the specified file.
     */
    public static Set<String> extractEcoreIdentifiers(File ramFile) {
        String rawFile = loadFromDisk(ramFile);
        String[] tokens = tokenize(rawFile);
        Set<String> ecoreIdentifiers = filterIdentifiers(tokens);

        logger.info("Extracted " + ecoreIdentifiers.size() + " unique ecore identifiers of ram file " + ramFile.getAbsolutePath());
        return ecoreIdentifiers;
    }

    /**
     * Processes a string array and stores all elements that start with an the xml id tag in a result set. (stripped to
     * the actual id)
     *
     * @param tokens as the array of tokens to process
     * @return set of ecore identifiers used by id tokens.
     */
    private static Set<String> filterIdentifiers(String[] tokens) {

        Set<String> ecoreIdentifiers = new LinkedHashSet<>();

        for (String token : tokens) {
            if (token.startsWith("xmi:id")) {
                // extract the actual ID within quotes
                ecoreIdentifiers.add(token.split("\"")[1]);
            }
        }

        return ecoreIdentifiers;
    }
}
