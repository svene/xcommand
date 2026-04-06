parser grammar TemplateParser;

@header {
    package org.collage.parser.antlr;
}

options { tokenVocab = TemplateLexer; }

// =====================
// Parser rules
// =====================

start
    : element* EOF
    ;

element
    : VAR_START     #VarStart
    | VAR_NAME      #VarName
    | VAR_END       #VarEnd
    | EOL           #Eol
    | JAVA_START    #JavaStart
    | JAVA_CODE     #JavaCode
    | JAVA_END      #JavaEnd
    | TEXT          #Text
    ;
