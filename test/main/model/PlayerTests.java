package main.model;


import main.constant.*;
import main.model.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;


class PlayerTests {

    /*Check for 13 cards in a hand after distribution */
    @Test
    void checkHandHas13Cards(){
        Dealer testDealer = new Dealer();
        testDealer.initDealer();
        testDealer.newRound();

        assertEquals(13, testDealer.getUserHandCards().size());
        assertEquals(13, testDealer.getBotA().getHandCards().size());
        assertEquals(13, testDealer.getBotB().getHandCards().size());
        assertEquals(13, testDealer.getBotC().getHandCards().size());
    }


    /*Check for 12 cards in a hand after playing a card */
    @Test
    void checkHandAfterRemovingACard(){
        Player testPlayer = new Player();
        Dealer testDealer = new Dealer();
        testDealer.initDealer();
        testDealer.newRound();
        
        List<Card> testHand = testDealer.getUserHandCards();
        testPlayer.addCardsToHand(testHand);

        List<Card> handCards = testPlayer.getHandCards();
        Card removeCard = testHand.get(0);
        testPlayer.removeHand(removeCard);


        System.out.println(testPlayer.getHandCards().contains(removeCard));
        assertEquals(12, testPlayer.getHandCards().size()); //check for 12 cards
        assertFalse(testPlayer.getHandCards().contains(removeCard)); //check removed cards is not present in hand
    }

    /*Check for 10 cards in a hand after passing 3 cards and yet to receive. Test for constructor method for removeHand*/
    @Test
    void checkHandAfterRemovingACardForPassing(){
        Player testPlayer = new Player();
        Dealer testDealer = new Dealer();
        ArrayList<Card> testCards = new ArrayList<Card>();
        ArrayList<Card> removedCards = new ArrayList<Card>();
        
        Card card1 = new Card(Suit.SPADES,Rank.KING,true);
        Card card2 = new Card(Suit.CLUBS,Rank.JACK,true);
        Card card3 = new Card(Suit.DIAMONDS,Rank.TWO,true);
        Card card4 = new Card(Suit.CLUBS,Rank.THREE,true);
        Card card5 = new Card(Suit.HEARTS,Rank.TWO,true);
        Card card6 = new Card(Suit.CLUBS,Rank.FIVE,true);
        Card card7 = new Card(Suit.SPADES,Rank.FIVE,true);
        Card card8 = new Card(Suit.CLUBS,Rank.ACE,true);
        Card card9 = new Card(Suit.DIAMONDS,Rank.ACE,true);
        Card card10 = new Card(Suit.HEARTS,Rank.THREE,true);
        Card card11 = new Card(Suit.HEARTS,Rank.JACK,true);
        Card card12 = new Card(Suit.CLUBS,Rank.TWO,true);
        Card card13 = new Card(Suit.SPADES,Rank.QUEEN,true);

        testCards.add(card1);
        testCards.add(card2);
        testCards.add(card3);
        testCards.add(card4);
        testCards.add(card5);
        testCards.add(card6);
        testCards.add(card7);
        testCards.add(card8);
        testCards.add(card9);
        testCards.add(card10);
        testCards.add(card11);
        testCards.add(card12);
        testCards.add(card13);

        removedCards.add(card1);
        removedCards.add(card2);
        removedCards.add(card3);

        testPlayer.fillHandCards(testCards);
        testPlayer.removeHand(removedCards);


        assertEquals(10, testPlayer.getHandCards().size()); //check for 10 cards
        assertFalse(testPlayer.getHandCards().contains(removedCards.get(0))); //check removed cards is not present in hand
        assertFalse(testPlayer.getHandCards().contains(removedCards.get(1))); //check removed cards is not present in hand
        assertFalse(testPlayer.getHandCards().contains(removedCards.get(2))); //check removed cards is not present in hand
    }

