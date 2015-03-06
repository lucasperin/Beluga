#!/bin/bash

rm src/* parser/* 
javacc Beluga.jj 
mv *.java src 
cd src 
javac *.java 
mv *.class ../parser 
cd .. 
java parser.langB -debug_AS $1 >> resultado_$1.txt
