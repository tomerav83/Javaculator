package org.javaculator;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.javaculator.antlr4.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        String input = "x= - --a";

        CalcLexer lexer = new CalcLexer(CharStreams.fromString(input));
        CalcParser parser = new CalcParser(new CommonTokenStream(lexer));
        parser.setErrorHandler(new BailErrorStrategy());

        CalculationInterpreter evaluator = new CalculationInterpreter();
        evaluator.setVar("a", 5);
        evaluator.setVar("b", 2);
        evaluator.setVar("c", 6);

        try {
            Integer result = parser.prog().accept(evaluator);
            System.out.println("Result: " + result);
            System.out.println("Vars: " + evaluator.getVars());
        } catch (ParseCancellationException e) {
            if (e.getCause() instanceof InputMismatchException inputMismatchE) {
                Token token = inputMismatchE.getOffendingToken();

                System.out.printf(
                        "Invalid token: %s, [%s-%s]%n", token.getText(), token.getStartIndex(), token.getStopIndex());
            }
        }

    }
}