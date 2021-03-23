#!/bin/bash

if [ -z "$1" ]; then
	echo "Source directory location required as paramter 2."
	exit -1
fi

if [ -z "$2" ]; then
	echo "Target directory location required as paramter 2."
	exit -1
fi

if [ ! -d "$1" ];then
	echo "Parameter 1 does not specify a directory."
	exit -1
fi

if [ ! -d "$2" ];then
	echo "Parameter 2 does not specify a directory."
	exit -1
fi

# Where the master folder with the zips from mycourses lies
SOURCEDIR=$1

# Where the cleaned data shall be stored
TARGETDIR=$2

# Remove target dir if already present
if [ -d "$TARGETDIR" ]; then
    rm -rf $TARGETDIR
fi

# Copy everything to preserve the original data
cp -r $SOURCEDIR $TARGETDIR

# work on the copied data
cd $TARGETDIR

# convert every student zip submission into a named folder
for i in *zip; do
	# crop filename to studentname (split at '-', take 3rd position)
	STUDENT=$(echo $i | cut -f3 -d'-')

	# trim whitespaces from string
	STUDENT=$(echo $STUDENT | xargs)
	echo $STUDENT

	# create dediacted folder for student, unzip submission inside
	mkdir "$STUDENT"
	unzip "$i" -d "$STUDENT"

	# remove MACOS spotlight / trash backups from target folder
	if [ -d "$STUDENT/__MACOSX" ]; then
		rm -rf "$STUDENT/__MACOSX"
	fi
done

# Remove everything that is not a folder (DS store, zip files, index html...)
find . -maxdepth 1 -type f -delete

# create an index file with all RAM submissions, escape spaces, remove leading './'
find . -name \*.ram | sed 's/\ /\\\ /g' | cut -c 3-  > _ramfiles.txt
