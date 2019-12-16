package main.model;


import main.constant.*;
import main.model.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;


class BotTests {

    /*Check for top 3 cards in a hand of all Spades */
    @Test
    void checkTop3CardsForSpadesHand(){
        Bot testBot = new Bot();
   
        List<Card> top3Cards = new ArrayList<Card>();
        List<Card> testHand = new ArrayList<Card>();
       
        for (Rank rank : Rank.values()) {
            testHand.add(new Card(Suit.SPADES, rank, true));
        }
        
        testBot.fillHandCards(testHand);
        top3Cards = testBot.passThreeCards();

        assertEquals("ACE of SPADES[pickable by null]", top3Cards.get(0).toString());
        assertEquals("KING of SPADES[pickable by null]", top3Cards.get(1).toString());
        assertEquals("QUEEN of SPADES[pickable by null]", top3Cards.get(2).toString());
    }

    /*Check for top 3 cards in a hand of lower spectrum and mixed suits */
    @Test
    void checkTop3CardsForMixedHand(){
        Bot testBot = new Bot();
   
        List<Card> top3Cards = new ArrayList<Card>();
        List<Card> testHand = new ArrayList<Card>();
       
        Card card1 = new Card(Suit.SPADES,Rank.TWO,true);
        Card card2 = new Card(Suit.CLUBS,Rank.TWO,true);
        Card card3 = new Card(Suit.DIAMONDS,Rank.TWO,true);
        Card card4 = new Card(Suit.HEARTS,Rank.TWO,true);
        Card card5 = new Card(Suit.SPADES,Rank.THREE,true);
        Card card6 = new Card(Suit.CLUBS,Rank.THREE,true);
        Card card7 = new Card(Suit.DIAMONDS,Rank.THREE,true);
        Card card8 = new Card(Suit.HEARTS,Rank.THREE,true);

        testHand.add(card1);
        testHand.add(card2);
        testHand.add(card3);
        testHand.add(card4);
        testHand.add(card5);
        testHand.add(card6);
        testHand.add(card7);
        testHand.add(card8);
        
        testBot.fillHandCards(testHand);
        top3Cards = testBot.passThreeCards();

        assertEquals("THREE of CLUBS[pickable by null]", top3Cards.get(0).toString());
        assertEquals("THREE of DIAMONDS[pickable by null]", top3Cards.get(1).toString());
        assertEquals("THREE of SPADES[pickable by null]", top3Cards.get(2).toString());
    }
    /* Check that bot does pass and receives the correct 3 cards */
    @Test
    void passedCards(){
        Bot spadeBot = new Bot();
        Bot heartBot = new Bot();
   
        List<Card> heartHand, spadeHand, top3Hearts, top3Spades;

        heartHand = new ArrayList<Card>();
        spadeHand = new ArrayList<Card>();

        top3Hearts = new ArrayList<Card>();
        top3Spades = new ArrayList<Card>();
       
        for (Rank rank : Rank.values()) {
            heartHand.add(new Card(Suit.HEARTS, rank, true));
        }

        for (Rank rank : Rank.values()) {
            spadeHand.add(new Card(Suit.SPADES, rank, true));
        }
        
        heartBot.fillHandCards(heartHand);
        spadeBot.fillHandCards(spadeHand);
        
        System.out.println(spadeBot.getHandCards());

        top3Hearts = heartBot.passThreeCards();
        top3Spades = spadeBot.passThreeCards();

        heartBot.removeHand(top3Hearts);
        spadeBot.removeHand(top3Spades);

        heartBot.addCardsToHand(top3Spades);
        spadeBot.addCardsToHand(top3Hearts);

        System.out.println(spadeBot.getHandCards());

        assertEquals("QUEEN of SPADES[pickable by null]", heartBot.getHandCards().get(0).toString());
        assertEquals("KING of SPADES[pickable by null]", heartBot.getHandCards().get(1).toString());
        assertEquals("ACE of SPADES[pickable by null]", heartBot.getHandCards().get(2).toString());
        assertEquals("ACE of HEARTS[pickable by null]", spadeBot.getHandCards().get(12).toString());
        assertEquals("KING of HEARTS[pickable by null]", spadeBot.getHandCards().get(11).toString());
        assertEquals("QUEEN of HEARTS[pickable by null]", spadeBot.getHandCards().get(10).toString());
    }

