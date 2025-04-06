package org.javaculator.shuntified;

import org.javaculator.shuntified.cache.RollbackCache;
import org.javaculator.shuntified.calculator.Calculator;
import org.javaculator.shuntified.exceptions.eval.EvaluationException;
import org.javaculator.shuntified.lexer.StagingLexer;
import org.javaculator.shuntified.exceptions.lexer.UnhandledTokenException;
import org.javaculator.shuntified.models.Token;
import org.javaculator.shuntified.parser.RpnParser;
import org.javaculator.shuntified.exceptions.rpn.RpnParsingException;
import org.javaculator.terminal.TerminalLogger;

import java.util.List;

public class ShuntiCalc {
    private final Calculator calculator = new Calculator();

    public void calculate(String input) {
        try {
            calculator.takeSnapshot();
            List<Token> tokens = StagingLexer.create(input).tokenize();
            List<Token> rpn = RpnParser.parse(tokens);
            calculator.evaluate(rpn);
            TerminalLogger.info("After calculation: %s".formatted(calculator.getCache()));
        } catch (UnhandledTokenException | RpnParsingException | EvaluationException e) {
            calculator.rollback();
            TerminalLogger.error("Encountered exception: %s".formatted(e.getMessage()));
            TerminalLogger.warn("After calculation: %s".formatted(calculator.getCache()));
        } catch (Exception e) {
            TerminalLogger.fatal("Encountered unhandled exception: %s".formatted(e.getMessage()));
            TerminalLogger.warn("After calculation: %s".formatted(calculator.getCache()));
        }
    }

    public RollbackCache getCache() {
        return calculator.getCache();
    }

    public void clear() {
        calculator.clear();
    }
}