    @Test
    void testfor2Clubs(){
        Player testPlayer1 = new Player();
        Player testPlayer2 = new Player();

        ArrayList<Card> clubHand, spadeHand;

        clubHand = new ArrayList<Card>();
        spadeHand = new ArrayList<Card>();
        
        for (Rank rank : Rank.values()) {
            clubHand.add(new Card(Suit.CLUBS, rank, true));
        }

        for (Rank rank : Rank.values()) {
            spadeHand.add(new Card(Suit.SPADES, rank, true));
        }

        testPlayer1.fillHandCards(clubHand);
        testPlayer2.fillHandCards(spadeHand);


        assertTrue(testPlayer1.hasTwoOfClubs()); //return True for 2 of clubs present
        assertFalse(testPlayer2.hasTwoOfClubs()); //return False for no 2 of clubs present
    }

    /*Test cards pickaility for first trick when player has 2 clubs */
    @Test
    void checkPickability_FirstRound(){
        Player testPlayer = new Player();
        ArrayList<Card> testHand = new ArrayList <Card>();
        ArrayList<Card> playedCards = new ArrayList <Card>();
       
        Card card1 = new Card(Suit.SPADES,Rank.QUEEN,true);
        Card card2 = new Card(Suit.CLUBS,Rank.NINE,true);
        Card card3 = new Card(Suit.DIAMONDS,Rank.TWO,true);
        Card card4 = new Card(Suit.HEARTS,Rank.THREE,true);
        Card card5 = new Card(Suit.HEARTS,Rank.JACK,true);
        Card card6 = new Card(Suit.CLUBS,Rank.TWO,true);

        testHand.add(card1);
        testHand.add(card2);
        testHand.add(card3);
        testHand.add(card4);
        testHand.add(card5);
        testHand.add(card6);

            
        testPlayer.fillHandCards(testHand);
        testPlayer.updateCardPickability(playedCards, false, false);
        assertEquals(false, testHand.get(0).getPickable());
        assertEquals(false, testHand.get(1).getPickable());
        assertEquals(false, testHand.get(2).getPickable());
        assertEquals(false, testHand.get(3).getPickable());
        assertEquals(false, testHand.get(4).getPickable());
        assertEquals(true, testHand.get(5).getPickable());
    }
    /*Test cards pickaility for following a suit and hearts NOT broken and First Trick */
    /**Queen Spades should be not pickable as well */ 
    @Test
    void checkPickability_FollowingSuit_heartsNotBroken(){
        Player testPlayer = new Player();
        ArrayList<Card> testHand = new ArrayList <Card>();
        ArrayList<Card> playedCards = new ArrayList <Card>();
       
        Card card1 = new Card(Suit.SPADES,Rank.QUEEN,true);
        Card card2 = new Card(Suit.CLUBS,Rank.NINE,true);
        Card card3 = new Card(Suit.DIAMONDS,Rank.TWO,true);
        Card card4 = new Card(Suit.HEARTS,Rank.THREE,true);
        Card card5 = new Card(Suit.HEARTS,Rank.JACK,true);
        Card card6 = new Card(Suit.DIAMONDS,Rank.TWO,true);

        Card aPlayedCard = new Card(Suit.DIAMONDS, Rank.ACE, true);

        testHand.add(card1);
        testHand.add(card2);
        testHand.add(card3);
        testHand.add(card4);
        testHand.add(card5);
        testHand.add(card6);

        playedCards.add(aPlayedCard);
            
        testPlayer.fillHandCards(testHand);
        testPlayer.updateCardPickability(playedCards, true, false);
        assertEquals(false, testHand.get(0).getPickable());
        assertEquals(false, testHand.get(1).getPickable());
        assertEquals(true, testHand.get(2).getPickable());
        assertEquals(false, testHand.get(3).getPickable());
        assertEquals(false, testHand.get(4).getPickable());
        assertEquals(true, testHand.get(5).getPickable());

    }

    //No suit and no broken hearts------------------------------------------------------

