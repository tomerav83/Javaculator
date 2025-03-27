grammar Calc;

@header {
package org.javaculator.antlr4;
}

prog: lhs op=('=' | '+=' | '-=' | '*=' | '/=' | '%=') expression EOF;

expression
    : expression op=('+'|'-') expression                     # AddSubExpr
    | expression op=('*'|'/'|'%') expression                 # MulDivModExpr
    | '++' ID                                                # PreIncrementExpr
    | '--' ID                                                # PreDecrementExpr
    | ID '++'                                                # PostIncrementExpr
    | ID '--'                                                # PostDecrementExpr
    | INT                                                    # IntLiteral
    | ID                                                     # Identifier
    | '(' expression ')'                                     # ParenExpr
    ;

lhs: ID;

ID  : [a-zA-Z_][a-zA-Z0-9_]* ;
INT : [0-9]+ ;
WS  : [ \t\r\n]+ -> skip ;