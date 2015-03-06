#!/bin/bash

rm src/*
rm parser/*

../../../Desktop/javacc-5.0/bin/javacc Beluga.jj 
mv *.java src/ 

javac src/*
 
mv src/*.class parser/  

java parser.langB -debug_AS -print_tree $1 > resultado_$1.txt