    /*Test cards pickaility for NOT following a suit and hearts broken */
    /**FOLLOWS with no leading suit, hearts broken */
    /**Queen Spades should be not pickable as well */
    @Test
    void checkPickability_NoLeadingSuits_heartsBroken(){
        Player testPlayer = new Player();
        ArrayList<Card> testHand = new ArrayList <Card>();
        ArrayList<Card> playedCards = new ArrayList <Card>();
       
        Card card1 = new Card(Suit.SPADES,Rank.QUEEN,true);
        Card card2 = new Card(Suit.CLUBS,Rank.NINE,true);
        Card card3 = new Card(Suit.SPADES,Rank.TWO,true);
        Card card4 = new Card(Suit.HEARTS,Rank.THREE,true);
        Card card5 = new Card(Suit.HEARTS,Rank.JACK,true);
        Card card6 = new Card(Suit.CLUBS,Rank.TWO,true);

        Card aPlayedCard = new Card(Suit.DIAMONDS, Rank.ACE, true);

        testHand.add(card1);
        testHand.add(card2);
        testHand.add(card3);
        testHand.add(card4);
        testHand.add(card5);
        testHand.add(card6);

        playedCards.add(aPlayedCard);
            
        testPlayer.fillHandCards(testHand);
        testPlayer.updateCardPickability(playedCards, true, true);
        assertEquals(true, testHand.get(0).getPickable());
        assertEquals(true, testHand.get(1).getPickable());
        assertEquals(true, testHand.get(2).getPickable());
        assertEquals(true, testHand.get(3).getPickable());
        assertEquals(true, testHand.get(4).getPickable());
        assertEquals(true, testHand.get(5).getPickable());

    }

    /*Test cards pickaility for NOT following a suit and hearts not broken*/
    /**Queen Spades should be not pickable as well */

    @Test
    void checkPickability_NoLeadingSuits_heartsNotBroken(){
        Player testPlayer = new Player();
        ArrayList<Card> testHand = new ArrayList <Card>();
        ArrayList<Card> playedCards = new ArrayList <Card>();
       
        Card card1 = new Card(Suit.SPADES,Rank.QUEEN,true);
        Card card2 = new Card(Suit.CLUBS,Rank.NINE,true);
        Card card3 = new Card(Suit.SPADES,Rank.TWO,true);
        Card card4 = new Card(Suit.HEARTS,Rank.THREE,true);
        Card card5 = new Card(Suit.HEARTS,Rank.JACK,true);
        Card card6 = new Card(Suit.CLUBS,Rank.TWO,true);

        Card aPlayedCard = new Card(Suit.DIAMONDS, Rank.ACE, true);

        testHand.add(card1);
        testHand.add(card2);
        testHand.add(card3);
        testHand.add(card4);
        testHand.add(card5);
        testHand.add(card6);

        playedCards.add(aPlayedCard);
            
        testPlayer.fillHandCards(testHand);
        testPlayer.updateCardPickability(playedCards, true, false);
        assertEquals(true, testHand.get(0).getPickable());
        assertEquals(true, testHand.get(1).getPickable());
        assertEquals(true, testHand.get(2).getPickable());
        assertEquals(true, testHand.get(3).getPickable());
        assertEquals(true, testHand.get(4).getPickable());
        assertEquals(true, testHand.get(5).getPickable());

    }
    

    /*Test cards pickaility when player start trick with hearts BROKEN */
    @Test
    void checkPickability_StartTrickWithHeartsBroken(){
        Player testPlayer = new Player();
        ArrayList<Card> testHand = new ArrayList <Card>();
        ArrayList<Card> playedCards = new ArrayList <Card>();
       
        Card card1 = new Card(Suit.SPADES,Rank.QUEEN,true);
        Card card2 = new Card(Suit.DIAMONDS,Rank.NINE,true);
        Card card3 = new Card(Suit.DIAMONDS,Rank.TWO,true);
        Card card4 = new Card(Suit.HEARTS,Rank.THREE,true);
        Card card5 = new Card(Suit.HEARTS,Rank.JACK,true);

        testHand.add(card1);
        testHand.add(card2);
        testHand.add(card3);
        testHand.add(card4);
        testHand.add(card5);

            
        testPlayer.fillHandCards(testHand);
        testPlayer.updateCardPickability(playedCards, true, true);
        assertEquals(true, testHand.get(0).getPickable());
        assertEquals(true, testHand.get(1).getPickable());
        assertEquals(true, testHand.get(2).getPickable());
        assertEquals(true, testHand.get(3).getPickable());
        assertEquals(true, testHand.get(4).getPickable());


    }

