package org.javaculator.tokenization;

import org.javaculator.tokenization.abs.Tokenizer;
import org.javaculator.tokenization.impl.numbers.NumberTokenizer;
import org.javaculator.tokenization.impl.misc.WhitespaceTokenizer;
import org.javaculator.tokenization.impl.identifiers.VariableTokenizer;
import org.javaculator.tokenization.impl.identifiers.PrimitiveTypeTokenizer;
import org.javaculator.tokenization.impl.operators.*;
import org.javaculator.tokenization.impl.parenthesis.LeftParenTokenizer;
import org.javaculator.tokenization.impl.parenthesis.RightParenTokenizer;
import org.javaculator.tokenization.models.Token;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CompositeTokenizer {
    private static final List<Tokenizer> tokenizers = List.of(
            VariableTokenizer.INSTANCE,
            PrimitiveTypeTokenizer.INSTANCE,
            AssignmentOperatorTokenizer.INSTANCE,
            DecOperatorTokenizer.INSTANCE,
            IncOperatorTokenizer.INSTANCE,
            NumericOperatorTokenizer.INSTANCE,
            ShiftOperatorTokenizer.INSTANCE,
            LeftParenTokenizer.INSTANCE,
            RightParenTokenizer.INSTANCE,
            NumberTokenizer.INSTANCE,
            WhitespaceTokenizer.INSTANCE);

    public static List<Token> tokenize(String input) {
        return tokenizers.parallelStream()
                .map((Tokenizer tokenizer) -> tokenizer.tokenize(input))
                .flatMap(Collection::parallelStream)
                .sorted(Comparator.comparing(Token::pos))
                .collect(Collectors.toList());
    }
}