    /* Check that bot does pass and receives the correct 3 cards */
    @Test
    void passedCardsForMixedHand(){
        Bot testbot1 = new Bot();
        Bot testbot2 = new Bot();
   
        List<Card> botHand1, botHand2, top3Cards1, top3Cards2;

        botHand1 = new ArrayList<Card>();
        botHand2 = new ArrayList<Card>();

        top3Cards1 = new ArrayList<Card>();
        top3Cards2 = new ArrayList<Card>();
       
        Card card1 = new Card(Suit.SPADES,Rank.TWO,true);
        Card card2 = new Card(Suit.CLUBS,Rank.TWO,true);
        Card card3 = new Card(Suit.DIAMONDS,Rank.TWO,true);
        Card card4 = new Card(Suit.HEARTS,Rank.TWO,true);
        Card card5 = new Card(Suit.SPADES,Rank.THREE,true);
        Card card6 = new Card(Suit.CLUBS,Rank.THREE,true);
        Card card7 = new Card(Suit.DIAMONDS,Rank.THREE,true);
        Card card8 = new Card(Suit.HEARTS,Rank.THREE,true);

        botHand1.add(card1);
        botHand1.add(card2);
        botHand1.add(card3);
        botHand1.add(card4);
        botHand2.add(card5);
        botHand2.add(card6);
        botHand2.add(card7);
        botHand2.add(card8);
        
        
        testbot1.fillHandCards(botHand1);
        testbot2.fillHandCards(botHand2);

        top3Cards1 = testbot1.passThreeCards();
        top3Cards2 = testbot2.passThreeCards();

        testbot1.removeHand(top3Cards1);
        testbot2.removeHand(top3Cards2);

        testbot1.addCardsToHand(top3Cards2);
        testbot2.addCardsToHand(top3Cards1);

        assertEquals("THREE of CLUBS[pickable by null]", testbot1.getHandCards().get(0).toString());
        assertEquals("THREE of DIAMONDS[pickable by null]", testbot1.getHandCards().get(1).toString());
        assertEquals("THREE of SPADES[pickable by null]", testbot1.getHandCards().get(2).toString());
        assertEquals("TWO of SPADES[pickable by null]", testbot2.getHandCards().get(2).toString());
        assertEquals("TWO of DIAMONDS[pickable by null]", testbot2.getHandCards().get(1).toString());
        assertEquals("TWO of CLUBS[pickable by null]", testbot2.getHandCards().get(0).toString());
    }


    /*Tests for first turn bot to throw 2 of clubs */
    @Test
    void testForThrows2Clubs(){
        
        Bot testBot = new Bot();
        List<Card> testHand = new ArrayList<Card>();
        List<Card> playedCards = new ArrayList <Card>();

        Card card1 = new Card(Suit.CLUBS,Rank.TWO,true);
        Card card2 = new Card(Suit.DIAMONDS,Rank.NINE,true);
        Card card3 = new Card(Suit.DIAMONDS,Rank.TWO,true);
        Card card4 = new Card(Suit.HEARTS,Rank.THREE,true);
        Card card5 = new Card(Suit.HEARTS,Rank.JACK,true);
        Card card6 = new Card(Suit.DIAMONDS,Rank.TWO,true);

        
        testHand.add(card1);
        testHand.add(card2);
        testHand.add(card3);
        testHand.add(card4);
        testHand.add(card5);
        testHand.add(card6);

        testBot.fillHandCards(testHand);

        testBot.updateCardPickability(playedCards, false, false);
        assertEquals("TWO of CLUBS[pickable by null]", testBot.playCard(null, 0).toString());
       
    }
    /*Test smallest rank card after first trick - first player will always throw smallest */
    @Test
    void firstBotThrowSmallestRankTest(){
        
        Bot testBot = new Bot();
        ArrayList<Card> testHand = new ArrayList <Card>();
        List<Card> playedCards = new ArrayList <Card>();
       
        Card card1 = new Card(Suit.CLUBS,Rank.FOUR,true);
        Card card2 = new Card(Suit.CLUBS,Rank.NINE,true);
        Card card3 = new Card(Suit.DIAMONDS,Rank.ACE,true);
        Card card4 = new Card(Suit.SPADES,Rank.SIX,true);
        Card card5 = new Card(Suit.SPADES,Rank.TEN,true);
        Card card6 = new Card(Suit.HEARTS,Rank.THREE,true);

        testHand.add(card1);
        testHand.add(card2);
        testHand.add(card3);
        testHand.add(card4);
        testHand.add(card5);
        testHand.add(card6);
        
        testBot.fillHandCards(testHand);

        testBot.updateCardPickability(playedCards, false, false);

        assertEquals("FOUR of CLUBS[pickable by null]", testBot.playCard(null, 0).toString());
       
    }
    /*Test smallest rank card after first trick - first player will always throw smallest. Test for hearts broken */
    @Test
    void firstBotThrowSmallestRankTestHeartsBroken(){
        
        Bot testBot = new Bot();
        ArrayList<Card> testHand = new ArrayList <Card>();
        List<Card> playedCards = new ArrayList <Card>();
       
        Card card1 = new Card(Suit.CLUBS,Rank.FOUR,true);
        Card card2 = new Card(Suit.CLUBS,Rank.NINE,true);
        Card card3 = new Card(Suit.DIAMONDS,Rank.ACE,true);
        Card card4 = new Card(Suit.SPADES,Rank.SIX,true);
        Card card5 = new Card(Suit.SPADES,Rank.TEN,true);
        Card card6 = new Card(Suit.HEARTS,Rank.THREE,true);

        testHand.add(card1);
        testHand.add(card2);
        testHand.add(card3);
        testHand.add(card4);
        testHand.add(card5);
        testHand.add(card6);
        
        testBot.fillHandCards(testHand);

        testBot.updateCardPickability(playedCards, true, true);

        assertEquals("THREE of HEARTS[pickable by null]", testBot.playCard(null, 0).toString());
       
    }

