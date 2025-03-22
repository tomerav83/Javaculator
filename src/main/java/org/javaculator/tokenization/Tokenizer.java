package org.javaculator.tokenization;

import java.util.regex.Matcher;

public interface Tokenizer<T> {
     T tokenize(String token);
}
