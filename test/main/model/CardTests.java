package main.model;


import main.constant.*;
// import static org.junit.jupiter.api.Assertions.*;
// import org.junit.jupiter.api.*;

import static org.junit.Assert.*;
import org.junit.jupiter.api.*;



public class CardTests{

    /*Test for card value based on suit and rank */
    @Test
    public void cardValueTest() {
        Suit suit1 = Suit.SPADES;
        Rank rank1 = Rank.TWO;

        Card card1 = new Card(suit1, rank1, false);
        int cardValue1 = card1.getCardValue();

        Suit suit2 = Suit.HEARTS;
        Rank rank2 = Rank.ACE;

        Card card2 = new Card(suit2, rank2, false);
        int cardValue2 = card2.getCardValue();


        Suit suit3 = Suit.CLUBS;
        Rank rank3 = Rank.TWO;

        Card card3 = new Card(suit3, rank3, false);
        int cardValue3 = card3.getCardValue();
        
        assertEquals(26, cardValue1);
        assertEquals(51, cardValue2);
        assertEquals(0, cardValue3);
    }


    /*Tests card' method in verifying if it is two of clubs 
     *Check suit same, rank different
     *check suit different, rank same
      check suit same, rank same. */
    
    @Test
    public void cardIsTwoOfClubsTest() {
        
        Suit suit1 = Suit.SPADES;
        Suit suit2 = Suit.CLUBS;
        
        Rank rank1  = Rank.TWO;
        Rank rank2 = Rank.THREE;

        Card cardDifferentRankAndSuit = new Card(suit1,rank2,false);
        Card cardDifferentRankAndSameSuit = new Card(suit2,rank2,false);
        Card cardSameRankAndSuit = new Card(suit2,rank1,false);
        Card cardSameRankDifferentSuit = new Card(suit1,rank1,false);

        assertFalse("Different rank and suit from two of clubs will fail",cardDifferentRankAndSuit.isTwoOfClubs());
        assertFalse("Different rank from two of clubs will fail",cardDifferentRankAndSameSuit.isTwoOfClubs());
        assertTrue("Same rank and suit as two of clubs will pass",cardSameRankAndSuit.isTwoOfClubs());
        assertFalse("Same rank and different suit from two of clubs will fail",cardSameRankDifferentSuit.isTwoOfClubs());
    }


    /*Tests card' method in verifying if it is HEARTS suit
     *Check suit different
      Check suit same */
    
    @Test
    public void cardIsHearts() {

        Suit suit1 = Suit.HEARTS;
        Suit suit2 = Suit.CLUBS;

        /* any rank will do */
        Rank rank1 = Rank.ACE;

        Card cardDifferentSuit = new Card(suit2,rank1,false);
        Card cardSameSuit = new Card(suit1,rank1,false);

        
        
        assertTrue("Same suit as HEARTS will pass",cardSameSuit.isHearts());
        assertFalse("Different suit from HEARTS will not pass",cardDifferentSuit.isHearts());
          
    }


    /*Tests card' method in verifying if it is Queen of Spades 
     *Check different suit , same rank
      Check same suit, different rank
      Check same suit, same rank */
    @Test
    public void cardIsQueenOfSpades() {

        Suit suit1 = Suit.SPADES;
        Suit suit2 = Suit.CLUBS;
        
        Rank rank1  = Rank.QUEEN;
        Rank rank2 = Rank.TWO;

        Card cardDifferentRankAndSuit = new Card(suit2,rank2,false);
        Card cardDifferentRankAndSameSuit = new Card(suit1,rank2,false);
        Card cardSameRankAndSuit = new Card(suit1,rank1,false);
        Card cardSameRankDifferentSuit = new Card(suit2,rank1,false);

        assertFalse("Different rank and suit from Queen of Spades will fail",cardDifferentRankAndSuit.isQueenOfSpades());
        assertFalse("Different rank from Queen of Spades will fail",cardDifferentRankAndSameSuit.isQueenOfSpades());
        assertTrue("Same rank and suit as Queen of Spades will pass",cardSameRankAndSuit.isQueenOfSpades());
        assertFalse("Same rank but different suit from Queen of Spades will fail",cardDifferentRankAndSameSuit.isQueenOfSpades());
        
    }

}