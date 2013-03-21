package mancala;

/**
 * These will be acted upon by the Mancala class
 * Will be returned from seedContainer classes to tell the game what to do next
 */
public enum GameMove {
    GO_TO_NEXT, STEAL_SEEDS, EXTRA_TURN,
}
