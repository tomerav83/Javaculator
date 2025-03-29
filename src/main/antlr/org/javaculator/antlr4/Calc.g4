grammar Calc;

@header {
package org.javaculator.antlr4;
}

assignment: expression EOF                              #Root;

expression
    : ID (ASSIGNMENT | AUGMENTED_ASSIGNMENT) expression #AssignExpr
    | additive                                          #SingletonExpr
    ;

additive
    : multiplicative (('+'|'-') multiplicative)*         #AddSubExpr
    ;

multiplicative
    : unary (('*'|'/'|'%') unary)*                       #MulDivModExpr
    ;

unary
    : ('+'|'-') unary                                    #SignedExpr
    | ('++' | '--') ID                                   #PreIncDecExpr
    | ID ('++' | '--')                                   #PostIncDecExpr
    | '(' expression ')'                                 #ParenExpr
    | literals                                           #LiteralsExpr
    ;

literals
    : INT                                                #Integer
    | ID                                                 #Identifier
    | 'null'                                             #Null
    ;

// Lexer rules.
ASSIGNMENT: '=';
AUGMENTED_ASSIGNMENT: ('+=' | '-=' | '*=' | '/=' | '%=');
ID  : [a-zA-Z_][a-zA-Z0-9_]* ;
INT : [0-9]+ ;
WS  : [ \t\r\n]+ -> skip ;