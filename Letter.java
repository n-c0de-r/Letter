import java.security.SecureRandom;
import java.util.*;

/**
 * Maintain the environment for a 2D characterular automaton.
 * 
 * @author David J. Barnes
 * @version  2016.02.29
 */
public class Letter
{
    // Default size for the environment.
    private static final int DEFAULT_ROWS = 65;
    private static final int DEFAULT_COLS = 125;

    private static final int START_Y = 5;
    private static final int START_X = 5;
    private int current_y;
    private int current_x;

    // The grid of characters.
    private Character[][] characters;
    // Visualization of the environment.
    private final LetterView view;

    /**
     * Create an environment with the default size.
     */
    public Letter()
    {
        setup(DEFAULT_ROWS, DEFAULT_COLS);
        //randomize();
        view = new LetterView(this, DEFAULT_ROWS, DEFAULT_COLS);
        makeBorder();
        view.showCharacters();
        current_y = START_Y;
        current_x = START_X;
        processLetter("Hello Prof.");
        newLine();
        processLetter("YOUR NAME HERE,");
        newLine();
        newLine();
        processLetter("Thank you for all");
        newLine();
        processLetter("your time, effort and patience.");
        newLine();
        processLetter("We wish you a great sabbatical. Enjoy");
        newLine();
        processLetter("your time, stay...");
        waiting(1000);
        transition();
        transition();
        transition();
        reset();
        makeBorder();
        processLetter("healthy and get a");
        newLine();
        processLetter("lot of well earned rest. We will see you in the next, better");
        newLine();
        processLetter("winter-semester.");
        newLine();
        newLine();
        processLetter("Sincerely,");
        newLine();
        newLine();
        processLetter("your imi-students.");
        waiting(1000);
        transition();
        transition();
        transition();
    }

    /**
     * Run the automaton for one step.
     */
    public void step()
    {
        int numRows = characters.length;
        int numCols = characters[0].length;
        // Build a record of the next state of each character.
        int[][] nextStates = new int[numRows][numCols];
        // Ask each character to determine its next state.
        for(int row = 0; row < numRows; row++) {
            int[] rowOfStates = nextStates[row];
            for(int col = 0; col < numCols; col++) {
                rowOfStates[col] = characters[row][col].getNextState();
            }
        }
        // Update the characters' states.
        for(int row = 0; row < numRows; row++) {
            int[] rowOfStates = nextStates[row];
            for(int col = 0; col < numCols; col++) {
                setCharacterState(row, col, rowOfStates[col]);
            }
        }
        view.showCharacters();
    }

    /**
     * 
     * 
     * Set the state of one character.
     * @param row The character's row.
     * @param col The character's col.
     * @param state The character's state.
     */
    public void setCharacterState(int row, int col, int state)
    {
        characters[row][col].setState(state);
    }

    /**
     * Return the grid of characters.
     * @return The grid of characters.
     */
    public Character[][] getCharacters()
    {
        return characters;
    }

