grammar Calc;

@header {
package org.javaculator.antlr4;
}

calculate: expression EOF                               #Root;

expression
    : ID ASSIGNMENT expression                          #AssignExpr
    | ID AUGMENTED_ASSIGNMENT expression                #AugmentedAssignExpr
    | additive                                          #SingletonExpr
    ;

additive
    : multiplicative (('+'|'-') multiplicative)*         #AddSubExpr
    ;

multiplicative
    : unary (('*'|'/'|'%'|'^') unary)*                       #MulDivModExpr
    ;

unary
    : ('+'|'-') unary                                    #SignedExpr
    | ('++' | '--') ID                                   #PreIncDecExpr
    | ID ('++' | '--')                                   #PostIncDecExpr
    | '(' expression ')'                                 #ParenExpr
    | literals                                           #LiteralsExpr
    ;

literals
    : INT_LITERAL                                        #Integer
    | FLOAT_LITERAL                                      #FloatingPoint
    | ID                                                 #Identifier
    | 'null'                                             #Null
    ;


INT_LITERAL
    :   DEC_INT_LITERAL
    |   HEX_INT_LITERAL
    |   OCT_INT_LITERAL
    |   BIN_INT_LITERAL
    ;

FLOAT_LITERAL
    :   DEC_FLOAT_LITERAL
    |   HEX_FLOAT_LITERAL
    ;

fragment DEC_INT_LITERAL
    :   '0'
    |   NON_ZERO_DIGIT (UNDERSCORE? DIGIT)* ( 'l' | 'L' )?
    ;

fragment HEX_INT_LITERAL
    :   '0' [xX] HEX_DIGIT (UNDERSCORE? HEX_DIGIT)* ( 'l' | 'L' )?
    ;

fragment OCT_INT_LITERAL
    :   '0' [0-7] (UNDERSCORE? [0-7])* ( 'l' | 'L' )?
    ;

fragment BIN_INT_LITERAL
     :   '0' [bB] BINARY_DIGIT (UNDERSCORE? BINARY_DIGIT)* ( 'l' | 'L' )?
    ;

fragment DEC_FLOAT_LITERAL
   :   (DIGITS? '.' DIGITS EXPONENT?
   | DIGITS '.' DIGITS? EXPONENT?
   | DIGITS EXPONENT)
   ( 'f' | 'F' | 'd' | 'D' )?
   ;

fragment HEX_FLOAT_LITERAL
    :   '0' [xX] (
            HEX_DIGITS ('.' HEX_DIGITS?)?    // Case A and B: numeral with optional dot and fractional part
          |
            '.' HEX_DIGITS                  // Case C: dot followed by numeral
        )
        [pP] [+-]? DIGITS                    // The exponent part (binary exponent) is required
        ( 'f' | 'F' | 'd' | 'D' )?
    ;

fragment EXPONENT:   [eE] [+-]? DIGITS;
fragment DIGITS: DIGIT (UNDERSCORE? DIGIT)*;
fragment HEX_DIGITS: HEX_DIGIT (UNDERSCORE? HEX_DIGIT)*;
fragment DIGIT:   [0-9];
fragment NON_ZERO_DIGIT:   [1-9];
fragment HEX_DIGIT: [0-9a-fA-F];
fragment BINARY_DIGIT: [01];
fragment UNDERSCORE: '_'; // BOB

ASSIGNMENT: '=';
AUGMENTED_ASSIGNMENT: ('+=' | '-=' | '*=' | '/=' | '^=' | '%=');
ID  : [a-zA-Z_][a-zA-Z0-9_]* ;
WS  : [ \t\r\n]+ -> skip ;