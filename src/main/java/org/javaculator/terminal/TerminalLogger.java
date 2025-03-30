package org.javaculator.terminal;

import lombok.experimental.UtilityClass;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.jline.utils.AttributedStyle.DEFAULT;

/**
 * Utility class for logging styled messages to the console using JLine.
 * Supports ANSI styling and color-coded log levels (info, error, fatal).
 */
@UtilityClass
public class TerminalLogger {
    private static final DateTimeFormatter TIMESTAMP_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    private static final boolean DISABLE_LOGGING = Boolean.parseBoolean(System.getenv("DISABLE_LOGGING"));
    private static final boolean ENABLE_TIMESTAMP = true;
    private static final boolean ENABLE_EMOJIS = true;

    /**
     * Logs a warn error message in yellow.
     *
     * @param logMessage the message to log
     */
    public static void warn(String logMessage) {
        log("[WARN]", logMessage, AttributedStyle.YELLOW, "⚠️");
    }

    /**
     * Logs an informational message in green.
     *
     * @param logMessage the message to log
     */
    public static void info(String logMessage) {
        log("[INFO]", logMessage, AttributedStyle.GREEN, "ℹ️");
    }

    /**
     * Logs an error message in red.
     *
     * @param logMessage the message to log
     */
    public static void error(String logMessage) {
        log("[ERROR]", logMessage, AttributedStyle.RED, "❌");
    }

    /**
     * Logs a fatal error message in bold red.
     *
     * @param logMessage the message to log
     */
    public static void fatal(String logMessage) {
        log("[FATAL]", logMessage, AttributedStyle.RED, "💀");
    }


    /**
     * Creates a colored {@link AttributedString} with the specified foreground color.
     *
     * @param logMessage the message to style
     * @param color      the {@link AttributedStyle} color constant
     * @return the styled {@link AttributedString}
     */
    public static AttributedString foreground(String logMessage, int color) {
        return new AttributedString(logMessage, DEFAULT.foreground(color));
    }

    /**
     * Parses and creates an {@link AttributedString} from an ANSI-formatted string.
     *
     * @param logMessage the ANSI string to parse
     * @return the parsed {@link AttributedString}
     */
    public static AttributedString fromAnsi(String logMessage) {
        return AttributedString.fromAnsi(logMessage);
    }

    private static void log(String level, String message, int color, String emoji) {
        if (DISABLE_LOGGING) {
            return;
        }

        StringBuilder sb = new StringBuilder();

        if (ENABLE_TIMESTAMP) {
            String timestamp = LocalDateTime.now().format(TIMESTAMP_FORMATTER);
            sb.append("[").append(timestamp).append("] ");
        }

        if (ENABLE_EMOJIS) {
            sb.append(emoji).append(" ");
        }

        sb.append(level).append(" ").append(message);

        new AttributedString(sb.toString(), DEFAULT.foreground(color)).println(JLineTerminal.get());
    }
}
