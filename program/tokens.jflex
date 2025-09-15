/*-***
 *
 * This file defines a stand-alone lexical analyzer for a subset of the Pascal
 * programming language.  This is the same lexer that will later be integrated
 * with a CUP-based parser.  Here the lexer is driven by the simple Java test
 * program in ./PascalLexerTest.java, q.v.  See 330 Lecture Notes 2 and the
 * Assignment 2 writeup for further discussion.
 *
 */


import java_cup.runtime.*;


%%
/*-*
 * LEXICAL FUNCTIONS:
 */

%cup
%line
%column
%unicode
%class Lexer

%{

/**
 * Return a new Symbol with the given token id, and with the current line and
 * column numbers.
 */
Symbol newSym(int tokenId) {
    return new Symbol(tokenId, yyline, yycolumn);
}

/**
 * Return a new Symbol with the given token id, the current line and column
 * numbers, and the given token value.  The value is used for tokens such as
 * identifiers and numbers.
 */
Symbol newSym(int tokenId, Object value) {
    return new Symbol(tokenId, yyline, yycolumn, value);
}

%}


/*-*
 * PATTERN DEFINITIONS:
 */

letter=[a-zA-Z]
digit=[0-9]

identifier = {letter}({letter}|{digit})*
integer_lit = {digit}{digit}*
character_lit = \'([^\'\\]|\\.)\'
floating_lit = {digit}+\.{digit}+
string_lit = \"([^\n\t\\\"]|\\.)*\"

whitespace = [ \n\r\t]
single_line_comment = \\\\.*
multi_line_comment = (\\\*)([^\*]|(\*+[^\*\\]))*(\*+\\)


%%
/**
 * LEXICAL RULES:
 */
class			        { return newSym(sym.CLASS, "class"); }
final                   { return newSym(sym.FINAL, "final"); }
void                    { return newSym(sym.VOID, "void"); }
int                     { return newSym(sym.INT, "int"); }
char                    { return newSym(sym.CHAR, "char"); }
bool                    { return newSym(sym.BOOL, "bool"); }
float                   { return newSym(sym.FLOAT, "float"); }
if                      { return newSym(sym.IF, "if"); }
while                   { return newSym(sym.WHILE, "while"); }
read                    { return newSym(sym.READ, "read"); }
print                   { return newSym(sym.PRINT, "print"); }
printline               { return newSym(sym.PRINTLINE, "printline"); }
return                  { return newSym(sym.RETURN, "return"); }
else                    { return newSym(sym.ELSE, "else"); }
true                    { return newSym(sym.TRUE, "true"); }
false                   { return newSym(sym.FALSE, "false"); }
"<="                    { return newSym(sym.LESSEQUAL, "<="); }
">="                    { return newSym(sym.GREATEREQUAL, ">="); }
"=="                    { return newSym(sym.EQUALEQUAL, "=="); }
"<>"                    { return newSym(sym.NOTEQUAL, "<>"); }
"||"                    { return newSym(sym.LOGOR, "||"); }
"&&"                    { return newSym(sym.LOGAND, "&&"); }
"++"                    { return newSym(sym.PLUSPLUS, "++"); }
"--"                    { return newSym(sym.MINUSMINUS, "--"); }
"{"                     { return newSym(sym.OPENCURL, "{"); }
"}"                     { return newSym(sym.CLOSECURL, "}"); }
"["                     { return newSym(sym.OPENSQR, "["); }
"]"                     { return newSym(sym.CLOSESQR, "]"); }
"("                     { return newSym(sym.OPENPAR, "("); }
")"                     { return newSym(sym.CLOSEPAR, ")"); }
"="                     { return newSym(sym.EQUAL, "="); }
";"                     { return newSym(sym.SEMICOLON, ";"); }
"?"                     { return newSym(sym.QUESTION, "?"); }
":"                     { return newSym(sym.COLON, ":"); }
","                     { return newSym(sym.COMMA, ","); }
"~"                     { return newSym(sym.TILDE, "~"); }
"-"                     { return newSym(sym.MINUS, "-"); }
"+"                     { return newSym(sym.PLUS, "+"); }
"*"                     { return newSym(sym.MULTIPLY, "*"); }
"/"                     { return newSym(sym.DIVIDE, "/"); }
"<"                     { return newSym(sym.SMALLER, "<"); }
">"                     { return newSym(sym.GREATER, ">"); }
{floating_lit}          { return newSym(sym.FLOATLIT, Float.parseFloat(yytext())); }
{integer_lit}           { return newSym(sym.INTLIT, Integer.parseInt(yytext())); }
{character_lit}         { return newSym(sym.CHARLIT, yytext().charAt(1)); }
{string_lit}            { return newSym(sym.STRLIT, yytext()); }
{identifier}            { return newSym(sym.ID, yytext()); }
{single_line_comment}   { /* Ignore single-line line comment. */ }
{multi_line_comment}    { /* Ignore multi-line line comment. */ }
{whitespace}            { /* Ignore whitespace. */ }
.                       { System.out.println("Illegal char, '" + yytext() +
                            "' line: " + yyline + ", column: " + yychar); }