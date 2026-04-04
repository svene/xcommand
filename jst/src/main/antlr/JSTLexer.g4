lexer grammar JSTLexer;

@header {
package org.xcommand.template.jst.parser;
}

// =====================
// Default mode
// =====================
JAVA_EOL
    : '\r\n' | '\n' | '\r'
    ;

COMMENT_START
    : '/*#' -> pushMode(IN_COMMENT)
    ;

JAVA_TEXT
    : .
    ;

// =====================
// COMMENT mode
// =====================
mode IN_COMMENT;

COMMENT_END
    : '#*/' -> popMode
    ;

COMMENT_EOL
    : '\r\n' | '\n' | '\r'
    ;

COMMENT_TEXT
    : .
    ;

JAVA_EXPR_START
    : '$jv{' -> pushMode(IN_JAVA_EXPR)
    ;

// =====================
// JAVA EXPR mode
// =====================
mode IN_JAVA_EXPR;

JAVA_EXPR_END
    : '}' -> popMode
    ;

fragment DIGIT : [0-9A-Z];
fragment LETTER : [a-zA-Z_];
fragment JAVA_CHARS : '.' | '(' | ')';

JAVA_EXPR
    : (LETTER | DIGIT | JAVA_CHARS)+
    ;
