package mancala;

import utility.MockIO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * This class is the starting point for SOFTENG 701 Assignment 1.1 in 2013.
 */
public class Mancala {
    private boolean isRunning;
    private Board board;
    private int currentPlayer = 1;
    private int housesPerPlayer = 6;
    private int seedsPerHouse = 4;


	public static void main(String[] args) {
		new Mancala().play();
	}
	public void play(MockIO io) {

		io.println("+----+-------+-------+-------+-------+-------+-------+----+");
		io.println("| P2 | 6[ 4] | 5[ 4] | 4[ 4] | 3[ 4] | 2[ 4] | 1[ 4] |  0 |");
		io.println("|    |-------+-------+-------+-------+-------+-------|    |");
		io.println("|  0 | 1[ 4] | 2[ 4] | 3[ 4] | 4[ 4] | 5[ 4] | 6[ 4] | P1 |");
		io.println("+----+-------+-------+-------+-------+-------+-------+----+");
		io.println("Player 1's turn - Specify house number or 'q' to quit: ");
        /* This is the initial display but needs to take in and process the input*/
	}
    public void play(){
        /* Give user some reading material while board is created*/
        System.out.println("Welcome to Mancala");
        board = new Board(housesPerPlayer, seedsPerHouse);
        isRunning = true;
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String input;

        while (isRunning) {
            board.printBoard();
            board.setPlayer(currentPlayer);
            promptPlayer(currentPlayer);
            try {
                input = in.readLine().trim();
            } catch (IOException e) {
                input = null;
            }

            if (input.equals("q") || input==null) {
                isRunning = false;
                System.out.println("Mancala terminated");
                break;
            }
            /* Convert input to integer*/
            int selectedHouse;
            try {
                selectedHouse = Integer.parseInt(input);
                if (selectedHouse > housesPerPlayer || selectedHouse < 1) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                System.out.println(String.format("Please choose a number between 1 and %d", housesPerPlayer));
                continue;
            }

            /* Get and distribute seeds*/
            int seeds = board.getSeedsFromHouse(selectedHouse);
            for (int i = 0; i < seeds; i++){
                GameMove move = board.insertSeedIntoNextContainer(seeds-i);
            }
        }


    }

    private void promptPlayer(int player){
        System.out.println(String.format("Player %d's turn - Specify house number or 'q' to quit: ", player));
    }
}
