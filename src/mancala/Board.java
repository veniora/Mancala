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
    private int p1StoreIndex;
    private int p2StoreIndex;
    private int p1House1Index;
    private int p2House1Index;
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
        p1House1Index = 0;
        p2House1Index = halfBoard;
        p1StoreIndex = halfBoard-1;
        p2StoreIndex = 2*halfBoard-1;
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

    /* Current state of board, excludes prompt to user*/
    public void printBoard(){
        String borderCenterSection = "";
        for (int i = 0; i < housesPerPlayer; i++){
            borderCenterSection += "-------+";
        }
        String borderRow = String.format("+----+" + borderCenterSection + "----+");
        String centreRow = String.format("|    |" + borderCenterSection + "    |");

        /* Content rows*/
        /* Top row is player2*/
        String p2Row = "| P2 |";
        for (int i = 0; i < housesPerPlayer; i++){
            p2Row += String.format(" %d[ %d] |",housesPerPlayer-i,boardArray[p2House1Index+i].getSeedCount());
        }
        p2Row += String.format("  %d |", boardArray[p1StoreIndex].getSeedCount());

        /* Bottom row is player1*/
        String p1Row = String.format("|  %d |", boardArray[p2StoreIndex].getSeedCount());
        for (int i = 0; i < housesPerPlayer; i++){
            p1Row += String.format(" %d[ %d] |",i+1,boardArray[p1House1Index+i].getSeedCount());
        }
        p1Row += " P1 |";

        System.out.println(borderRow);
        System.out.println(p2Row);
        System.out.println(centreRow);
        System.out.println(p1Row);
        System.out.println(borderRow);

    }

}
