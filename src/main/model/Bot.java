
package main.model;

import java.util.*;

/**
 * This class represents the computer player in the HeartsApplication.
 * @version 5th Apr 2019
 * @author Amos Lam, Chang Ernrae, Lim Zhaowei
 *
 */
public class Bot extends Player {

    /**
    * Returns the top three rank cards from hand for passing
    * @return List of 3 cards 
    * @see Card           
    */
    public List<Card> passThreeCards() {

        /* copy of Bot's hand cards */
        List<Card> passList = new ArrayList<Card>(getHandCards());

        /* sort passList in descending order in terms of rank */
        passList.sort(new Comparator<Card>(){
            @Override
            public int compare(Card card1, Card card2){

                if (card1.getCardValue()%13 > card2.getCardValue()%13) {
                    return -1;
                } else {
                    return 1;
                }
            }
        });

        /* The computer will adopt a simple strategy of picking the highest card of any suit.
           Reduce passList to top 3 cards */
        passList = passList.subList(0, 3);

        return passList;
    }


    /**
    * Generates card used for the trick
    * @param   largestPlayedCard  largest card played in the trick so far
    * @param   numberOfPlayedCards  number of cards that have already been played into the trick by other players
    * @return  Playing card for the trick
    */
    public Card playCard(Card largestPlayedCard, int numberOfPlayedCards) {
        
        /* 
        Project requirements:
         If is first player to start the trick, BOT will pick card of smallest rank that adheres to game rules
         If is not last and not first, BOT will pick largest rank card of the leading suit that is below the largest discarded already by others
         If is last player to start, BOT will pick the largest card (Assume that it is largest ranking card). 
         If dont have the same suit to play, BOT will pick largest rank card of different suit
        */
        List<Card> handCards = getHandCards();

        /* if bot holds onto two of clubs. Throw it */
        for (Card card : handCards) {
            if(card.isTwoOfClubs()) {
                removeHand(card);
                return card;
            }
        }

        /* pick smallest ranking card if BOT starts the trick */
        if(numberOfPlayedCards == 0) {
            Card smallestRankCard = getSmallestRankCard();
            removeHand(smallestRankCard);
            return smallestRankCard;
        }

        /*  If is not last and not first, BOT will pick largest rank card of the leading suit that is below the largest discarded already by others */
        else if(numberOfPlayedCards < 3) {
            /* Can just find 2nd largest in ranking within handCards since pickability is always set b4 
             * playCard method is called */

            int handSize = handCards.size();

            /*find closest larger card on the right and find closest smaller card on the left of leading suit. */
            Card smallerClosestCardOfLargestPlayedCard = null;
            Card LargerClosestCardOfLargestPlayedCard = null;

            for(Card card: handCards) {
                if (card.getPickable() && 
                   (card.getSuit() == largestPlayedCard.getSuit())) {
                        if(LargerClosestCardOfLargestPlayedCard == null && 
                        (card.getCardValue() > largestPlayedCard.getCardValue())) {
                            LargerClosestCardOfLargestPlayedCard = card;
                        }

                        else if(card.getCardValue() > largestPlayedCard.getCardValue() &&
                                card.getCardValue() < LargerClosestCardOfLargestPlayedCard.getCardValue()) {
                                    LargerClosestCardOfLargestPlayedCard = card;
                        }

                        else if(smallerClosestCardOfLargestPlayedCard == null &&
                        (card.getCardValue() < largestPlayedCard.getCardValue())) {
                            smallerClosestCardOfLargestPlayedCard = card;
                        }

                        else if(card.getCardValue() < largestPlayedCard.getCardValue() && 
                                card.getCardValue() > smallerClosestCardOfLargestPlayedCard.getCardValue()) {
                                    smallerClosestCardOfLargestPlayedCard = card;
                                }
                            
                    }
                    
            }

            /* if handCards have a card of same leading suit but second largest compared to the largest card played.
            return that card */
            if(smallerClosestCardOfLargestPlayedCard != null) {
                removeHand(smallerClosestCardOfLargestPlayedCard);
                return smallerClosestCardOfLargestPlayedCard;
            }

            /* if handCards have a card of same leading suit but only larger than the largest card played. 
            Bot has no choice but to return that card */
            else if(LargerClosestCardOfLargestPlayedCard != null) {
                removeHand(LargerClosestCardOfLargestPlayedCard);
                return LargerClosestCardOfLargestPlayedCard;
            }
                
        
        }

        /* if handCards have no same suit as the largestCardPlayed, return the highest ranking card. 
        This rule applies for both 2nd/3rd/last player */
        Card largestRankCard = getLargestRankCard();
        removeHand(largestRankCard);
        return largestRankCard;
    }

}