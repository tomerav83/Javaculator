package org.javaculator.shuntified.lexer;

import org.javaculator.shuntified.models.tkn.impl.BracketTkn;
import org.javaculator.shuntified.models.tkn.impl.ConstantTkn;
import org.javaculator.shuntified.models.tkn.impl.VariableTkn;
import org.javaculator.shuntified.models2.bracket.BracketToken;
import org.javaculator.shuntified.models2.bracket.Brackets;
import org.javaculator.shuntified.models2.op.Operator;
import org.javaculator.shuntified.models2.op.Operators;
import org.javaculator.shuntified.models2.Token;
import org.javaculator.shuntified.models2.val.ValueToken;
import org.javaculator.shuntified.models2.vars.VariableToken;
import org.javaculator.shuntified.utils.InputPreprocessor;
import org.javaculator.shuntified.pattern.collectors.impl.NumbersCollector;
import org.javaculator.shuntified.pattern.collectors.impl.VariableCollector;

import java.util.ArrayList;
import java.util.List;

public class Lexified {
    private final String input;
    private final List<String> vars;
    private final List<String> nums;

    public Lexified(String input) {
        this.vars = VariableCollector.INSTANCE.collect(input);
        this.nums = NumbersCollector.INSTANCE.collect(input);
        this.input = InputPreprocessor.preprocess(input);
    }

    public List<Token> tokenize() {
        int position = 0;
        List<Token> tokens = new ArrayList<>();

        while (position < input.length()) {
            position += readToken(position, tokens);
        }

        return tokens;
    }

    private Integer readToken(int start, List<Token> tokens) {
        for (Operator operator : Operators.get()) {
            Integer size = operator.attemptToMatchAndRetrieve(input, start);

            if (size != null) {
                tokens.add(operator);
                return size;
            }
        }

        for (String variable : vars) {
            if (input.startsWith(variable, start)) {
                tokens.add(new VariableToken(variable));
                return variable.length();
            }
        }

        for (BracketToken bracketToken : Brackets.get()) {
            if (input.startsWith(bracketToken.getSign(), start)) {
                tokens.add(bracketToken);
                return bracketToken.getSign().length();
            }
        }

        for (String num : nums) {
            if (input.startsWith(num, start)) {
                tokens.add(new ValueToken(num));
                return num.length();
            }
        }

       throw new RuntimeException("couldn't match any token for input=%s at=%s".formatted(input, start));
    }

}
