package mancala;

/**
 * Created with IntelliJ IDEA.
 * User: michael
 * Date: 21/03/13
 * Time: 11:39 AM
 * To change this template use File | Settings | File Templates.
 */
public class House extends SeedContainer{

    protected House(int owner, int numberOfSeedsInContainer) {
        super(owner, numberOfSeedsInContainer);
        lastSeedAdded = GameMove.STEAL_SEEDS;
    }

    @Override
    protected GameMove addSeed(int player, int seedsRemaining) {
        /* House does not care who owns it*/
        numberOfSeedsInContainer++;
        if (seedsRemaining == 1){   /* last seed*/
            return lastSeedAdded;
        }
        return regularSeedAdded;
    }

    @Override
    protected int getSeedCount() {
        return numberOfSeedsInContainer;
    }

    /* When taken from another house*/
    public void insertLiberatedSeeds (int seedNumber){
        numberOfSeedsInContainer += seedNumber;
    }

    /* When an opponent has landed on an empty house that they own with their final seed,
    they can apparently take the contents of the equivalent player house*/
    @Override
    public int surrenderAllSeeds() {
        int seeds = numberOfSeedsInContainer;
        numberOfSeedsInContainer = 0;
        return seeds;
    }

}
