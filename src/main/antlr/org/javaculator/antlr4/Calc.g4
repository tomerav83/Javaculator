grammar Calc;

@header {
package org.javaculator.antlr4;
}

assignment
    : ID '=' expression EOF                             #AssignExpr
    | ID op=('+=' | '-=' | '*=' | '/=' | '%=') expression EOF                #AugmentedAssignExpr
    ;

expression
    : expression op=('+'|'-') expression                       # AddSubExpr
    | expression op=('*'|'/'|'%') expression                   # MulDivModExpr
    | expression op=('+=' | '-=' | '*=' | '/=' | '%=') expression            # AugmentedExpr
    | op=('+'|'-') INT                                         # SignedNumberExpr
    | op=('+'|'-') ID                                          # SignedIdentifierExpr
    | '++' ID                                                  # PreIncrementExpr
    | '--' ID                                                  # PreDecrementExpr
    | ID '++'                                                  # PostIncrementExpr
    | ID '--'                                                  # PostDecrementExpr
    | INT                                                      # IntLiteral
    | ID                                                       # Identifier
    | '(' expression ')'                                       # ParenExpr
    ;

AUGMENTED_ASSIGNMENT:  ('+=' | '-=' | '*=' | '/=' | '%=');
ID  : [a-zA-Z_][a-zA-Z0-9_]* ;
INT : [0-9]+ ;
WS  : [ \t\r\n]+ -> skip ;