package org.javaculator;

import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.javaculator.antlr4.CalcLexer;
import org.javaculator.antlr4.CalcParser;
import org.javaculator.antlr4.Javaculator;
import org.javaculator.antlr4.snapshot.Snapshot;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.impl.DefaultParser;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private static final Javaculator CALCULATOR = new Javaculator();
    private static final BailErrorStrategy ERROR_STRATEGY = new BailErrorStrategy();

    public static void main(String[] args) {
        try (Terminal terminal = TerminalBuilder.builder()
                .system(true)
                .color(true)
                .build()){
            System.out.println("Hello Taboola :), ready for some math?");

            LineReader lineReader = LineReaderBuilder.builder()
                    .terminal(terminal)
                    .parser(new DefaultParser())
                    .build();

            String line;
            while ((line = lineReader.readLine("Enter calculation: ")) != null) {
                if ("exit".equals(line)) {
                    break;
                }

                System.out.println(calculate(line));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static Snapshot calculate(String input) {
        CalcLexer lexer = new CalcLexer(CharStreams.fromString(input));
        CalcParser parser = new CalcParser(new CommonTokenStream(lexer));
        parser.setErrorHandler(ERROR_STRATEGY);

        return CALCULATOR.addCalculationStage(parser);
    }
}