parser grammar JSTParser;

@header {
package org.xcommand.template.jst.parser;
}

options { tokenVocab = JSTLexer; }

// =====================
// Parser rules
// =====================

start
    : element* EOF
    ;

element
    : JAVA_EOL          #JavaEol
    | JAVA_TEXT         #JavaText
    | COMMENT_START     #CommentStart
    | COMMENT_END       #CommentEnd
    | COMMENT_TEXT      #CommentText
    | COMMENT_EOL       #CommentEol
    | JAVA_EXPR_START   #JavaVarStart
    | JAVA_EXPR         #JavaVar
    | JAVA_EXPR_END     #JavaVarEnd
    ;
