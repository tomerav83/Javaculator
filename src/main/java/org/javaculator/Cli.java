package org.javaculator;

import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.javaculator.antlr4.CalcLexer;
import org.javaculator.antlr4.CalcParser;
import org.javaculator.antlr4.Javaculator;
import org.javaculator.terminal.JLineTerminal;
import org.javaculator.terminal.LoggerUtils;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.impl.DefaultParser;
import org.jline.terminal.Terminal;

public class Cli {
    private static final Javaculator CALCULATOR = new Javaculator();
    private static final BailErrorStrategy ERROR_STRATEGY = new BailErrorStrategy();

    public static void main(String[] args) {
        try (Terminal terminal = JLineTerminal.get()){
            LineReader lineReader = LineReaderBuilder.builder()
                    .terminal(terminal)
                    .parser(new DefaultParser())
                    .build();

            runLoopUntilExit(lineReader);
        } catch (Exception e) {
            LoggerUtils.fatal(e.getMessage());
            System.exit(-1);
        }
    }

    private static void runLoopUntilExit(LineReader lineReader) {
        String line;
        do {
            line = lineReader.readLine("Enter calculation: ");

            if ("exit".equals(line)) {
                LoggerUtils.info("Javaculator finished, thanks for playing :)");
                break;
            }

            runCalculationStage(line);
        } while (line != null);
    }

    private static void runCalculationStage(String input) {
        CalcLexer lexer = new CalcLexer(CharStreams.fromString(input));
        CalcParser parser = new CalcParser(new CommonTokenStream(lexer));
        parser.setErrorHandler(ERROR_STRATEGY);
        CALCULATOR.addCalculationStage(parser);
    }
}
