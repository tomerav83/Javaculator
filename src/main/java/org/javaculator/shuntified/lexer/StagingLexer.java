package org.javaculator.shuntified.lexer;

import org.javaculator.shuntified.exceptions.lexer.UnhandledTokenException;
import org.javaculator.shuntified.lexer.stages.TokenizationStage;
import org.javaculator.shuntified.lexer.stages.impl.BracketStage;
import org.javaculator.shuntified.lexer.stages.impl.LiteralStage;
import org.javaculator.shuntified.lexer.stages.impl.OperatorStage;
import org.javaculator.shuntified.lexer.stages.impl.VariableStage;
import org.javaculator.shuntified.models.Token;
import org.javaculator.shuntified.utils.InputPreprocessor;

import java.util.ArrayList;
import java.util.List;

public class StagingLexer {
    private final List<TokenizationStage> stages;
    private final String input;


    private StagingLexer(String input) {
        this.stages = List.of(
                OperatorStage.create(),
                BracketStage.create(),
                LiteralStage.create(input),
                VariableStage.create(input));
        this.input = InputPreprocessor.preprocess(input);
    }

    public static StagingLexer create(String input) {
        return new StagingLexer(input);
    }

    public List<Token> tokenize() throws UnhandledTokenException {
        int position = 0;
        List<Token> tokens = new ArrayList<>();

        while (position < input.length()) {
            Token token = readToken(position);
            tokens.add(token);
            position += token.getSign().length();
        }

        return tokens;
    }

    private Token readToken(int position) throws UnhandledTokenException {
        for (TokenizationStage matcher : stages) {
            Token token = matcher.match(input, position);

            if (token != null) {
                return token;
            }
        }

        throw new UnhandledTokenException(input, position);
    }

}