    /*Test second player throw the second highest rank card following a suit*/
    @Test
    void secondBotThrowsHighestRankFollowSuit(){
        
        Bot testBot = new Bot();
        ArrayList<Card> testHand = new ArrayList <Card>();
        List<Card> playedCards = new ArrayList <Card>();
       
        Card card1 = new Card(Suit.CLUBS,Rank.FOUR,true);
        Card card2 = new Card(Suit.CLUBS,Rank.NINE,true);
        Card card3 = new Card(Suit.DIAMONDS,Rank.TWO,true);
        Card card4 = new Card(Suit.DIAMONDS,Rank.THREE,true);
        Card card5 = new Card(Suit.HEARTS,Rank.KING,true);
        Card card6 = new Card(Suit.SPADES,Rank.QUEEN,true);
        Card card7 = new Card(Suit.SPADES,Rank.TEN,true);

        testHand.add(card1);
        testHand.add(card2);
        testHand.add(card3);
        testHand.add(card4);
        testHand.add(card5);
        testHand.add(card6);
        testHand.add(card7);
        
        
        testBot.fillHandCards(testHand);

        Card firstCard = new Card(Suit.SPADES,Rank.JACK,true);

        playedCards.add(firstCard);
        testBot.updateCardPickability(playedCards, true, true);

        Card thrownCard = testBot.playCard(firstCard, 1);
        assertEquals("TEN of SPADES[pickable by null]", thrownCard.toString());
       
    }
    /*Test third player throw the second highest rank card following a suit*/
    @Test
    void thirdBotThrowsSecondHighestFollowSuit(){
        
        Bot testBot = new Bot();
        ArrayList<Card> testHand = new ArrayList <Card>();
        List<Card> playedCards = new ArrayList <Card>();
       
        Card card1 = new Card(Suit.CLUBS,Rank.FOUR,true);
        Card card2 = new Card(Suit.CLUBS,Rank.NINE,true);
        Card card3 = new Card(Suit.HEARTS,Rank.TWO,true);
        Card card4 = new Card(Suit.DIAMONDS,Rank.THREE,true);
        Card card5 = new Card(Suit.DIAMONDS,Rank.JACK,true);
        Card card6 = new Card(Suit.SPADES,Rank.SIX,true);
        
        testHand.add(card1);
        testHand.add(card2);
        testHand.add(card3);
        testHand.add(card4);
        testHand.add(card5);
        testHand.add(card6);
        
        testBot.fillHandCards(testHand);
        Card thirdCard = new Card(Suit.DIAMONDS,Rank.TEN,true);

        playedCards.add(thirdCard);
        testBot.updateCardPickability(playedCards, true, true);

        Card thrownCard = testBot.playCard(thirdCard, 2);
        assertEquals("THREE of DIAMONDS[pickable by null]", thrownCard.toString());
       
    }

    
    /* if handCards have a card of same leading suit but only larger than the largest card played. 
            Bot has no choice but to return that card */
    @Test
    void throwCardFollowSuitHigherThanCardsPlayed(){
        
        Bot testBot = new Bot();
        ArrayList<Card> testHand = new ArrayList <Card>();
        List<Card> playedCards = new ArrayList <Card>();
       
        Card card1 = new Card(Suit.CLUBS,Rank.FOUR,true);
        Card card2 = new Card(Suit.CLUBS,Rank.NINE,true);
        Card card3 = new Card(Suit.HEARTS,Rank.TWO,true);
        Card card4 = new Card(Suit.DIAMONDS,Rank.JACK,true);
        Card card5 = new Card(Suit.SPADES,Rank.SIX,true);
        
        testHand.add(card1);
        testHand.add(card2);
        testHand.add(card3);
        testHand.add(card4);
        testHand.add(card5);
        
        testBot.fillHandCards(testHand);
        Card lastCard = new Card(Suit.DIAMONDS,Rank.TEN,true);

        playedCards.add(lastCard);
        testBot.updateCardPickability(playedCards, true, true);
        Card thrownCard = testBot.playCard(lastCard, 2);
        assertEquals("JACK of DIAMONDS[pickable by null]", thrownCard.toString());
       
    }

