package eu.kartoffelquadrat.duplicatedetector.io;

import java.io.File;
import java.io.FilenameFilter;

/**
 * Custom implementation of generic file filter interface that allows direct identification of touchcore RAM files.
 * (Files ending on ".ram")
 *
 * NOPE - THIS IS NOT RECURSIVE.. use Commons OI: https://stackoverflow.com/questions/2534632/list-all-files-from-a-directory-recursively-with-java
 *
 * @author Maximilian Schiedermeier
 */
public class RamFileFilter implements FilenameFilter {

    // custom filter that only matches files ending on *.ram
    @Override
    public boolean accept(File dir, String name) {
        return name.endsWith(".ram");
    }
}
