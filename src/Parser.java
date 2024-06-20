import java.io.*;

public class Parser {
    String err = "syntax error";
    String success = "parse successful";
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

    public static void main(String[] args) {
        File file = new File("noerrors.txt");
        LexemeAnalyzer lexemeAnalyzer = new LexemeAnalyzer(file);
        Parser parser = new Parser();

        parser.parseProgram(lexemeAnalyzer);
    }

    public void parseProgram(LexemeAnalyzer l){
        // checks for program begin program syntax
        if (l.getNext().tokenCode != PROGRAM) {
            System.err.println(err);
            return;
        }
        if (l.getNext().tokenCode != BEGIN) {
            System.err.println(err);
            return;
        }

        // parse statement list
        parseStatementList(l);

        // checks for end program syntax
        if (l.getNext().tokenCode != END)
            System.err.println(err);
        else
            System.out.println(success);

    }

    public void parseStatementList(LexemeAnalyzer l){
        // parse statement
        parseStatement(l);

        if (l.getNext().tokenCode != SEMI_COL)
            return;
        else
            l.nextIndex--;

        //check for more statements
        while (l.getNext().tokenCode == SEMI_COL) {
            if (l.getNext().tokenCode == END) {
                l.nextIndex -= 2;
                return;
            }
            l.nextIndex--;
            parseStatement(l);
        }

        l.nextIndex--;
    }

    public void parseStatement(LexemeAnalyzer l){
        if (l.getNext().lexeme.equals("if")){
            // parse if statement
            l.nextIndex--;
            parseIfStatement(l);
            return;
        }
        else
            l.nextIndex--;

        if (l.getNext().lexeme.equals("loop")){
            // parse if statement
            l.nextIndex--;
            parseLoopStatement(l);
            return;
        }
        else l.nextIndex--;

        // parse assignment statement
        parseAssignmentStatement(l);
    }

    public void parseAssignmentStatement(LexemeAnalyzer l){
        // parse variable
        parseVariable(l);

        // check lexeme for equals sign
        if (l.getNext().tokenCode != ASSIGN_OP)
            return;

        // parse expression
        parseExpression(l);
    }

    public void parseIfStatement(LexemeAnalyzer l){
        // check for "if"
        if (!(l.getNext().lexeme.equals("if")))
            return;

        // check for left parenthesis
        if (l.getNext().tokenCode != LEFT_PAREN)
            return;

        // parse logic expression
        parseLogicExpression(l);

        // check for right parenthesis
        if (l.getNext().tokenCode != RIGHT_PAREN)
            return;

        // check for "then"
        if (!l.getNext().lexeme.equals("then"))
            return;

        // parse statement
        parseStatement(l);
    }

    public void parseLoopStatement(LexemeAnalyzer l){
        // check for "loop"
        if (!l.getNext().lexeme.equals("loop"))
            return;

        // check for left parenthesis
        if (l.getNext().tokenCode != LEFT_PAREN)
            return;

        // parse logic expression
        parseLogicExpression(l);

        // check for right parenthesis
        if (l.getNext().tokenCode != RIGHT_PAREN)
            return;

        // parse statement
        parseStatement(l);
    }

    public void parseLogicExpression(LexemeAnalyzer l){
        // parse variable
        parseVariable(l);

        // check for greater than or less than
        Lexeme temp = l.getNext();
        if ((temp.tokenCode != LESS_THAN) && (temp.tokenCode != GREATER_THAN))
            return;

        // parse variable
        parseVariable(l);
    }

    public void parseVariable(LexemeAnalyzer l){
        if (l.getNext().tokenCode != IDENT)
            System.err.println(err);
    }

    public void parseExpression(LexemeAnalyzer l){
        // parse term
        parseTerm(l);

        // check for addition or subtraction
        Lexeme temp = l.getNext();
        if ((temp.tokenCode != ADD_OP) && (temp.tokenCode != SUB_OP))
            l.nextIndex--;
        else {
            parseTerm(l);
        }
    }

    public void parseTerm(LexemeAnalyzer l){
        // parse factor
        parseFactor(l);

        // check for multiplication or division
        Lexeme temp = l.getNext();
        if ((temp.tokenCode != MULT_OP) && (temp.tokenCode != DIV_OP))
            l.nextIndex--;
        else {
            parseFactor(l);
        }
    }

    public void parseFactor(LexemeAnalyzer l){
        if (l.getNext().tokenCode == IDENT)
            return;
        l.nextIndex--;

        if (l.getNext().tokenCode == INT_LIT)
            return;
        l.nextIndex--;

        if (l.getNext().tokenCode != LEFT_PAREN)
            return;
        else
            parseExpression(l);

        if (l.getNext().tokenCode != RIGHT_PAREN)
            return;
    }
}

