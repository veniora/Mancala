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
		new Mancala().play(new MockIO());
	}
	public void play(MockIO io) {

        board = new Board(housesPerPlayer, seedsPerHouse);
        isRunning = true;

        while(isRunning){
            board.printBoard();
            board.setPlayer(currentPlayer);
            promptPlayer(currentPlayer);
            int selectedHouse;
            selectedHouse = io.readInteger("", 1, 6, -1, "q");
            if (selectedHouse == -1){
                isRunning = false;
                io.println("Game over");
                board.printBoard();
                io.finished();
                break;
            }
            /* Get and distribute seeds*/
            int seeds = board.getSeedsFromHouse(currentPlayer, selectedHouse);
            if (seeds == 0){
                System.out.println("House is empty. Move again.");
                continue;
            }
            GameMove move = null;
            for (int i = 0; i < seeds; i++){
                move = board.insertSeedIntoNextContainer(seeds-i);

            }
            /* All seeds placed, look at result of final one*/
            switch (move){
                case GO_TO_NEXT:
                    /* Turn finished on a house in normal conditions*/
                    switchPlayer();
                    break;
                case EXTRA_TURN:
                    /*Turn ended in player's own store*/
                    break;
                case STEAL_SEEDS:
                    /*Turn ended in an empty house belonging to the player*/
                    board.emptyHousesIntoStore();
                    switchPlayer();
                    break;

            }
            isRunning = isThereAnotherTurn();

        }
//        board.printBoard();
//        promptPlayer(currentPlayer);


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
                System.out.println("Game over");
                board.printBoard();
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
            int seeds = board.getSeedsFromHouse(currentPlayer, selectedHouse);
            if (seeds == 0){
                System.out.println("House is empty. Move again.");
                continue;
            }
            GameMove move = null;
            for (int i = 0; i < seeds; i++){
                move = board.insertSeedIntoNextContainer(seeds-i);

            }
            /* All seeds placed, look at result of final one*/
            switch (move){
                case GO_TO_NEXT:
                    /* Turn finished on a house in normal conditions*/
                    switchPlayer();
                     break;
                case EXTRA_TURN:
                    /*Turn ended in player's own store*/
                    break;
                case STEAL_SEEDS:
                    /*Turn ended in an empty house belonging to the player*/
                    board.emptyHousesIntoStore();
                    switchPlayer();
                    break;

            }
            isRunning = isThereAnotherTurn();
        }


    }

    private void switchPlayer(){
        if (currentPlayer == 1){
            currentPlayer = 2;
        } else {
            currentPlayer = 1;
        }
    }
    private boolean isThereAnotherTurn() {
        /* Check if either player has empty houses*/
        int p1sum = board.countSeedsInPlayerHouses(1);
        int p2sum = board.countSeedsInPlayerHouses(2);
        if (p1sum == 0 || p2sum == 0) {
            System.out.println("Game over");
            p1sum += board.countSeedsInPlayerStore(1);
            p2sum += board.countSeedsInPlayerStore(2);
            System.out.println("    player 1:"+p1sum);
            System.out.println("    player 2:"+p2sum);
            if (p1sum > p2sum) {
                System.out.println("Player 1 wins!");
            } else if (p2sum > p1sum){
                System.out.println("Player 2 wins!");
            }
            return false;
        }
        return true;
    }

    private void promptPlayer(int player){
        System.out.println(String.format("Player %d's turn - Specify house number or 'q' to quit: ", player));
    }
}
