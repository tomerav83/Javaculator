#!/bin/bash

gradle jar -PmainClass=org.javaculator.shuntified.Cli
java -jar build/libs/Javaculator.jar