    /**
     * Setup a new environment of the given size.
     * @param numRows The number of rows.
     * @param numCols The number of cols;
     */
    private void setup(int numRows, int numCols)
    {
        characters = new Character[numRows][numCols];
        for(int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                characters[row][col] = new Character();
            }
        }
        setupNeighbors();
    }

    /**
     * Give to a character a list of its neighbors.
     */
    private void setupNeighbors()
    {
        int numRows = characters.length;
        int numCols = characters[0].length;
        // Allow for 8 neighbors plus the character.
        ArrayList<Character> neighbors = new ArrayList<>(9);
        for(int row = 0; row < numRows; row++) {
            for(int col = 0; col < numCols; col++) {
                Character character = characters[row][col];
                // This process will also include the character.
                for(int dr = -1; dr <= 1; dr++) {
                    for(int dc = -1; dc <= 1; dc++) {
                        //makes the characters not go over borders
                        int nr = (row + dr);
                        int nc = (col + dc);
                        //checks if the neighbor character is within the borders
                        if(nr > 0 && nr < numRows &&
                        nc > 0 && nc < numCols){
                            neighbors.add(characters[nr][nc]);
                        }
                    } 
                }
                // The neighbours should not include the character at
                // (row,col) so remove it.
                neighbors.remove(character);
                character.setNeighbors(neighbors);
                neighbors.clear();
            }
        }
    }

    /**
     * Generate Letters
     */
    private void A(int y, int x) {

        setCharacterState(y+1,x,0);
        setCharacterState(y+2,x,0);
        setCharacterState(y+3,x,0);
        setCharacterState(y+4,x,0);

        setCharacterState(y,x+1,0);
        setCharacterState(y+2,x+1,0);

        setCharacterState(y,x+2,0);
        setCharacterState(y+2,x+2,0);

        setCharacterState(y,x+3,0);
        setCharacterState(y+2,x+3,0);

        setCharacterState(y+1,x+4,0);
        setCharacterState(y+2,x+4,0);
        setCharacterState(y+3,x+4,0);
        setCharacterState(y+4,x+4,0);
    }

    private void B(int y, int x) {
        setCharacterState(y,x,0);
        setCharacterState(y+1,x,0);
        setCharacterState(y+2,x,0);
        setCharacterState(y+3,x,0);
        setCharacterState(y+4,x,0);

        setCharacterState(y,x+1,0);
        setCharacterState(y+2,x+1,0);
        setCharacterState(y+4,x+1,0);

        setCharacterState(y,x+2,0);
        setCharacterState(y+2,x+2,0);
        setCharacterState(y+4,x+2,0);

        setCharacterState(y,x+3,0);
        setCharacterState(y+2,x+3,0);
        setCharacterState(y+4,x+3,0);

        setCharacterState(y+1,x+4,0);
        setCharacterState(y+3,x+4,0);
    }

    private void C(int y, int x) {
        setCharacterState(y+1,x,0);
        setCharacterState(y+2,x,0);
        setCharacterState(y+3,x,0);

        setCharacterState(y,x+1,0);
        setCharacterState(y+4,x+1,0);

        setCharacterState(y,x+2,0);
        setCharacterState(y+4,x+2,0);

        setCharacterState(y,x+3,0);
        setCharacterState(y+4,x+3,0);

        setCharacterState(y,x+4,0);
        setCharacterState(y+4,x+4,0);
    }

    private void D(int y, int x) {
        setCharacterState(y,x,0);
        setCharacterState(y+1,x,0);
        setCharacterState(y+2,x,0);
        setCharacterState(y+3,x,0);
        setCharacterState(y+4,x,0);

        setCharacterState(y,x+1,0);
        setCharacterState(y+4,x+1,0);

        setCharacterState(y,x+2,0);
        setCharacterState(y+4,x+2,0);

        setCharacterState(y,x+3,0);
        setCharacterState(y+4,x+3,0);

        setCharacterState(y+1,x+4,0);
        setCharacterState(y+2,x+4,0);
        setCharacterState(y+3,x+4,0);
    }

    private void E(int y, int x) {
        setCharacterState(y,x,0);
        setCharacterState(y+1,x,0);
        setCharacterState(y+2,x,0);
        setCharacterState(y+3,x,0);
        setCharacterState(y+4,x,0);

        setCharacterState(y,x+1,0);
        setCharacterState(y+2,x+1,0);
        setCharacterState(y+4,x+1,0);

        setCharacterState(y,x+2,0);
        setCharacterState(y+2,x+2,0);
        setCharacterState(y+4,x+2,0);

        setCharacterState(y,x+3,0);
        setCharacterState(y+4,x+3,0);

        setCharacterState(y,x+4,0);
        setCharacterState(y+4,x+4,0);
    }

    private void F(int y, int x) {
        setCharacterState(y,x,0);
        setCharacterState(y+1,x,0);
        setCharacterState(y+2,x,0);
        setCharacterState(y+3,x,0);
        setCharacterState(y+4,x,0);

        setCharacterState(y,x+1,0);
        setCharacterState(y+2,x+1,0);

        setCharacterState(y,x+2,0);
        setCharacterState(y+2,x+2,0);

        setCharacterState(y,x+3,0);

        setCharacterState(y,x+4,0);
    }

    private void G(int y, int x) {
        setCharacterState(y+1,x,0);
        setCharacterState(y+2,x,0);
        setCharacterState(y+3,x,0);

        setCharacterState(y,x+1,0);
        setCharacterState(y+4,x+1,0);

        setCharacterState(y,x+2,0);
        setCharacterState(y+2,x+2,0);
        setCharacterState(y+4,x+2,0);

        setCharacterState(y,x+3,0);
        setCharacterState(y+2,x+3,0);
        setCharacterState(y+4,x+3,0);

        setCharacterState(y,x+4,0);
        setCharacterState(y+2,x+4,0);
        setCharacterState(y+3,x+4,0);
    }

    private void H(int y, int x) {
        setCharacterState(y,x,0);
        setCharacterState(y+1,x,0);
        setCharacterState(y+2,x,0);
        setCharacterState(y+3,x,0);
        setCharacterState(y+4,x,0);

        setCharacterState(y+2,x+1,0);

        setCharacterState(y+2,x+2,0);

        setCharacterState(y+2,x+3,0);

        setCharacterState(y,x+4,0);
        setCharacterState(y+1,x+4,0);
        setCharacterState(y+2,x+4,0);
        setCharacterState(y+3,x+4,0);
        setCharacterState(y+4,x+4,0);
    }

    private void I(int y, int x) {
        setCharacterState(y,x,0);
        setCharacterState(y+1,x,0);
        setCharacterState(y+2,x,0);
        setCharacterState(y+3,x,0);
        setCharacterState(y+4,x,0);
    }

    private void J(int y, int x) {
        setCharacterState(y+3,x,0);

        setCharacterState(y+4,x+1,0);

        setCharacterState(y+4,x+2,0);

        setCharacterState(y+4,x+3,0);

        setCharacterState(y,x+4,0);
        setCharacterState(y+1,x+4,0);
        setCharacterState(y+2,x+4,0);
        setCharacterState(y+3,x+4,0);
    }

    private void K(int y, int x) {
        setCharacterState(y,x,0);
        setCharacterState(y+1,x,0);
        setCharacterState(y+2,x,0);
        setCharacterState(y+3,x,0);
        setCharacterState(y+4,x,0);

        setCharacterState(y+2,x+1,0);

        setCharacterState(y+2,x+2,0);

        setCharacterState(y+1,x+3,0);
        setCharacterState(y+3,x+3,0);

        setCharacterState(y,x+4,0);
        setCharacterState(y+4,x+4,0);
    }

    private void L(int y, int x) {
        setCharacterState(y,x,0);
        setCharacterState(y+1,x,0);
        setCharacterState(y+2,x,0);
        setCharacterState(y+3,x,0);
        setCharacterState(y+4,x,0);

        setCharacterState(y+4,x+1,0);

        setCharacterState(y+4,x+2,0);

        setCharacterState(y+4,x+3,0);

        setCharacterState(y+4,x+4,0);
    }

    private void M(int y, int x) {
        setCharacterState(y,x,0);
        setCharacterState(y+1,x,0);
        setCharacterState(y+2,x,0);
        setCharacterState(y+3,x,0);
        setCharacterState(y+4,x,0);

        setCharacterState(y+1,x+1,0);

        setCharacterState(y+2,x+2,0);

        setCharacterState(y+1,x+3,0);

        setCharacterState(y,x+4,0);
        setCharacterState(y+1,x+4,0);
        setCharacterState(y+2,x+4,0);
        setCharacterState(y+3,x+4,0);
        setCharacterState(y+4,x+4,0);
    }

    private void N(int y, int x) {
        setCharacterState(y,x,0);
        setCharacterState(y+1,x,0);
        setCharacterState(y+2,x,0);
        setCharacterState(y+3,x,0);
        setCharacterState(y+4,x,0);

        setCharacterState(y+1,x+1,0);

        setCharacterState(y+2,x+2,0);

        setCharacterState(y+3,x+3,0);

        setCharacterState(y,x+4,0);
        setCharacterState(y+1,x+4,0);
        setCharacterState(y+2,x+4,0);
        setCharacterState(y+3,x+4,0);
        setCharacterState(y+4,x+4,0);
    }

    private void O(int y, int x) {
        setCharacterState(y+1,x,0);
        setCharacterState(y+2,x,0);
        setCharacterState(y+3,x,0);

        setCharacterState(y,x+1,0);
        setCharacterState(y+4,x+1,0);

        setCharacterState(y,x+2,0);
        setCharacterState(y+4,x+2,0);

        setCharacterState(y,x+3,0);
        setCharacterState(y+4,x+3,0);

        setCharacterState(y+1,x+4,0);
        setCharacterState(y+2,x+4,0);
        setCharacterState(y+3,x+4,0);
    }

    private void P(int y, int x) {
        setCharacterState(y,x,0);
        setCharacterState(y+1,x,0);
        setCharacterState(y+2,x,0);
        setCharacterState(y+3,x,0);
        setCharacterState(y+4,x,0);

        setCharacterState(y,x+1,0);
        setCharacterState(y+2,x+1,0);

        setCharacterState(y,x+2,0);
        setCharacterState(y+2,x+2,0);

        setCharacterState(y,x+3,0);
        setCharacterState(y+2,x+3,0);

        setCharacterState(y+1,x+4,0);
    }

    private void Q(int y, int x) {
        setCharacterState(y+1,x,0);
        setCharacterState(y+2,x,0);
        setCharacterState(y+3,x,0);

        setCharacterState(y,x+1,0);
        setCharacterState(y+4,x+1,0);

        setCharacterState(y,x+2,0);
        setCharacterState(y+2,x+2,0);
        setCharacterState(y+4,x+2,0);

        setCharacterState(y,x+3,0);
        setCharacterState(y+3,x+3,0);

        setCharacterState(y+1,x+4,0);
        setCharacterState(y+2,x+4,0);
        setCharacterState(y+4,x+4,0);
    }

    private void R(int y, int x) {
        setCharacterState(y,x,0);
        setCharacterState(y+1,x,0);
        setCharacterState(y+2,x,0);
        setCharacterState(y+3,x,0);
        setCharacterState(y+4,x,0);

        setCharacterState(y,x+1,0);
        setCharacterState(y+2,x+1,0);

        setCharacterState(y,x+2,0);
        setCharacterState(y+2,x+2,0);

        setCharacterState(y,x+3,0);
        setCharacterState(y+2,x+3,0);

        setCharacterState(y+1,x+4,0);
        setCharacterState(y+3,x+4,0);
        setCharacterState(y+4,x+4,0);
    }

    private void S(int y, int x) {
        setCharacterState(y+1,x,0);
        setCharacterState(y+4,x,0);

        setCharacterState(y,x+1,0);
        setCharacterState(y+2,x+1,0);
        setCharacterState(y+4,x+1,0);

        setCharacterState(y,x+2,0);
        setCharacterState(y+2,x+2,0);
        setCharacterState(y+4,x+2,0);

        setCharacterState(y,x+3,0);
        setCharacterState(y+2,x+3,0);
        setCharacterState(y+4,x+3,0);

        setCharacterState(y,x+4,0);
        setCharacterState(y+3,x+4,0);
    }

    private void T(int y, int x) {
        setCharacterState(y,x,0);

        setCharacterState(y,x+1,0);

        setCharacterState(y,x+2,0);
        setCharacterState(y+1,x+2,0);
        setCharacterState(y+2,x+2,0);
        setCharacterState(y+3,x+2,0);
        setCharacterState(y+4,x+2,0);

        setCharacterState(y,x+3,0);

        setCharacterState(y,x+4,0);
    }

    private void U(int y, int x) {
        setCharacterState(y,x,0);
        setCharacterState(y+1,x,0);
        setCharacterState(y+2,x,0);
        setCharacterState(y+3,x,0);

        setCharacterState(y+4,x+1,0);

        setCharacterState(y+4,x+2,0);

        setCharacterState(y+4,x+3,0);

        setCharacterState(y,x+4,0);
        setCharacterState(y+1,x+4,0);
        setCharacterState(y+2,x+4,0);
        setCharacterState(y+3,x+4,0);
    }

    private void V(int y, int x) {
        setCharacterState(y,x,0);
        setCharacterState(y+1,x,0);
        setCharacterState(y+2,x,0);

        setCharacterState(y+3,x+1,0);

        setCharacterState(y+4,x+2,0);

        setCharacterState(y+3,x+3,0);

        setCharacterState(y,x+4,0);
        setCharacterState(y+1,x+4,0);
        setCharacterState(y+2,x+4,0);
    }

    private void W(int y, int x) {
        setCharacterState(y,x,0);
        setCharacterState(y+1,x,0);
        setCharacterState(y+2,x,0);
        setCharacterState(y+3,x,0);
        setCharacterState(y+4,x,0);

        setCharacterState(y+3,x+1,0);

        setCharacterState(y+2,x+2,0);

        setCharacterState(y+3,x+3,0);

        setCharacterState(y,x+4,0);
        setCharacterState(y+1,x+4,0);
        setCharacterState(y+2,x+4,0);
        setCharacterState(y+3,x+4,0);
        setCharacterState(y+4,x+4,0);
    }

    private void X(int y, int x) {
        setCharacterState(y,x,0);
        setCharacterState(y+4,x,0);

        setCharacterState(y+1,x+1,0);
        setCharacterState(y+3,x+1,0);

        setCharacterState(y+2,x+2,0);

        setCharacterState(y+1,x+3,0);
        setCharacterState(y+3,x+3,0);

        setCharacterState(y,x+4,0);
        setCharacterState(y+4,x+4,0);
    }

    private void Y(int y, int x) {
        setCharacterState(y,x,0);

        setCharacterState(y+1,x+1,0);

        setCharacterState(y+2,x+2,0);
        setCharacterState(y+3,x+2,0);
        setCharacterState(y+4,x+2,0);

        setCharacterState(y+1,x+3,0);

        setCharacterState(y,x+4,0);
    }

    private void Z(int y, int x) {
        setCharacterState(y,x,0);
        setCharacterState(y+4,x,0);

        setCharacterState(y,x+1,0);
        setCharacterState(y+3,x+1,0);
        setCharacterState(y+4,x+1,0);

        setCharacterState(y,x+2,0);
        setCharacterState(y+2,x+2,0);
        setCharacterState(y+4,x+2,0);

        setCharacterState(y,x+3,0);
        setCharacterState(y+1,x+3,0);
        setCharacterState(y+4,x+3,0);

        setCharacterState(y,x+4,0);
        setCharacterState(y+4,x+4,0);
    }

    private void period(int y, int x) {
        setCharacterState(y+4,x,0);
    }

    private void comma(int y, int x) {
        setCharacterState(y+3,x,0);
        setCharacterState(y+4,x,0);
        setCharacterState(y+5,x-1,0);
    }

    private void hyphen(int y, int x) {
        setCharacterState(y+2,x,0);

        setCharacterState(y+2,x+1,0);

        setCharacterState(y+2,x+2,0);
    }

    private void makeBorder(){
        for(int row = 0; row < DEFAULT_ROWS; row++) {
            for (int col = 0; col < DEFAULT_COLS; col++) {
                if ((row < 2 || row >= DEFAULT_ROWS-2)
                && row%2 == col%2) {
                    setCharacterState(row,col,0);
                    view.showCharacters();
                    waiting(5);
                } else if ((col < 2 || col >= DEFAULT_COLS-2)
                && row%2 == col%2){
                    setCharacterState(row,col,0);
                    view.showCharacters();
                    waiting(5);
                }
            }
        }
    }

    private void processLetter(String line){
        for (int chars = 0; chars<line.length(); chars++){
            if (current_x+6 > DEFAULT_COLS-2){
                newLine();
            }
            checkChar(line.toUpperCase().charAt(chars));
            view.showCharacters();
            waiting(50);
        }
    }

    private void checkChar(char c){
        switch (c){
            case 'A':
            A(current_y, current_x);
            current_x += 6;
            break;
            case 'B':
            B(current_y, current_x);
            current_x += 6;
            break;
            case 'C':
            C(current_y, current_x);
            current_x += 6;
            break;
            case 'D':
            D(current_y, current_x);
            current_x += 6;
            break;
            case 'E':
            E(current_y, current_x);
            current_x += 6;
            break;
            case 'F':
            F(current_y, current_x);
            current_x += 6;
            break;
            case 'G':
            G(current_y, current_x);
            current_x += 6;
            break;
            case 'H':
            H(current_y, current_x);
            current_x += 6;
            break;
            case 'I':
            I(current_y, current_x);
            current_x += 2;
            break;
            case 'J':
            J(current_y, current_x);
            current_x += 6;
            break;
            case 'K':
            K(current_y, current_x);
            current_x += 6;
            break;
            case 'L':
            L(current_y, current_x);
            current_x += 6;
            break;
            case 'M':
            M(current_y, current_x);
            current_x += 6;
            break;
            case 'N':
            N(current_y, current_x);
            current_x += 6;
            break;
            case 'O':
            O(current_y, current_x);
            current_x += 6;
            break;
            case 'P':
            P(current_y, current_x);
            current_x += 6;
            break;
            case 'Q':
            Q(current_y, current_x);
            current_x += 6;
            break;
            case 'R':
            R(current_y, current_x);
            current_x += 6;
            break;
            case 'S':
            S(current_y, current_x);
            current_x += 6;
            break;
            case 'T':
            T(current_y, current_x);
            current_x += 6;
            break;
            case 'U':
            U(current_y, current_x);
            current_x += 6;
            break;
            case 'V':
            V(current_y, current_x);
            current_x += 6;
            break;
            case 'W':
            W(current_y, current_x);
            current_x += 6;
            break;
            case 'X':
            X(current_y, current_x);
            current_x += 6;
            break;
            case 'Y':
            Y(current_y, current_x);
            current_x += 6;
            break;
            case 'Z':
            Z(current_y, current_x);
            current_x += 6;
            break;
            case ' ':
            current_x += 6;
            break;
            case ',':
            comma(current_y, current_x);
            current_x += 2;
            break;
            case '.':
            period(current_y, current_x);
            current_x += 2;
            break;
            case '-':
            hyphen(current_y, current_x);
            current_x += 4;
            break;
        }
    }

    private void newLine(){
        current_y += 6;
        current_x = START_X;
    }

    private void waiting(int time){
        try {
            Thread.sleep(time);
        }
        catch(InterruptedException e) {
        }
    }

    private void transition(){
        step();
        waiting(100);
        step();
        waiting(100);
        step();
        waiting(100);
        step();
        waiting(100);
        step();
        waiting(100);
    }

    public void reset(){
        int numRows = characters.length;
        int numCols = characters[0].length;
        for(int row = 0; row < numRows; row++) {
            for(int col = 0; col < numCols; col++) {
                setCharacterState(row, col, 1);
            }
            view.showCharacters();
            waiting(10);
        }
        current_y = START_Y;
        current_x = START_X;
    }
}
