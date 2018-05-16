package Automata;

import java.util.ArrayList;
import java.util.Random;

public class Automata {

    protected int nbInput;
    protected int nbOutput;

    protected short[][] stateTable;
    protected short[][] actionTable;

    /*
        stateTable[currentState][input] = next state;
        actionTable[currentState][input] = action performed / output.
    */

    protected int initialState;
    protected int currentState;

    // Create a random automata with the given attributes.
    public Automata(int nbState, int nbInput, int nbOutput) {

        this.nbInput = nbInput;
        this.nbOutput = nbOutput;

        Random rand = new Random();

        this.stateTable = new short[nbState][nbInput];
        for (int i = 0; i < nbState; i++ ) {
            for (int j = 0; j < nbInput; j++) {
                stateTable[i][j] = (short)(rand.nextInt(nbState)+1); //random value between 1 and nbState included.
            }
        }

        this.actionTable = new short[nbState][nbInput];
        for (int i = 0; i < nbState; i++ ) {
            for (int j = 0; j < nbInput; j++) {
                actionTable[i][j] = (short)(rand.nextInt(nbOutput)+1); //random value between 1 and nbState included.
            }
        }

        initialState = rand.nextInt(nbState)+1;
        currentState = initialState;

    }

    public int next(int input) {
        int output = actionTable[currentState][input];
        currentState = stateTable[currentState][input];
        return output;
    }





}
