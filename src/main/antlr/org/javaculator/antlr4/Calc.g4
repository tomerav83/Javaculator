grammar Calc;

@header {
package org.javaculator.antlr4;
}

assignment: expression EOF                              #RootAssignExpr;

expression
    : ID (ASSIGNMENT | AUGMENTED_ASSIGNMENT) expression #AssignExpr
    | additiveExpr                                      #RootAddExpr
    ;

// Lowest precedence for additive operators.
additiveExpr
    : multiplicativeExpr (('+'|'-') multiplicativeExpr)* #RootMultExpr
    ;

// Next level: multiplicative operators.
multiplicativeExpr
    : unaryExpr (('*'|'/'|'%') unaryExpr)*               #RootUnaryExpr
    ;

// Handle unary operators.
unaryExpr
    : ('+'|'-') unaryExpr                                #SignedUnaryExpr
    | ('++' | '--') ID                                   #PreUnaryExpr
    | primaryExpr                                        #RootPrimaryExpr
    ;

// Primary expressions.
primaryExpr
    : INT                                                #LiteralExpr
    | ID (('++' | '--'))?                                #PostUnaryExpr
    | '(' expression ')'                                 #ParenExpr
    ;

// Lexer rules.
ASSIGNMENT: '=';
AUGMENTED_ASSIGNMENT: ('+=' | '-=' | '*=' | '/=' | '%=');
ID  : [a-zA-Z_][a-zA-Z0-9_]* ;
INT : [0-9]+ ;
WS  : [ \t\r\n]+ -> skip ;