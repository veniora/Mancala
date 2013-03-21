package mancala;

/**
 * Created with IntelliJ IDEA.
 * User: michael
 * Date: 21/03/13
 * Time: 11:36 AM
 * To change this template use File | Settings | File Templates.
 */
public class Board {
    private int housesPerPlayer;
    private int seedsPerHouse;
    private int initialSeedsInStore = 0;
    private int p1StoreIndex;
    private int p2StoreIndex;
    private int p1House1Index;
    private int p2House1Index;
    private SeedContainer boardArray[];
    private int currentContainer;
    private int currentPlayer;
    private int halfBoard;


    public Board(int housesPerPlayer, int seedsPerHouse) {
        this.housesPerPlayer = housesPerPlayer;
        this.seedsPerHouse = seedsPerHouse;
        createBoard();
    }

    public void setPlayer(int player){
        currentPlayer = player;
    }


    public int getSeedsFromHouse(int player, int houseNumber){
        int houseId = getArrayId(player, houseNumber);
        int seeds = 0;
        switch (currentPlayer){
            case 1:
                currentContainer = houseId;
                seeds = boardArray[currentContainer].surrenderAllSeeds();
                break;
            case 2:
                currentContainer = houseId;
                seeds = boardArray[currentContainer].surrenderAllSeeds();
                break;
        }
        return seeds;
    }

    public int getSeeds(int id){
        return boardArray[id].surrenderAllSeeds();
    }

    private int getArrayId(int player, int houseNumber) {
        if (player == 1){
            return p1House1Index + houseNumber -1; /* -1 to account for different 0/1 counting*/
        } else {
            return p2House1Index + houseNumber -1;
        }
    }

    public GameMove insertSeedIntoNextContainer(int seedsRemaining){
        getNextContainer();
        GameMove result =  boardArray[currentContainer].addSeed(currentPlayer, seedsRemaining);
        if (result == null){
            /* Skip ahead one as it did not accept the seed*/
            getNextContainer();
            result =  boardArray[currentContainer].addSeed(currentPlayer, seedsRemaining);
        }
        return result;
    }

    public void emptyHousesIntoStore(){
//        getNextContainer();
        int playerHouseSeeds = getSeeds(currentContainer);
        int opposingHouse = boardArray.length - currentContainer -2;

        int opponentHouseSeeds = getSeeds(opposingHouse);
        int total = opponentHouseSeeds + playerHouseSeeds;
        if (currentPlayer == 1){
            boardArray[p1StoreIndex].insertLiberatedSeeds(total);
        } else {
            boardArray[p2StoreIndex].insertLiberatedSeeds(total);
        }
    }

    public int countSeedsInPlayerHouses(int player){
        int firstHouse;
        if (player == 1){
            firstHouse = p1House1Index;
        } else {
            firstHouse = p2House1Index;
        }
        int sum = 0;
        for (int i = 0; i < housesPerPlayer; i++){
            sum += boardArray[firstHouse+i].numberOfSeedsInContainer;
        }
        return sum;
    }

    public int countSeedsInPlayerStore(int player) {
        if (player == 1){
            return boardArray[p1StoreIndex].numberOfSeedsInContainer;
        } else {
            return boardArray[p2StoreIndex].numberOfSeedsInContainer;
        }
    }

    private void getNextContainer(){

        int next = currentContainer + 1;
        if (next >= boardArray.length){
            next = 0;
        }
        currentContainer = next;
        System.out.println("CurrentIndex: "+currentContainer);
    }
    private void createBoard(){
        int boardSize = 2*housesPerPlayer + 2;
        halfBoard = boardSize/2;
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
            p2Row += String.format(" %d[%2s] |",housesPerPlayer-i,formatNum(boardArray[p2StoreIndex - 1 - i].getSeedCount()));
        }
        p2Row += String.format(" %2s |", formatNum(boardArray[p1StoreIndex].getSeedCount()));

        /* Bottom row is player1*/
        String p1Row = String.format("| %2s |", formatNum(boardArray[p2StoreIndex].getSeedCount()));
        for (int i = 0; i < housesPerPlayer; i++){
            p1Row += String.format(" %d[%2s] |",i+1, formatNum(boardArray[p1House1Index + i].getSeedCount()));
        }
        p1Row += " P1 |";

        System.out.println(borderRow);
        System.out.println(p2Row);
        System.out.println(centreRow);
        System.out.println(p1Row);
        System.out.println(borderRow);

    }

    private String formatNum(int num){

        return ""+num;
    }

}
