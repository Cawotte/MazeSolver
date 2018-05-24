package Model;

import Model.Automata.AutomataMaze;

import java.util.ArrayList;

public class AutomataMazeSolver {

    public static void main(String[] args) {


        ArrayList<Maze> mazePool = new ArrayList<>();
        ArrayList<AutomataMaze> automataPool = new ArrayList<>();

        //Pool of mazes parameters.
        int mazeHeight = 20;
        int mazeWidth = 20;
        int nbMazes = 20;

        //Pool of automate parameters.
        int nbAutomatas = 50;
        int nbStates = 20;

        //Other parameters
        int nbGenMax = 50;
        int fitnessCap = 20;

        //We generate a pool of maze given different parameters
        for ( int i = 0; i < nbMazes; i++ ) {
            mazePool.add(new Maze(mazeHeight, mazeWidth) );
        }

        //We generate an initial pool of automata
        for ( int i = 0; i < nbAutomatas; i++) {
            automataPool.add(new AutomataMaze(nbStates));
        }

    }

}
