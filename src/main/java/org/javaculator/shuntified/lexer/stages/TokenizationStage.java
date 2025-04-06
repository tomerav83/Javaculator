package org.javaculator.shuntified.lexer.stages;

import org.javaculator.shuntified.models.Token;

public interface TokenizationStage {
    Token match(String input, int position);
}
