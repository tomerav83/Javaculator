grammar Calc2;

@header {
package org.javaculator.antlr4;
}

assignment: expression EOF ;

expression
    : ID (ASSIGNMENT | AUGMENTED_ASSIGNMENT) expression
    | additiveExpr
    ;

// Lowest precedence for additive operators.
additiveExpr
    : multiplicativeExpr (('+'|'-') multiplicativeExpr)*
    ;

// Next level: multiplicative operators.
multiplicativeExpr
    : unaryExpr (('*'|'/'|'%') unaryExpr)*
    ;

// Handle unary operators.
unaryExpr
    : ('+'|'-') unaryExpr
    | ('++' | '--') ID
    | primaryExpr
    ;

// Primary expressions.
primaryExpr
    : INT
    | ID (('++' | '--'))?
    | '(' expression ')'
    ;

// Lexer rules.
ASSIGNMENT: '=';
AUGMENTED_ASSIGNMENT: ('+=' | '-=' | '*=' | '/=' | '%=');
ID  : [a-zA-Z_][a-zA-Z0-9_]* ;
INT : [0-9]+ ;
WS  : [ \t\r\n]+ -> skip ;