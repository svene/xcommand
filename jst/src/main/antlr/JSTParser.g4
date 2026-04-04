grammar JSTParser;

// =====================
// Parser
// =====================

start
    : (javaEol
     | javaText
     | commentStart
     | commentEnd
     | commentText
     | commentEol
     | javaVarStart
     | javaVarEnd
     | javaVar
     )* eof
    ;

javaText
    : JAVA_TEXT
      {
        parserCV.setToken($JAVA_TEXT);
        parserCV.setValue($JAVA_TEXT.text);
        javaTextNotifier.execute();
      }
    ;

commentStart
    : COMMENT_START
      {
        parserCV.setToken($COMMENT_START);
        inCommentState = true;
        commentStartNotifier.execute();
      }
    ;

commentText
    : COMMENT_TEXT
      {
        parserCV.setToken($COMMENT_TEXT);
        parserCV.setValue($COMMENT_TEXT.text);
        commentTextNotifier.execute();
      }
    ;

commentEnd
    : COMMENT_END
      {
        parserCV.setToken($COMMENT_END);
        inCommentState = false;
        commentEndNotifier.execute();
      }
    ;

javaVarStart
    : JAVA_EXPR_START
      {
        parserCV.setToken($JAVA_EXPR_START);
        javaVarStartNotifier.execute();
      }
    ;

javaVar
    : JAVA_EXPR
      {
        parserCV.setToken($JAVA_EXPR);
        parserCV.setValue($JAVA_EXPR.text);
        javaVarNotifier.execute();
      }
    ;

javaVarEnd
    : JAVA_EXPR_END
      {
        parserCV.setToken($JAVA_EXPR_END);
        javaVarEndNotifier.execute();
      }
    ;

javaEol
    : JAVA_EOL
      {
        parserCV.setToken($JAVA_EOL);
        eolInJavaNotifier.execute();
      }
    ;

commentEol
    : COMMENT_EOL
      {
        parserCV.setToken($COMMENT_EOL);
        eolInCommentNotifier.execute();
      }
    ;

eof
    : EOF
      {
        parserCV.setToken(null);
        eofNotifier.execute();
      }
    ;

// =====================
// Lexer
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

// ---------- COMMENT MODE ----------
mode IN_COMMENT;

COMMENT_END
    : '#*/' -> popMode
    ;

COMMENT_EOL
    : '\r\n' | '\n' | '\r'
    ;

JAVA_EXPR_START
    : '$jv{' -> pushMode(IN_JAVA_EXPR)
    ;

COMMENT_TEXT
    : .
    ;

// ---------- JAVA EXPR MODE ----------
mode IN_JAVA_EXPR;

JAVA_EXPR_END
    : '}' -> popMode
    ;

fragment DIGIT
    : [0-9A-Z]
    ;

fragment LETTER
    : [a-zA-Z_]
    ;

fragment JAVA_CHARS
    : '.' | '(' | ')'
    ;

JAVA_EXPR
    : (LETTER | DIGIT | JAVA_CHARS)*
    ;
