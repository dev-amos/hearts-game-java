package main.model;
import main.constant.*;


/**
 * This Class represents the card in the Hearts Game.
 * Uses rank and suit enum objects to represent value of the card
 * @version 5th Apr 2019
 * @author Amos Lam, Chang Ernrae, Lim Zhaowei
 */
public class Card {
    private Suit suit;
    private Rank rank;

    /* Identify the card owner so that via the card, owner can be found */
    private String ownerOfCard;

    /* This is for showing Card's availability for getting picked */
    private boolean pickable;

    /**
     * Create a Card with a specific enum suit, rank and truthy status of the card's playability in a trick
     * @param  suit  A suit enum object either Clubs, Diamonds, Hearts or Spades 
     * @param  rank  A rank enum object either TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE
     * @param  pickable  true if card can be picked to play by player, false otherwise
     */
    public Card(Suit suit, Rank rank, boolean pickable) {
        this.suit = suit;
        this.rank = rank;
        this.pickable = pickable;
    }


    /** Uses the enum {@link Rank} and {@link Suit} index position to retrieve value
     * @return ordinal value of the card
     */
    public int getCardValue() {
        return this.suit.ordinal() * 13 + this.rank.ordinal();
    }

    /**
    * Gets the enum rank object stored in the card.
    * @return The enum rank
    */
    public Rank getRank() {
        return rank;
    }
    
    /**
    * Sets enum rank object in Card
    * @param rank  The enum {@link Rank} object to represent the card's rank
    */
    public void setRank(Rank rank) {
        this.rank = rank;
    }

    /**
    * Gets the enum suit object stored in the card
    * @return The enum suit
    */
    public Suit getSuit() {
        return suit;
    }

    /**
    * Sets enum suit object in Card
    * @param suit  The {@link Suit} object to represent the card's suit
    */
    public void setSuit(Suit suit) {
        this.suit = suit;
    }

    /**
    * Gets the boolean value of the card's pickability for a trick
    * @return true if the card can be played for a trick, false if otherwise
    */
    public boolean getPickable() {
        return this.pickable;
    }

    /**
    * Sets the boolean value on a card's pickability for a trick
    * @param pickable  true if set card playable for a trick, false for not playable
    */
    public void setPickable(boolean pickable) {
        this.pickable = pickable;
    }

    /**
    * Verify if card is two of clubs.
    * @return true if card is two of clubs, false otherwise
    * @see Suit
    * @see Rank
    */
    public boolean isTwoOfClubs() {
        if (suit == Suit.CLUBS && rank == Rank.TWO) {
            return true;
        } else {
            return false;
        }
    }

    /**
    * Verify if card is of suit HEARTS
    * @return true if card is of suit HEARTS, false otherwise
    */
    public boolean isHearts() {
        if (suit == Suit.HEARTS) {
            return true;
        } else {
            return false;
        }
    }

    /**
    * Verify if card is Queen of Spades
    * @return true if card is Queen of Spades, false otherwise
    * @see Suit
    * @see Rank
    */
    public boolean isQueenOfSpades() {
        if (suit == Suit.SPADES && rank == Rank.QUEEN) {
            return true;
        } else {
            return false;
        }
    }

    /**
    * Retrieves the id of the owner of card (owner = player)
    * @return The {@link Player} id 
    */
    public String getOwnerOfCard() {
        return ownerOfCard;
    }
    
    /**
    * Sets the card's owner to the owner's id (owner = player)
    * @param owner  {@link Player} id
    */
    public void setOwnerOfCard(String owner) {
        ownerOfCard = owner;
    }
    
    /**
    * Gives a String representation of the Card object in the format {RANK} of {SUITS}[{pickable} by {ownerOfCard}]
    * @return a string representation of the Card object
    */
    @Override
    public String toString() {
        String playable = "not pickable";
        if(pickable) {
            playable = "pickable";
        }
        return rank + " of " + suit + "[" + playable + " by " + ownerOfCard + "]";
    }
}