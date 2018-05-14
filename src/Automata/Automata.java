package Automata;

import java.util.ArrayList;
import java.util.Random;

public class Automata {

    private int[] possibleInput;
    private int[] possibleOutput;

    private int[][] stateTable;
    private int[][] actionTable;
    /*
        stateTable[currentState][input] = next state;
        actionTable[currentState][input] = action performed / output.
    */

    private int initialState;
    private int currentState;

    // Create a random automata with the given attributes.
    public Automata(ArrayList<Integer> possibleInput, ArrayList<Integer> possibleOutput, int nbState) {

        Random rand = new Random();
        int random_index;

        for (int i = 0; i < nbState; i++ ) {
            for (int j = 0; j < possibleInput.size(); j++) {

                stateTable[i][j] = rand.nextInt(nbState)+1; //random value between 1 and nbState included.

            }
        }

        for (int i = 0; i < nbState; i++ ) {
            for (int j = 0; j < possibleInput.size(); j++) {

                random_index = rand.nextInt(possibleOutput.size());
                stateTable[i][j] = possibleOutput.get(random_index);

            }
        }

    }





}
