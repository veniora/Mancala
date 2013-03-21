package mancala;

/**
 * A simple seed store which can only be accessed by the player who owns it
 */
public class SeedStore extends SeedContainer {
    protected SeedStore(int owner, int numberOfSeedsInContainer) {
        super(owner, numberOfSeedsInContainer);
        lastSeedAdded = GameMove.EXTRA_TURN;
    }

    @Override
    protected GameMove addSeed(int player, int seedsRemaining) {
        /* Player can only move through their own store*/
        if (player == owner){
            numberOfSeedsInContainer++;
            if (seedsRemaining == 1){   /* last seed*/
                return lastSeedAdded;
            }
            return regularSeedAdded;
        }
        /* Move failed*/
        return null;
    }

    /* When taken from another house*/
    public void insertLiberatedSeeds (int seedNumber){
        numberOfSeedsInContainer += seedNumber;
    }

    @Override
    protected int getSeedCount() {
        return numberOfSeedsInContainer;
    }

    @Override
    protected int surrenderAllSeeds() {
        return -1;
    }
}
