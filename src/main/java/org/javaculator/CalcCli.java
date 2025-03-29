package org.javaculator;

import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.javaculator.antlr4.CalcLexer;
import org.javaculator.antlr4.CalcParser;
import org.javaculator.antlr4.Javaculator;
import org.javaculator.antlr4.snapshot.Snapshot;

import java.util.Scanner;

public class CalcCli {
    private static final Javaculator CALCULATOR = new Javaculator();
    private static final BailErrorStrategy ERROR_STRATEGY = new BailErrorStrategy();

    public static void startCalculator() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter text (type 'BOB' to stop):");

        while (true) {
            String input = scanner.nextLine();

            if (input.equals("BOB")) {
                break;
            }

            System.out.println("Current state after calculation: " + calculate(input));
        }

        System.out.println("Input ended.");
        scanner.close();
    }

    private static Snapshot calculate(String input) {
        CalcLexer lexer = new CalcLexer(CharStreams.fromString(input));
        CalcParser parser = new CalcParser(new CommonTokenStream(lexer));
        parser.setErrorHandler(ERROR_STRATEGY);

        return CALCULATOR.addCalculationStage(parser);
    }
}