    /*Test cards pickaility when player start trick with hearts NOT BROKEN */
    /**FIRST TRICK  */
    @Test
    void checkPickability_StartTrickWithHeartsNotBroken(){
        Player testPlayer = new Player();
        ArrayList<Card> testHand = new ArrayList <Card>();
        ArrayList<Card> playedCards = new ArrayList <Card>();
       
        Card card1 = new Card(Suit.SPADES,Rank.QUEEN,true);
        Card card2 = new Card(Suit.DIAMONDS,Rank.NINE,true);
        Card card3 = new Card(Suit.DIAMONDS,Rank.TWO,true);
        Card card4 = new Card(Suit.HEARTS,Rank.THREE,true);
        Card card5 = new Card(Suit.HEARTS,Rank.JACK,true);

        testHand.add(card1);
        testHand.add(card2);
        testHand.add(card3);
        testHand.add(card4);
        testHand.add(card5);

        testPlayer.fillHandCards(testHand);
        testPlayer.updateCardPickability(playedCards, false, true);
        assertEquals(false, testHand.get(0).getPickable());
        assertEquals(true, testHand.get(1).getPickable());
        assertEquals(true, testHand.get(2).getPickable());
        assertEquals(false, testHand.get(3).getPickable());
        assertEquals(false, testHand.get(4).getPickable());
  
    }

    /*Test cards pickaility when player start trick with hearts NOT BROKEN */
    /**FIRST TRICK and players other than first player has no leading CLUBS */
    @Test
    void checkPickability_FirstTrickNoLeadingSuitWithHeartsNotBroken(){
        Player testPlayer = new Player();
        ArrayList<Card> testHand = new ArrayList <Card>();
        ArrayList<Card> playedCards = new ArrayList <Card>();
       
        Card card1 = new Card(Suit.SPADES,Rank.QUEEN,true);
        Card card2 = new Card(Suit.DIAMONDS,Rank.NINE,true);
        Card card3 = new Card(Suit.DIAMONDS,Rank.TWO,true);
        Card card4 = new Card(Suit.HEARTS,Rank.THREE,true);
        Card card5 = new Card(Suit.HEARTS,Rank.JACK,true);
        Card aPlayedCard = new Card(Suit.CLUBS,Rank.TWO,true);

        testHand.add(card1);
        testHand.add(card2);
        testHand.add(card3);
        testHand.add(card4);
        testHand.add(card5);

        testPlayer.fillHandCards(testHand);

        playedCards.add(aPlayedCard);

        testPlayer.updateCardPickability(playedCards, false, true);
        assertEquals(false, testHand.get(0).getPickable());
        assertEquals(true, testHand.get(1).getPickable());
        assertEquals(true, testHand.get(2).getPickable());
        assertEquals(false, testHand.get(3).getPickable());
        assertEquals(false, testHand.get(4).getPickable());
  
    }


    /*Test for smalelst card in hand */
    @Test
    void getSmallestRankCard(){
        Player testPlayer = new Player();
        ArrayList<Card> testHand = new ArrayList <Card>();
        ArrayList<Card> playedCards = new ArrayList <Card>();
       
        Card card1 = new Card(Suit.SPADES,Rank.QUEEN,true);
        Card card2 = new Card(Suit.DIAMONDS,Rank.NINE,true);
        Card card3 = new Card(Suit.DIAMONDS,Rank.TWO,true);
        Card card4 = new Card(Suit.HEARTS,Rank.THREE,true);
        Card card5 = new Card(Suit.HEARTS,Rank.JACK,true);
        Card card6 = new Card(Suit.CLUBS,Rank.TWO,true);


        testHand.add(card1);
        testHand.add(card2);
        testHand.add(card3);
        testHand.add(card4);
        testHand.add(card5);
  
        testPlayer.fillHandCards(testHand);
       
        assertEquals("TWO of DIAMONDS[pickable by null]",  testPlayer.getSmallestRankCard().toString());
        
    }

    
    /*Test for smallest card in hand */
    @Test
    void getLargestRankCard(){
        Player testPlayer = new Player();
        ArrayList<Card> testHand = new ArrayList <Card>();
        ArrayList<Card> playedCards = new ArrayList <Card>();
       
        Card card1 = new Card(Suit.SPADES,Rank.QUEEN,true);
        Card card2 = new Card(Suit.DIAMONDS,Rank.NINE,true);
        Card card3 = new Card(Suit.DIAMONDS,Rank.TWO,true);
        Card card4 = new Card(Suit.HEARTS,Rank.THREE,true);
        Card card5 = new Card(Suit.HEARTS,Rank.JACK,true);
        Card card6 = new Card(Suit.CLUBS,Rank.TWO,true);


        testHand.add(card1);
        testHand.add(card2);
        testHand.add(card3);
        testHand.add(card4);
        testHand.add(card5);
  
        testPlayer.fillHandCards(testHand);
       
        assertEquals("QUEEN of SPADES[pickable by null]",  testPlayer.getLargestRankCard().toString());
        
    }


