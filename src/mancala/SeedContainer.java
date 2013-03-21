package mancala;

/**
 * All common functionality that seedContainers must share
 * Created from board class
 */
public abstract class SeedContainer {

    protected int owner;
    protected int numberOfSeedsInContainer;
    protected GameMove regularSeedAdded = GameMove.GO_TO_NEXT;
    protected GameMove lastSeedAdded = GameMove.GO_TO_NEXT;

    protected SeedContainer(int owner, int numberOfSeedsInContainer) {
        this.owner = owner;
        this.numberOfSeedsInContainer = numberOfSeedsInContainer;
    }

    /* Check if current player can add a seed to that entity*/
    protected abstract GameMove addSeed(int player, int seedsRemaining);

    protected abstract int getSeedCount();

    protected abstract int surrenderAllSeeds();

}
