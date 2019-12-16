package main.model;

import java.util.Collections;
import java.util.Stack;
import main.constant.*;

/**
 * This contains the deck class which stores the 52 distinct cards for our HeartsApplication. 
 * @version 5th Apr 2019
 * @author Amos Lam, Chang Ernrae, Lim Zhaowei
 *
 */
public class Deck {
    /* defined as Stack to make it easier to distribute cards in sequence. */
    private Stack<Card> deckCards;
    /**
     * Create a Deck with {@link Card}(s) of different {@link Suit} and {@link Rank}
     */
    public Deck() {
        deckCards = new Stack<Card>();
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                deckCards.push(new Card(suit, rank, true));
            }
        }

        /* randomness of this method is assumed to be tested thoroughly by the creator */
        Collections.shuffle(deckCards);
    }

    /**
     * Gets a stack of 52 cards shuffled randomly
     * @return a Stack of shuffled Cards
     */
    public Stack<Card> getDeckCards() {
        return deckCards;
    }

}