    /*Test cards pickaility for following at first trick when hand is all point cards. Queen of Spades
    is an exception and can be played */
    
    @Test
    void checkPickability_HandsAllPointCardsAndQueenSpades(){
        Player testPlayer = new Player();
        ArrayList<Card> testHand = new ArrayList <Card>();
        ArrayList<Card> playedCards = new ArrayList <Card>();
       
        Card card1 = new Card(Suit.SPADES,Rank.QUEEN,true);
        Card card2 = new Card(Suit.HEARTS,Rank.ACE,true);
        Card card3 = new Card(Suit.HEARTS,Rank.TWO,true);
        Card card4 = new Card(Suit.HEARTS,Rank.THREE,true);
        Card card5 = new Card(Suit.HEARTS,Rank.FOUR,true);
        Card card6 = new Card(Suit.HEARTS,Rank.FIVE,true);
        Card card7 = new Card(Suit.HEARTS,Rank.SIX,true);
        Card card8 = new Card(Suit.HEARTS,Rank.SEVEN,true);
        Card card9 = new Card(Suit.HEARTS,Rank.EIGHT,true);
        Card card10 = new Card(Suit.HEARTS,Rank.NINE,true);
        Card card11 = new Card(Suit.HEARTS,Rank.TEN,true);
        Card card12 = new Card(Suit.HEARTS,Rank.JACK,true);
        Card card13 = new Card(Suit.HEARTS,Rank.QUEEN,true);

        Card aPlayedCard = new Card(Suit.CLUBS, Rank.TWO, true);

        testHand.add(card1);
        testHand.add(card2);
        testHand.add(card3);
        testHand.add(card4);
        testHand.add(card5);
        testHand.add(card6);
        testHand.add(card7);
        testHand.add(card8);
        testHand.add(card9);
        testHand.add(card10);
        testHand.add(card11);
        testHand.add(card12);
        testHand.add(card13);

        playedCards.add(aPlayedCard);
            
        testPlayer.fillHandCards(testHand);
        testPlayer.updateCardPickability(playedCards, false, false);
        assertEquals(true, testHand.get(0).getPickable());
        assertEquals(true, testHand.get(1).getPickable());
        assertEquals(true, testHand.get(2).getPickable());
        assertEquals(true, testHand.get(3).getPickable());
        assertEquals(true, testHand.get(4).getPickable());
        assertEquals(true, testHand.get(5).getPickable());
        assertEquals(true, testHand.get(6).getPickable());
        assertEquals(true, testHand.get(7).getPickable());
        assertEquals(true, testHand.get(8).getPickable());
        assertEquals(true, testHand.get(9).getPickable());
        assertEquals(true, testHand.get(10).getPickable());
        assertEquals(true, testHand.get(11).getPickable());
        assertEquals(true, testHand.get(12).getPickable());

    }

    // /*Test cards pickaility for following at first trick when hand is all HEARTS cards.*/
    
