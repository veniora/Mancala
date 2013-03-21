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
    }

    @Override
    protected boolean addSeed(int player, int mobileSeeds) {
        /* Player can only move through their own store*/
        if (player == owner){
            numberOfSeedsInContainer++;
            return true;
        }
        /* Move failed*/
        return false;
    }
}
