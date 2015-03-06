#!/bin/bash

rm -rf src/ parser/
mkdir src
mkdir parser 
javacc Beluga.jj 
mv *.java src 

javac src/*.java recovery/*java 
mv src/*.class parser/
#mv recovery/*.class parser/ 

 
java parser.langB -debug_recovery $1
