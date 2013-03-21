package mancala;

/**
 * Created with IntelliJ IDEA.
 * User: michael
 * Date: 21/03/13
 * Time: 11:39 AM
 * To change this template use File | Settings | File Templates.
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

    @Override
    protected int getSeedCount() {
        return numberOfSeedsInContainer;
    }

    @Override
    protected int surrenderAllSeeds() {
        return -1;
    }
}
