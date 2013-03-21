package mancala;

import utility.MockIO;

/**
 * Controls access between the game logic and the individual seed containers
 */
class Board {
    private final int housesPerPlayer;
    private final int seedsPerHouse;
    private final int initialSeedsInStore = 0;
    private int p1StoreIndex;
    private int p2StoreIndex;
    private int p1House1Index;
    private int p2House1Index;
    private SeedContainer boardArray[];
    private int currentContainer;
    private int currentPlayer;
    private final MockIO io;

    /* Constructor*/
    public Board(MockIO io, int housesPerPlayer, int seedsPerHouse) {
        this.io = io;
        this.housesPerPlayer = housesPerPlayer;
        this.seedsPerHouse = seedsPerHouse;
        createBoard();
    }

    public void setPlayer(int player){
        currentPlayer = player;
    }

    /**
     * Takes in input values as understood by a human
     * HouseNumber is the value the user selected at the start of the turn
     * @param player current player
     * @param houseNumber selected by user
     * @return seed number
     */
    public int getSeedsFromHouse(int player, int houseNumber){
        currentContainer = getArrayId(player, houseNumber);
        return boardArray[currentContainer].surrenderAllSeeds();
    }

    /**
     * Sets seedContainer contents to zero and returns the former value
     * Only for private use as it uses array id as input rather than game relevant values
     * @param id
     * @return
     */
    private int getSeeds(int id){
        return boardArray[id].surrenderAllSeeds();
    }

    /**
     * Converts human understandable selection to array id
     * @param player
     * @param houseNumber
     * @return
     */
    private int getArrayId(int player, int houseNumber) {
        if (player == 1){
            return p1House1Index + houseNumber -1; /* -1 to account for different 0/1 counting*/
        } else {
            return p2House1Index + houseNumber -1;
        }
    }

    /**
     * Skips to next accessible container and attempts to add a single seed to it
     * @param seedsRemaining
     * @return
     */
    public GameMove insertSeedIntoNextContainer(int seedsRemaining){
        getNextContainer(); // Circular increment of array
        GameMove result =  boardArray[currentContainer].addSeed(currentPlayer, seedsRemaining);
        /* Case for landing in opponent's store*/
        if (result == null){
            /* Skip ahead one as it did not accept the seed*/
            getNextContainer();
            result =  boardArray[currentContainer].addSeed(currentPlayer, seedsRemaining);
        }
        return result;
    }

    /* Seize the contents of a house and its opposite in a daring but not that impressive raid*/
    public void moveTwoHouseContentsToStore(){

        int playerHouseSeeds = getSeeds(currentContainer);
        int opposingHouse = boardArray.length - currentContainer -2; /* Ugly but works */

        int opponentHouseSeeds = getSeeds(opposingHouse);
        int total = opponentHouseSeeds + playerHouseSeeds;
        /* Put them in the correct store*/
        if (currentPlayer == 1){
            boardArray[p1StoreIndex].insertLiberatedSeeds(total);
        } else {
            boardArray[p2StoreIndex].insertLiberatedSeeds(total);
        }
    }

    /**
     * For checking if a game has ended
     * Will iterate over all of a player's houses and return the total number of seeds in residence
     * @param player
     * @return
     */
    public int countSeedsInPlayerHouses(int player){
        int firstHouse;
        int sum = 0;

        if (player == 1){
            firstHouse = p1House1Index;
        } else {
            firstHouse = p2House1Index;
        }

        for (int i = 0; i < housesPerPlayer; i++){
            sum += boardArray[firstHouse+i].numberOfSeedsInContainer;
        }
        return sum;
    }

    /**
     * Once a game has been finished, this will finish calculating who won
     * @param player
     * @return
     */
    public int countSeedsInPlayerStore(int player) {
        if (player == 1){
            return boardArray[p1StoreIndex].numberOfSeedsInContainer;
        } else {
            return boardArray[p2StoreIndex].numberOfSeedsInContainer;
        }
    }

    /* Allow array to be incremented in a circle*/
    private void getNextContainer(){

        int next = currentContainer + 1;
        if (next >= boardArray.length){
            next = 0;
        }
        currentContainer = next;
    }

    /**
     * Bring the board into the world in all of its glory
     */
    private void createBoard(){
        int boardSize = 2*housesPerPlayer + 2;  /* One store per player + 2 players assumed*/
        int halfBoard = boardSize / 2;
        /* Values to simplify later array access*/
        p1House1Index = 0;
        p2House1Index = halfBoard;
        p1StoreIndex = halfBoard -1;
        p2StoreIndex = 2* halfBoard -1;
        /**/
        boardArray = new SeedContainer[boardSize];
        /* Format will be all player 1 houses, then store, then same for player two*/
        for (int player = 1; player<=2;player++){
            /* Houses*/
            for (int i= 0; i < housesPerPlayer; i++){
                boardArray[i+ halfBoard *(player-1)] = new House(player, seedsPerHouse);
            }
            /* Store*/
            boardArray[(halfBoard *player)-1] = new SeedStore(player, initialSeedsInStore);
        }
    }

    /**
     * For your viewing pleasure
     */
    public void printBoard(){
        String borderCenterSection = "";
        for (int i = 0; i < housesPerPlayer; i++){   /*Avoid needless string operations (somewhat)*/
            borderCenterSection += "-------+";
        }
        String borderRow = String.format("+----+" + borderCenterSection + "----+");
        String centreRow = String.format("|    |" + borderCenterSection.subSequence(0, borderCenterSection.length()-1) + "|    |"); /*Remove final "+" from centre*/

        /* Top row is player2*/
        String p2Row = "| P2 |";
        for (int i = 0; i < housesPerPlayer; i++){
            p2Row += String.format(" %d[%2d] |",housesPerPlayer-i,boardArray[p2StoreIndex - 1 - i].getSeedCount());
        }
        p2Row += String.format(" %2d |", boardArray[p1StoreIndex].getSeedCount());

        /* Bottom row is player1*/
        String p1Row = String.format("| %2d |", boardArray[p2StoreIndex].getSeedCount());
        for (int i = 0; i < housesPerPlayer; i++){
            p1Row += String.format(" %d[%2d] |",i+1, boardArray[p1House1Index + i].getSeedCount());
        }
        p1Row += " P1 |";

        io.println(borderRow);
        io.println(p2Row);
        io.println(centreRow);
        io.println(p1Row);
        io.println(borderRow);

    }
}
