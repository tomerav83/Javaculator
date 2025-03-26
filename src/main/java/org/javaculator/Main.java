package org.javaculator;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.javaculator.antlr4.CalculationInterpreter;
import org.javaculator.antlr4.CalcLexer;
import org.javaculator.antlr4.CalcParser;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        String input = "x = (a++ - b) / (--c - b)";

        CalcLexer lexer = new CalcLexer(CharStreams.fromString(input));
        CalcParser parser = new CalcParser(new CommonTokenStream(lexer));

        CalculationInterpreter evaluator = new CalculationInterpreter();
        evaluator.setVar("a", 5);
        evaluator.setVar("b", 2);
        evaluator.setVar("c", 6);

        ;

        Integer result = parser.prog().accept(evaluator);
        System.out.println("Result: " + result);
        System.out.println("Vars: " + evaluator.getVars());
    }
}