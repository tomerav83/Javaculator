package org.javaculator.tokenization.models;

public enum TokenType {
    WHITESPACE,

    // parenthesis
    R_PAREN,
    L_PAREN,

    // identifiers/keywords
    VARIABLE,
    PRIMITIVE,

    // operators
    NUMERIC,
    ASSIGN,
    INC,
    DEC,
    SHIFT,

    // numbers,literals
    NUMBER,

    // unrecognized
    UNKNOWN
}
