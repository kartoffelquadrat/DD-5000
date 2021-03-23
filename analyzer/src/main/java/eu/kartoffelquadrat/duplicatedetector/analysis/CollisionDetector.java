package eu.kartoffelquadrat.duplicatedetector.analysis;

import eu.kartoffelquadrat.duplicatedetector.HeadlessLauncher;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * Util class that allows detection of non unique ecore identifiers and the associated students.
 *
 * @author Maximilian Schiedermeier
 */
public class CollisionDetector {

    private static Logger logger = LogManager.getLogger(HeadlessLauncher.class);

    /**
     * Helper method that rearranges the map of ecore idenfiers per student into a map of students by ecore identifier.
     * Every entry with a target set that has more than one value suggest cheating of the listed students.
     *
     * @param ecoreIdentifiersPerStudent as the input map as produced by a StudentBundleBuilder.
     * @return reorganized map that lists for every ecore identifier all students that used this identifier.
     */
    public static Map<String, Set<String>> indexByEcoreIdentifierOccurrence(Map<String, Set<String>> ecoreIdentifiersPerStudent) {

        Map<String, Set<String>> allEcoreIdenfierMap = new LinkedHashMap<>();

        // Iterate over input map. Use every entry as key in the target map. Use every kay as value for that key in target map.
        for (String student : ecoreIdentifiersPerStudent.keySet()) {
            // feed all ecore listings of that student to hashmap as new entries.
            for (String ecoreIdentifier : ecoreIdentifiersPerStudent.get(student)) {
                // if ecore identifier not yet indexed: initialize a new entry with target set.
                if (!allEcoreIdenfierMap.containsKey(ecoreIdentifier))
                    allEcoreIdenfierMap.put(ecoreIdentifier, new LinkedHashSet<>());

                // In any case add the student-id to the entry of the ecore identifier
                allEcoreIdenfierMap.get(ecoreIdentifier).add(student);
            }
        }

        // Shrink map to only ecore identifier entries with more actual user collisions.
        Map<String, Set<String>> collisionMap = filterCollisions(allEcoreIdenfierMap);
        logger.info("Converted student bundles into a collision map.");
        System.out.println(buildCollisionMapTextualRepresentation(collisionMap));
        return collisionMap;
    }

    /**
     * Helper method to turn the collision map into a string representation, one ecore identifier per line. Lists all
     * students indexed for that ecore identifier. Automatically omits entries with less than two entries.
     *
     * @param collisionMap as the map to be consumed.
     * @return a string representation of the input collision map.
     */
    private static String buildCollisionMapTextualRepresentation(Map<String, Set<String>> collisionMap) {

        StringBuilder sb = new StringBuilder("List of collisions: (template IDs filtered)\n");
        if (collisionMap.isEmpty())
            sb.append("\n---none---");
        else {
            for (String ecoreIdentifier : collisionMap.keySet()) {
                sb.append(ecoreIdentifier).append(": [");
                for (String student : collisionMap.get(ecoreIdentifier)) {
                    sb.append(" ").append(student).append(" |");
                }
                sb.append("]\n");
            }
        }

        return sb.toString();
    }

    /**
     * Helper method that reduces the provided list of all indexed ecore identifier occurrences to only entries with
     * user collisions.
     *
     * @return a true collision map of ecore identifiers.
     */
    private static Map<String, Set<String>> filterCollisions(Map<String, Set<String>> ecoreMap) {

        Map<String, Set<String>> collisionMap = new LinkedHashMap<>();

        for (Map.Entry<String, Set<String>> entry : ecoreMap.entrySet()) {
            if (entry.getValue().size() >= 2) {
                collisionMap.put(entry.getKey(), entry.getValue());
            }
        }
        return collisionMap;
    }

}
