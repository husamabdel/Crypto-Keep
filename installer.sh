#!/bin/bash

#Declare the globals: 

BIN="/home/${whoami}/Packages/Crypto-Keep-1.7/bin"
SRC="/home/${whoami}/Packages/Crypto-Keep-1.7/src"

# Check if source is compiled or not.

BOOL=$(ls | grep .class)

if [[ $BOOL -eq $null ]]
then 
	printf ${MAGENTA} "Source is not compiled! compiling: \n"
	javac PassMan.java
fi

# Make the directories.

mkdir $BIN
mkdir $SRC

# Move appropreately

mv *.java $SRC
mv * $BIN

# create a runner script

echo 'java PassMan' >> ckeep.sh

# Move to user local bin

mv ckeep.sh /usr/local/bin

# Exit, but check if all is well

if [[ ${?} -ne 0 ]]
then 
	echo 'Installation failed! an error occured while running the installer!'
	exit 1
fi 

exit 0
