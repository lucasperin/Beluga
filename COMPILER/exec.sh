#!/bin/bash

rm src/*
rm parser/*
rm programa/*

javacc Beluga.jj 
mv *.java src/ 

javac src/*
 
mv src/*.class parser/  


java parser.langB -debug_AS $1 > resultado_$1.txt

mv *.jas programa/

jasmin programa/*.jas

mv *.class programa/

java -classpath .:programa/ Programa
