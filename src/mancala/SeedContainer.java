package mancala;

/**
 * All common functionality that seedContainers must share
 * Created from inside board class
 */
abstract class SeedContainer {

    final int owner;
    int numberOfSeedsInContainer;
    /* Override in child classes to add extra behaviour*/
    final GameMove regularSeedAdded = GameMove.GO_TO_NEXT;
    GameMove lastSeedAdded = GameMove.GO_TO_NEXT;
    GameMove lastSeedAddedIntoEmpty = GameMove.GO_TO_NEXT;

    SeedContainer(int owner, int numberOfSeedsInContainer) {
        this.owner = owner;
        this.numberOfSeedsInContainer = numberOfSeedsInContainer;
    }

    /* Check if current player can add a seed to that entity*/
    protected abstract GameMove addSeed(int player, int seedsRemaining);

    protected abstract int getSeedCount();

    protected abstract int surrenderAllSeeds();

    protected abstract void insertLiberatedSeeds(int seedNumber);

}
