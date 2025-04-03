package org.javaculator.terminal;

import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.AttributedStyle;

import java.util.regex.Pattern;

/**
 * Utility class for initializing and accessing a shared JLine {@link Terminal} instance
 * with color support enabled. Also prints a welcome message during initialization.
 */
public class JLineTerminal {
    private static final Terminal TERMINAL = setup();

    /**
     * Returns the shared {@link Terminal} instance.
     *
     * @return the initialized JLine Terminal
     */
    public static Terminal get() {
        return TERMINAL;
    }

    /**
     * Initializes the JLine terminal with color support enabled.
     * Prints a welcome message during initialization.
     *
     * @return the initialized Terminal instance
     * @throws RuntimeException if terminal setup fails
     */
    private static Terminal setup() {
        try (Terminal terminal = TerminalBuilder.builder().system(true).color(true).build()) {
            return hello(terminal);
        } catch (Exception e) {
            throw new RuntimeException("Terminal setup exception, terminating!", e);
        }
    }

    /**
     * Prints a styled welcome message to the terminal and returns it.
     *
     * @param terminal the terminal to print to
     * @return the same terminal instance after printing
     */
    private static Terminal hello(Terminal terminal) {
        TerminalLogger.fromAnsi("Hello ").print(terminal);
        TerminalLogger.foreground("Taboola :)", AttributedStyle.BLUE).print(terminal);
        TerminalLogger.fromAnsi(", ready for some math?").println(terminal);
        return terminal;
    }
}
