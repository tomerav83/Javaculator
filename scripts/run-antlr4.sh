#!/bin/bash

gradle jar -PmainClass=org.javaculator.antlr4.Cli
java -jar build/libs/Javaculator.jar