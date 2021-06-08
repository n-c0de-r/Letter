import java.util.*;

/**
 * A pixel in a 2D pixelated letter.
 */
public class Character
{
    // The possible states.
    public static final int WHITE = 0, BLACK = 1;
    // The number of possible states.
    public static final int NUM_STATES = 2;

    // The Character's state.
    private int state;
    // The Character's neighbors.
    private Character[] neighbors;

    /**
     * Set the initial state to be BLACK.
     */
    public Character()
    {
        this(BLACK);
    }

    /**
     * Set the initial state.
     * @param initialState The initial state
     */
    public Character(int initialState)
    {
        state = initialState;
        neighbors = new Character[0];
    }

    /**
     * Determine this Character's next state, based on the
     * state of its neighbors.
     * This is an implementation of the rules for Brian's Brain.
     * @return The next state.
     */
    public int getNextState()
    {
        int aliveCount = 0;
        for(Character n : neighbors){
            // Count the number of neighbors that are alive.
            if(n.getState() == WHITE) {
                aliveCount++;
            }
        }
        if(state == BLACK){
            if(aliveCount ==3){
                return WHITE;
            } else {
                return BLACK;
            }
        } else 
        if(aliveCount < 2 || aliveCount > 3){
            return BLACK;
        } else {
            return WHITE;
        }
    }

    /**
     * Receive the list of neighboring Characters and take
     * a copy.
     * @param neighborList Neighboring Characters.
     */
    public void setNeighbors(ArrayList<Character> neighborList)
    {
        neighbors = new Character[neighborList.size()];
        neighborList.toArray(neighbors);
    }

    /**
     * Get the state of this Character.
     * @return The state.
     */
    public int getState()
    {
        return state;
    }

    /**
     * Set the state of this Character.
     * @param The state.
     */
    public void setState(int state)
    {
        this.state = state;
    }   

}