    @Test
    void checkPickability_HandsAllHeartsOnFirstTrick(){
        Player testPlayer = new Player();
        ArrayList<Card> testHand = new ArrayList <Card>();
        ArrayList<Card> playedCards = new ArrayList <Card>();
       
        Card card1 = new Card(Suit.SPADES,Rank.QUEEN,true);
        Card card2 = new Card(Suit.HEARTS,Rank.ACE,true);
        Card card3 = new Card(Suit.HEARTS,Rank.TWO,true);
        Card card4 = new Card(Suit.HEARTS,Rank.THREE,true);
        Card card5 = new Card(Suit.HEARTS,Rank.FOUR,true);
        Card card6 = new Card(Suit.HEARTS,Rank.FIVE,true);
        Card card7 = new Card(Suit.HEARTS,Rank.SIX,true);
        Card card8 = new Card(Suit.HEARTS,Rank.SEVEN,true);
        Card card9 = new Card(Suit.HEARTS,Rank.EIGHT,true);
        Card card10 = new Card(Suit.HEARTS,Rank.NINE,true);
        Card card11 = new Card(Suit.HEARTS,Rank.TEN,true);
        Card card12 = new Card(Suit.HEARTS,Rank.JACK,true);
        Card card13 = new Card(Suit.HEARTS,Rank.QUEEN,true);

        Card aPlayedCard = new Card(Suit.CLUBS, Rank.TWO, true);

        testHand.add(card1);
        testHand.add(card2);
        testHand.add(card3);
        testHand.add(card4);
        testHand.add(card5);
        testHand.add(card6);
        testHand.add(card7);
        testHand.add(card8);
        testHand.add(card9);
        testHand.add(card10);
        testHand.add(card11);
        testHand.add(card12);
        testHand.add(card13);

        playedCards.add(aPlayedCard);
            
        testPlayer.fillHandCards(testHand);
        testPlayer.updateCardPickability(playedCards, false, false);
        assertEquals(true, testHand.get(0).getPickable());
        assertEquals(true, testHand.get(1).getPickable());
        assertEquals(true, testHand.get(2).getPickable());
        assertEquals(true, testHand.get(3).getPickable());
        assertEquals(true, testHand.get(4).getPickable());
        assertEquals(true, testHand.get(5).getPickable());
        assertEquals(true, testHand.get(6).getPickable());
        assertEquals(true, testHand.get(7).getPickable());
        assertEquals(true, testHand.get(8).getPickable());
        assertEquals(true, testHand.get(9).getPickable());
        assertEquals(true, testHand.get(10).getPickable());
        assertEquals(true, testHand.get(11).getPickable());
        assertEquals(true, testHand.get(12).getPickable());
        
        assertEquals(true, testPlayer.checkAllPointCards(testHand));
    }
    
     /*Test hand for all point cards */
    
    @Test
    void checkPickabilityHandsAllHearts(){
        Player testPlayer = new Player();
        ArrayList<Card> testHand = new ArrayList <Card>();
        ArrayList<Card> playedCards = new ArrayList <Card>();
       
        Card card1 = new Card(Suit.SPADES,Rank.QUEEN,true);
        Card card2 = new Card(Suit.HEARTS,Rank.ACE,true);
        Card card3 = new Card(Suit.HEARTS,Rank.TWO,true);
        Card card4 = new Card(Suit.HEARTS,Rank.THREE,true);
        Card card5 = new Card(Suit.HEARTS,Rank.FOUR,true);
        Card card6 = new Card(Suit.HEARTS,Rank.FIVE,true);
        Card card7 = new Card(Suit.HEARTS,Rank.SIX,true);
        Card card8 = new Card(Suit.HEARTS,Rank.SEVEN,true);
        Card card9 = new Card(Suit.HEARTS,Rank.EIGHT,true);
        Card card10 = new Card(Suit.HEARTS,Rank.NINE,true);
        Card card11 = new Card(Suit.HEARTS,Rank.TEN,true);
        Card card12 = new Card(Suit.HEARTS,Rank.JACK,true);
        Card card13 = new Card(Suit.HEARTS,Rank.QUEEN,true);

        Card aPlayedCard = new Card(Suit.CLUBS, Rank.TWO, true);

        testHand.add(card1);
        testHand.add(card2);
        testHand.add(card3);
        testHand.add(card4);
        testHand.add(card5);
        testHand.add(card6);
        testHand.add(card7);
        testHand.add(card8);
        testHand.add(card9);
        testHand.add(card10);
        testHand.add(card11);
        testHand.add(card12);
        testHand.add(card13);

        playedCards.add(aPlayedCard);
            
        testPlayer.fillHandCards(testHand);
        
        assertEquals(true, testPlayer.checkAllPointCards(testHand));
    }

}