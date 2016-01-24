package org.usfirst.frc.team5687.robot.scriptengine;

import java.io.Reader;

/**
 * The tokenizer is a low-level parser which accepts character input in the form of either a String or a Reader
 * Created by Ben Bernard on 1/22/2016.
 */
public class Tokenizer {
    private String sourceString;
    private Reader sourceReader;

    private Token top = null;
    private int next = 0;


    /***
     * Constructs a tokenizer to consume the provided string
     * @param string The string input to tokenize.
     */
    public Tokenizer(String string) {
        sourceString = string;
    }

    /***
     * Consume and return the next token.
     *
     * @return
     */
    public Token pop() {
        Token result = top;
        if (result!=null) {
            top = null;
        } else {
            result = parseToken();
        }
        return result;
    }

    /***
     * Return the next token without consuming it.
     *
     * @return
     */
    public Token peek() {
        if (top==null) {
            top = parseToken();
        }
        return top;
    }

    private final String WS = " \t";
    private final String digits = "0123456789";

    /***
     * Parse and return a token.  Leaves the next pointer at the first unconsumed character.
     * @return
     */
    private Token parseToken() {
        if (next>sourceString.length()-1) {
            return null;
        }
        String nextChar = sourceString.substring(next,next);
        // Consume any whitespace
        while (WS.contains(nextChar)) {
            next++;
            if (next>sourceString.length()-1) {
                return null;
            }
            nextChar = sourceString.substring(next, next);
        }

        // Next character is non-WS...but what is it?
        if ("#".equals(nextChar)) {
            return parseCommentToken();
        } else if (digits.contains(sourceString.substring(next,next))) {
            return parseNumberToken();
        } else {
            return parseSymbolToken();
        }

    }

    private CommentToken parseCommentToken() {
        // Since we're only handling one-line-per-tokenizer, the comment extends to the end of the string
        String comment = sourceString.substring(next, sourceString.length()-1);
        next = sourceString.length();
        return new CommentToken(comment);
    }

    private SymbolToken parseSymbolToken() {
        // Everything from next to the character before the next WS is the symbol
        return null;

    }

    private NumericToken parseNumberToken() {
        return null;
    }


    abstract public class Token {
    }

    public class SymbolToken extends Token {
        private String symbol;

        protected SymbolToken(String symbol) {
            this.symbol = symbol;
        }
    }

    /***
     *
     */
    public class NumericToken extends Token {
        private double value;

        protected NumericToken(String number) {
            value = Double.parseDouble(number);
        }
    }

    /***
     * A comment starts with a # and runs to the end of the line
     */
    public class CommentToken extends Token {
        private String comment;

        protected CommentToken(String comment) {
            this.comment = comment;
        }
    }

}
