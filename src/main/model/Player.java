package main.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import main.constant.*;

/**
 * This class models a player in the HeartsApplication
 * @version 5th Apr 2019
 * @author Amos Lam, Chang Ernrae, Lim Zhaowei
 */
public class Player {
    private List<Card> handCards = new ArrayList<Card>();
    
    /* an id is needed for dealer to assign turns and allocate points*/
    private String id;

    /**
    * Gets player's id 
    * @return player id
    */
    public String getId(){
        return this.id;
    }

    /**
    * Sets player's id 
    * @param id  id like "User", "BotA", "BotB" or "BotC"
    */
    public void setId(String id){
        this.id = id;
    }

    /**
    * Renew the player's hand cards by removing existing cards and add new cards
    * @param cards  List of {@link Card}(s) to fill the player's hands
    */
    public void fillHandCards(List<Card> cards) {

        /* remove all cards on hand. This method is only called when user start a new round */
        this.handCards.clear();

        for(Card card: cards) {
            card.setOwnerOfCard(getId());
        }

        this.handCards.addAll(cards);
    }

    /**
    * Add cards to player's hands.
    * @param cards  List of {@link Card}(s) to add onto player's hands
    */
    public void addCardsToHand(List<Card> cards) {
        for(Card card: cards) {
            card.setOwnerOfCard(getId());
        }
        this.handCards.addAll(cards);
    }
    
    /**
    * Gets player hand cards in sorted format
    * @return  A sorted List of {@link Card}(s)       
    */
    public List<Card> getHandCards() {

        handCards.sort(new Comparator<Card>() {
            @Override
            public int compare(Card card1, Card card2) {
                /*Comparison is over ordinal val of the enum suit + rank */
                if (card1.getCardValue() > card2.getCardValue()) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });

        return handCards;
    }

    /**
    * Removes card from player hand cards
    * @param card  Specific {@link Card} object
    * @return true if removal of card is successful, false if otherwise
    */
    public boolean removeHand(Card card) {

        for (int i = 0; i < handCards.size(); i++) {
            if (handCards.get(i).getCardValue() == card.getCardValue()) {
                handCards.remove(i);
                return true;
            }
        }     
        return false;
    }

    /**
    * Removes specified list of Card from player hand cards
    * @param   cards  A list of {@link Card}(s) 
    * @return  true if removal of cards is successful, false if otherwise
    */
    public boolean removeHand(List<Card> cards) {
        for(Card cardToRemove: cards) {
            if(!removeHand(cardToRemove)) {
                return false;
            }
        }
        return true;
   
    }

    /**
    * Checks if player holds onto two of clubs
    * @return  true if player has two of clubs, false if otherwise
    * @see Card           
    */
    public boolean hasTwoOfClubs() {
        for (Card card : handCards) {
            if (card.isTwoOfClubs()) {
                return true;
            }
        }
        return false;
    }
    
    /**
    * Update player hand cards pickability for a trick
    * @param playedCards  List of {@link Card}(s) already played in the trick
    * @param isNotFirstTrick  if is first trick or not
    * @param isHeartsBroken  if hearts has been broken or not
    */
    public void updateCardPickability(List<Card> playedCards,
    boolean isNotFirstTrick ,boolean isHeartsBroken) {
        /* pickableCount is used to track if player got any cards to play. If no cards, means set all cards pickable */
        int pickableCount = 0;

        /* set all cards' pickable false. Then decide which cards are pickable. */
        for (Card card : handCards) {
            card.setPickable(false);
        }

        /* if pass. Just need to set twoOfClubs as the only card playable */
        if(!isNotFirstTrick) {
            for (Card card : handCards) {
                if(card.isTwoOfClubs()) {
                    card.setPickable(true);
                    return;
                }
            }
        }

        /* check if player is first to start the trick or not.
        If yes. Means player will decide on leading suit */
        if(playedCards.size() == 0) {
            /* if leading the trick and hearts not broken, set all nonpoint cards as pickable */
            if(!isHeartsBroken) {
                for (Card card : handCards) {
                    if(!card.isHearts() && !card.isQueenOfSpades()) {
                        pickableCount++;
                        card.setPickable(true);
                    }
                }
            }
            
        }
        else {
            /* since got cards played already, follow leading suit */

            Card leadCard = playedCards.get(0);

            for(Card card: handCards) {
                if (card.getSuit() == leadCard.getSuit()) {
                    pickableCount++;
                    card.setPickable(true);
                }
            }
        }

        /* if pickable count = 0, the possibilities are:
        1)player starting trick(not first trick) and hearts broken . OR
        2)player starting trick(not first trick) and hearts not broken and player has all point cards 
        3)2nd/3rd/4th player has no cards of leading suit
        4)2nd/3rd/4th player has no cards of leading suit and has all point cards and hearts not broken
        */
        if(pickableCount == 0) {
            /* Handles no.2 and no.4 possibility */
            if(!isHeartsBroken && checkAllPointCards(handCards)) {
                /* turn all point cards pickable. Player can throw point cards even if is first trick. */
                for (Card card : handCards) {
                    card.setPickable(true);
                }
                return;
            }

            /* Handles no.1 and no.3 possibility */
            for(Card card:handCards) {
                card.setPickable(true);
                
                /* handles possibility 2 and it is first trick  + handles possibility 1*/
                if(!isNotFirstTrick && (card.isHearts() || card.isQueenOfSpades())) {
                    card.setPickable(false);
                }
            }
        }
    }

    /**
    * Gets lowest rank card in player hands
    * @return Card
    */
    public Card getSmallestRankCard() {

        List<Card> cards = getHandCards();

        Card smallest = null;

        for(Card card: cards) {
            if(card.getPickable()) {
                if(smallest == null){
                    smallest = card;
                }
                else{
                    if(smallest.getCardValue()%13 > card.getCardValue()%13) {
                        smallest = card;                     
                    }
                }
            }
        }

        return smallest;
    }

    /**
    * Gets largest rank card in player hands
    * @return Card
    */
    public Card getLargestRankCard() {

        List<Card> cards = getHandCards();

        Card largest = null;

        for(Card card: cards) {
            if(card.getPickable()) {
                if(largest == null){
                    largest = card;
                }
                else{
                    if(largest.getCardValue()%13 < card.getCardValue()%13) {
                        largest = card;                     
                    }
                }
            }
        }

        return largest;
    }

    /**
    * Removes user chosen played card from his hand of cards
    * @param  chosenCard  played card for the trick
    * @see  Card           
    */
    public void playCard(Card chosenCard) {
        removeHand(chosenCard);
    }

    /**
    * Check if list of cards are all point cards
    * @param cards  List of {@link Card} object
    * @return true if all the cards are point cards, false if otherwise
    */
    public boolean checkAllPointCards(List<Card> cards) {
        for(Card card : cards) {
            /* if card is neither hearts suit or queen of spades, return false */
            if (card.getSuit() != Suit.HEARTS && !(card.getSuit() == Suit.SPADES && card.getRank() == Rank.QUEEN) ) {
                return false;
            }
        }
        return true;
    }
}