    /*Last bot will always throw largest rank card */
    @Test
    void lastBotThrowsLargestRank(){
        
        Bot testBot = new Bot();
        ArrayList<Card> testHand = new ArrayList <Card>();
        List<Card> playedCards = new ArrayList <Card>();
       
        Card card1 = new Card(Suit.CLUBS,Rank.FOUR,true);
        Card card2 = new Card(Suit.DIAMONDS,Rank.JACK,true);
        Card card3 = new Card(Suit.SPADES,Rank.SIX,true);
        Card card4 = new Card(Suit.SPADES,Rank.TEN,true);
        Card card5 = new Card(Suit.SPADES,Rank.QUEEN,true);
        Card card6 = new Card(Suit.SPADES,Rank.KING,true);
        Card card7 = new Card(Suit.HEARTS,Rank.THREE,true);

        testHand.add(card1);
        testHand.add(card2);
        testHand.add(card3);
        testHand.add(card4);
        testHand.add(card5);
        testHand.add(card6);
        testHand.add(card7);

        testBot.fillHandCards(testHand);
        Card lastCard = new Card(Suit.SPADES,Rank.JACK,true);

        playedCards.add(lastCard);
        testBot.updateCardPickability(playedCards, true, true);
        Card thrownCard = testBot.playCard(lastCard, 3);
        assertEquals("KING of SPADES[pickable by null]", thrownCard.toString());
       
    }

    /*Last Bot will always throw largest rank card (no clubs) */
    @Test
    void lastBotThrowsLargestRankNotFollowingSuit(){
        
        Bot testBot = new Bot();
        ArrayList<Card> testHand = new ArrayList <Card>();
        List<Card> playedCards = new ArrayList <Card>();

        Card card1 = new Card(Suit.DIAMONDS,Rank.JACK,true);
        Card card2 = new Card(Suit.SPADES,Rank.QUEEN,true);
        Card card3 = new Card(Suit.SPADES,Rank.KING,true);
        Card card4 = new Card(Suit.HEARTS,Rank.THREE,true);

        testHand.add(card1);
        testHand.add(card2);
        testHand.add(card3);
        testHand.add(card4);

           
        testBot.fillHandCards(testHand);
        Card lastCard = new Card(Suit.CLUBS,Rank.TWO,true);

        playedCards.add(lastCard);
        testBot.updateCardPickability(playedCards, true, true);
        Card thrownCard = testBot.playCard(lastCard, 3);
        assertEquals("KING of SPADES[pickable by null]", thrownCard.toString());
       
    }
    /*Second Bot will always throw largest rank card (no clubs) */
    @Test
    void secondBotThrowsLargestRankNotFollowingSuit(){
        
        Bot testBot = new Bot();
        ArrayList<Card> testHand = new ArrayList <Card>();
        List<Card> playedCards = new ArrayList <Card>();
        
        Card card1 = new Card(Suit.DIAMONDS,Rank.JACK,true);
        Card card2 = new Card(Suit.SPADES,Rank.QUEEN,true);
        Card card3 = new Card(Suit.SPADES,Rank.KING,true);
        Card card4 = new Card(Suit.HEARTS,Rank.THREE,true);

        testHand.add(card1);
        testHand.add(card2);
        testHand.add(card3);
        testHand.add(card4);

           
        testBot.fillHandCards(testHand);
        Card lastCard = new Card(Suit.CLUBS,Rank.TWO,true);

        playedCards.add(lastCard);
        testBot.updateCardPickability(playedCards, true, true);
        Card thrownCard = testBot.playCard(lastCard, 1);
        assertEquals("KING of SPADES[pickable by null]", thrownCard.toString());
       
    }
    /*Third Bot will always throw largest rank card (no clubs) */
    @Test
    void thirdBotThrowsLargestRankNotFollowingSuit(){
        
        Bot testBot = new Bot();
        ArrayList<Card> testHand = new ArrayList <Card>();
        List<Card> playedCards = new ArrayList <Card>();
        
        Card card1 = new Card(Suit.DIAMONDS,Rank.JACK,true);
        Card card2 = new Card(Suit.SPADES,Rank.QUEEN,true);
        Card card3 = new Card(Suit.SPADES,Rank.KING,true);
        Card card4 = new Card(Suit.HEARTS,Rank.THREE,true);

        testHand.add(card1);
        testHand.add(card2);
        testHand.add(card3);
        testHand.add(card4);

           
        testBot.fillHandCards(testHand);
        Card lastCard = new Card(Suit.CLUBS,Rank.TWO,true);

        playedCards.add(lastCard);
        testBot.updateCardPickability(playedCards, true, true);
        Card thrownCard = testBot.playCard(lastCard, 2);
        assertEquals("KING of SPADES[pickable by null]", thrownCard.toString());
       
    }

    





}