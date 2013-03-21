package mancala;

/**
 * All common functionality that seedContainers must share
 * Created from board class
 */
public abstract class SeedContainer {

    protected int owner;
    protected int numberOfSeedsInContainer;

    protected SeedContainer(int owner, int numberOfSeedsInContainer) {
        this.owner = owner;
        this.numberOfSeedsInContainer = numberOfSeedsInContainer;
    }

    /* Check if current player can add a seed to that entity*/
    protected abstract boolean addSeed(int player, int mobileSeeds);


}
