package org.javaculator.antlr4;

import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.javaculator.antlr4.snapshot.Snapshot;
import org.javaculator.exceptions.InvalidCalculationException;

import java.util.Map;

public class CalculationProcessor {
    private static final Javaculator CALCULATOR = new Javaculator();
    private static final BailErrorStrategy ERROR_STRATEGY = new BailErrorStrategy();

    public static Snapshot calculate(String input) {
        CalcLexer lexer = new CalcLexer(CharStreams.fromString(input));
        CalcParser parser = new CalcParser(new CommonTokenStream(lexer));
        parser.setErrorHandler(ERROR_STRATEGY);

        try {
            CALCULATOR.visit(parser.assignment());
        } catch (InvalidCalculationException e) {
            System.out.println(e.getMessage());
        }

        return CALCULATOR.getSnapshot();
    }

    public static void clearState() {
        CALCULATOR.clearVars();
    }
}
