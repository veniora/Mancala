package mancala;

/**
 * A collection of rules that Mancala will understand and act on
 * Will be returned from seedContainer classes to tell the game what to do next
 * To add a new rule,
 *          add value to enum,
 *          override one of the fields in a SeedContainer subtype with the new value
 *          define the rules for invoking the rule inside that class
 *          add value to Mancala main switch statement and define its desired behaviour
 */
public enum GameMove {
    GO_TO_NEXT, STEAL_SEEDS, EXTRA_TURN,
}
