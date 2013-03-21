package mancala;

/**
 * Created with IntelliJ IDEA.
 * User: michael
 * Date: 21/03/13
 * Time: 11:36 AM
 * To change this template use File | Settings | File Templates.
 */
public class Board {
    private Board mInstance = null;
    private int housesPerPlayer;
    private int seedsPerHouse;
    private int initialSeedsInStore = 0;
    private SeedContainer boardArray[];

    public Board() {
        housesPerPlayer = 6;
        seedsPerHouse = 4;
        createBoard();
    }

    public Board(int housesPerPlayer, int seedsPerHouse) {
        this.housesPerPlayer = housesPerPlayer;
        this.seedsPerHouse = seedsPerHouse;
        createBoard();
    }

    private void createBoard(){
        int boardSize = 2*housesPerPlayer + 2;
        int halfBoard = boardSize/2;
        boardArray = new SeedContainer[boardSize];
        /* Format will be all player 1 houses, then store, then same for player two*/
        for (int player = 1; player<=2;player++){
            /* Houses*/
            for (int i= 0; i<housesPerPlayer; i++){
                boardArray[i+halfBoard*(player-1)] = new House(player, seedsPerHouse);
            }
            /* Store*/
            boardArray[(halfBoard*player)-1] = new SeedStore(player, initialSeedsInStore);
        }
    }

    public void printBoard(){

    }
}
