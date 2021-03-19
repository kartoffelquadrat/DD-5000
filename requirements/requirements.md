# Requirements

This document compiles requirements to guide the design and development of the DD-5000.

## Main usage

A list of things that **must** be supported.

 * Configuration of the input files location.
   * One subfolder per student.
   * Subfolder name matches student name.
   * Inner structure of student folders unknown.
   * RAM files / multiple deep RAM files within subfolder must be automatically detected.
 * Configuration of a template file. All ids featured in the template files must be excluded from analysis.
   * Note: Could also be an upper-bound filter of findins (omits ids that are present in EVERY submission). Potenitally dangerous, because students could remove parts of template.

Required information in output:

 * For every identifier (that is not in template AND in has at least findings for two students)
   * ~~Identifier information: Class / Operation etc...~~ Could vary.
   * Per Student match:
     * Name / ID of student
     * File with finding
     * Full tag with finding

Alternatively / Additionally

 * An overview. Per Student:
   * How many ID collisions with at least one other student
   * Considering ID collisions found for this students, how many other students share the collisions in total.

## Possible addons

 * Being able to deal with multiple, incremental files per student.
