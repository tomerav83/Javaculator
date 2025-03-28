package org.javaculator.antlr42po;

import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.javaculator.antlr4.Calc2Lexer;
import org.javaculator.antlr4.Calc2Parser;

import java.util.Map;

public class CalculationProcessor {
    private static final Javaculator CALCULATOR = new Javaculator();
    private static final BailErrorStrategy ERROR_STRATEGY = new BailErrorStrategy();

    public static Map<String, Integer> calculate(String input) {
        Calc2Lexer lexer = new Calc2Lexer(CharStreams.fromString(input));
        Calc2Parser parser = new Calc2Parser(new CommonTokenStream(lexer));
        parser.setErrorHandler(ERROR_STRATEGY);

        try {
            CALCULATOR.visit(parser.assignment());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return CALCULATOR.getVars();
    }

    public static void clearState() {
        CALCULATOR.clearVars();
    }
}
