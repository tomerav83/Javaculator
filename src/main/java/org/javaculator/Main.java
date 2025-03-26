package org.javaculator;

import org.javaculator.rules.Parsers;
import org.parboiled.parserunners.ReportingParseRunner;
import org.parboiled.support.ParsingResult;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        ParsingResult<Object> r1 = new ReportingParseRunner<>(Parsers.digit()).run("123");
        ParsingResult<Object> r2 = new ReportingParseRunner<>(Parsers.digit()).run("12.3");
        System.out.println("Hello Taboola!!!!!!");
    }
}