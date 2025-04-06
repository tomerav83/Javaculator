package org.javaculator;

import org.javaculator.shuntified.ShuntiCalc;
import org.javaculator.shuntified.calculator.Calculator;
import org.javaculator.terminal.JLineTerminal;
import org.javaculator.terminal.TerminalLogger;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.impl.DefaultParser;
import org.jline.terminal.Terminal;

public class Cli {
    public static final ShuntiCalc calculator = new ShuntiCalc();

    public static void main(String[] args) {
        try (Terminal terminal = JLineTerminal.get()){
            LineReader lineReader = LineReaderBuilder.builder()
                    .terminal(terminal)
                    .parser(new DefaultParser())
                    .build();

            runLoopUntilExit(lineReader);
        } catch (Exception e) {
            TerminalLogger.fatal(e.getMessage());
            System.exit(-1);
        }
    }

    private static void runLoopUntilExit(LineReader lineReader) {
        String input;
        do {
            input = lineReader.readLine("Enter calculation: ");

            if ("exit".equals(input)) {
                TerminalLogger.info("Javaculator finished, thanks for playing :)");
                break;
            }

            calculator.calculate(input);
        } while (input != null);
    }
}
