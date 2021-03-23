package eu.kartoffelquadrat.duplicatedetector;

import eu.kartoffelquadrat.duplicatedetector.analysis.CollisionDetector;
import org.junit.Test;

import java.io.File;
import java.util.Map;
import java.util.Set;

/**
 * Unit test for the entire launcher. This operates on sample files provided in the repo. Unzip "DD5000-test-files" and
 * save the CONTENT within /tmp.
 */
public class HeadlessLauncherTest {

    /**
     * Stub test that verifies the sample files are correctly interpreted and the right amount of collisions are
     * identified.
     */
    @Test
    public void testSampleFiles() {

        String[] sampleArgs = new String[]{"/tmp/TemplateTestDir", "/tmp/SubmissionTestDir"};

        // Ensure the arguments represent existing directories
        HeadlessLauncher.validateArgumentLocations(sampleArgs);

        // Run the actual search for duplicated, based on the validated input directories.
        Map<String, Set<String>> collisions = HeadlessLauncher.analyze(new File(sampleArgs[0]), new File(sampleArgs[1]));

        // Verify collision content
        assert collisions.size() == 11;

        // Verify exactly two collisions found per colliding ecore identifier.
        for (String ecoreIdentifier : collisions.keySet()) {
            assert collisions.get(ecoreIdentifier).size() == 2;
        }
    }
}
