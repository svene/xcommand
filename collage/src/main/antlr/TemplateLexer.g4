lexer grammar TemplateLexer;

@header {
package org.collage.parser.antlr;
}

// =====================
// DEFAULT MODE
// =====================

EOL
    : '\r\n' | '\n' | '\r'
    ;

VAR_START
    : '${' -> pushMode(IN_VAR)
    ;

TEXT
    : ~[\r\n<$]+
    ;

JAVA_START
    : '<?java' -> pushMode(IN_JAVA)
    ;

// =====================
// VAR MODE
// =====================
mode IN_VAR;

VAR_END
    : '}' -> popMode
    ;

fragment DIGIT  : [0-9A-Z];
fragment LETTER : [a-zA-Z_];

VAR_NAME
    : LETTER (LETTER | DIGIT)*
    ;

// =====================
// JAVA MODE
// =====================
mode IN_JAVA;

JAVA_END
    : '?>' -> popMode
    ;

JAVA_CODE
    : (~[?\r\n] | '?' ~'>')+
    ;
