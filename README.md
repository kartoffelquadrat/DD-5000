# Duplicate Detector 5000

Ecore ID duplicate detection made easy.

## About

The *Duplicate Detector [5000](https://youtu.be/UKygju476tM?t=18)* (*DD-5000*) is a software that analyzes models created with [TouchCORE](http://touchcore.cs.mcgill.ca/) and searches recurrent ecore identifiers. Ecore identifiers are commonly generated randomly. Therefore, potential results allow for an easy detection of plagiarisms. The *DD-5000* operates on a single input directory and presents findings in an intuitive format.

## Usage

### Data preparation

 * Get the mycourses submissions. (A folder with all zipped submissions) + an index html file.
 * Run the preparation script (creates one folder per student, named as student name+id)  
```bash
./dataprep.sh source-folder target-folder
```
   * Creates a master folder, named like ```target-folder```
   * Creates one subfolder per student name
   * Removes all hidden files / OS meta files
   * Creates an index text file with the relative pathes to all RAM files: ```_ramfiles.txt```


### Build

### Run

### Options

##  Further Resources

 * [Requirement Elicitation](requirements/requirements.md)
 * [Functional Specification](functionality/functionality.md)


## Contact / Pull Requests

 * Author: Maximilian Schiedermeier ![email](email.png)
 * Github: Kartoffelquadrat
 * Webpage: https://www.cs.mcgill.ca/~mschie3
 * License: [MIT](https://opensource.org/licenses/MIT)
