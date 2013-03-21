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
        board = new Board();
        isRunning = true;
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        while (isRunning){
            board.printBoard();
            promptPlayer(1);
            try {
                String input = in.readLine();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }


    }

    private void promptPlayer(int player){
        System.out.println(String.format("Player %d's turn - Specify house number or 'q' to quit: ", player));
    }
}
