import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.LinkedList;

public class LexemeAnalyzer {

    // token codes
    public static final int INT_LIT = 10;       // integer literal
    public static final int IDENT = 11;         // identifier
    public static final int ASSIGN_OP = 20;     // assignment operator
    public static final int ADD_OP = 21;        // addition operator
    public static final int SUB_OP = 22;        // subtraction operator
    public static final int MULT_OP = 23;       // multiplication operator
    public static final int DIV_OP = 24;        // division operator
    public static final int LEFT_PAREN = 25;    // left parentheses
    public static final int RIGHT_PAREN = 26;   // right parentheses
    public static final int GREATER_THAN = 30;  // greater than
    public static final int LESS_THAN = 31;     // less than
    public static final int SEMI_COL = 40;      // semi-colon
    public static final int PROGRAM = 0;        // program
    public static final int BEGIN = 1;          // begin
    public static final int END = 99;           // end

    String lexeme = "";
    int tokenCode;
    Lexeme nextLexeme;
    LinkedList<Lexeme> lexemeList = new LinkedList<>();
    int nextIndex = 0;

    LexemeAnalyzer(File file){
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader( new FileInputStream(file), Charset.forName("UTF-8")));
            int c;
            while((c = reader.read()) != -1) {
                char character = (char) c;

                // end of lexeme, add it to the list
                if (!lexeme.isEmpty() && (Character.isWhitespace(character) || (!Character.isDigit(character) && !Character.isAlphabetic(character)))){
                    if (lexeme.equals("program"))
                        tokenCode = PROGRAM;
                    if (lexeme.equals("begin"))
                        tokenCode = BEGIN;
                    if (lexeme.equals("end"))
                        tokenCode = END;
                    nextLexeme = new Lexeme(lexeme, tokenCode);
                    lexemeList.add(nextLexeme);
                    lexeme = "";
                }

                // character isn't digit or alphabetic
                if (!Character.isWhitespace(character) && !Character.isAlphabetic(character) && !Character.isDigit(character)){
                    lexeme += character;
                    tokenCode = lookupTokenCode(character);
                    nextLexeme = new Lexeme(lexeme, tokenCode);
                    lexemeList.add(nextLexeme);
                    lexeme = "";
                }

                // add until a white space is found
                if (!lexeme.isEmpty()){
                    lexeme += character;
                }

                // first character of lexeme is a letter, lexeme is an identifier
                if (lexeme.isEmpty() && Character.isAlphabetic(character)){
                    tokenCode = IDENT;
                    lexeme += character;
                }

                // first character of lexeme is a digit, lexeme is an integer literal
                if (lexeme.isEmpty() && Character.isDigit(character)){
                    tokenCode = INT_LIT;
                    lexeme += character;
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private int lookupTokenCode(char c){
        switch (c){
            case '=':
                return ASSIGN_OP;
            case '+':
                return ADD_OP;
            case '-':
                return SUB_OP;
            case '*':
                return MULT_OP;
            case '/':
                return DIV_OP;
            case '(':
                return LEFT_PAREN;
            case ')':
                return RIGHT_PAREN;
            case '>':
                return GREATER_THAN;
            case '<':
                return LESS_THAN;
            case ';':
                return SEMI_COL;
        }
        return 0;
    }

    // returns the next lexeme
    Lexeme getNext(){
        return lexemeList.get(nextIndex++);
    }
}
