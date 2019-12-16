package main.model;

import java.util.Collections;
import java.util.Stack;
import java.util.ArrayList;
import java.util.List;

import main.constant.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import main.constant.*;

class DealerTests {

    // @BeforeEach
    // public void setup() {
    //     Deck testDeck = new Deck();
    //     Stack<Card> shuffledCards = testDeck.getDeckCards();
    // }

    /*Check dealer dealt 13 cards  */
   @Test
   void testUserHandDistributed() {
        Dealer testDealer = new Dealer();
        testDealer.initDealer();
        testDealer.newRound();
        assertEquals(13 , testDealer.getUserHandCards().size());
   }

   @Test
   void testBotAHandDistributed() {
        Dealer testDealer = new Dealer();
        testDealer.initDealer();
        testDealer.newRound();
        assertEquals(13 , testDealer.getBotA().getHandCards().size());
   }

   @Test
   void testBotBHandDistributed() {
        Dealer testDealer = new Dealer();
        testDealer.initDealer();
        testDealer.newRound();
        assertEquals(13 , testDealer.getBotB().getHandCards().size());
   }

   @Test
   void testBotCHandDistributed() {
        Dealer testDealer = new Dealer();
        testDealer.initDealer();
        testDealer.newRound();
        assertEquals(13 , testDealer.getBotC().getHandCards().size());
   }
   /*Test for largest card in a trick following diamonds as suit*/
   @Test
   void testForLargestCardInTrick() {
        Dealer testDealer = new Dealer();

        Suit suit1 = Suit.DIAMONDS;
        Suit suit2 = Suit.SPADES;
        Suit suit3 = Suit.SPADES;
        Suit suit4 = Suit.DIAMONDS;
        
        Rank rank1  = Rank.TWO;
        Rank rank2 = Rank.KING;
        Rank rank3 = Rank.ACE;
        Rank rank4 = Rank.FIVE;

        Card card1 = new Card(suit1,rank1,false);
        Card card2 = new Card(suit2,rank2,false);
        Card card3 = new Card(suit3,rank3,false);
        Card card4 = new Card(suit4,rank4,false);

   
        testDealer.addToPlayedCards(card1);
        testDealer.addToPlayedCards(card2);
        testDealer.addToPlayedCards(card3);
        testDealer.addToPlayedCards(card4);

        assertEquals(card4 , testDealer.getLargestCardFromTrick());
   }


   /*Test for largest card in a trick following diamonds as suit and comparing rank*/
   @Test
   void testForLargestCardInTrickComparingRank() {
        Dealer testDealer = new Dealer();

        Suit suit1 = Suit.DIAMONDS;
        Suit suit2 = Suit.SPADES;
        Suit suit3 = Suit.DIAMONDS;
        Suit suit4 = Suit.DIAMONDS;
        
        Rank rank1  = Rank.TWO;
        Rank rank2 = Rank.KING;
        Rank rank3 = Rank.ACE;
        Rank rank4 = Rank.FIVE;

        Card card1 = new Card(suit1,rank1,false);
        Card card2 = new Card(suit2,rank2,false);
        Card card3 = new Card(suit3,rank3,false);
        Card card4 = new Card(suit4,rank4,false);

   
        testDealer.addToPlayedCards(card1);
        testDealer.addToPlayedCards(card2);
        testDealer.addToPlayedCards(card3);
        testDealer.addToPlayedCards(card4);

        assertEquals(card3 , testDealer.getLargestCardFromTrick());
   }

   /*Test for next player in a trick, assuming user starts first. Next player should be Bot A*/
   @Test
   void testForNextPlayerUserStartsFirst() {
        Dealer testDealer = new Dealer();
        testDealer.initDealer();
        Human testHuman = testDealer.getUser();
        testDealer.setNextPlayer(testDealer.getUser().getId());
        
        assertEquals(testDealer.getUser() , testDealer.getTrickPlayer());
        assertEquals(testDealer.getBotA().getId(), testDealer.getNextPlayer());
   }

   /*Test for first player that has 2 club. Test for whoever has 2 clubs is the playerStarter */
   @Test
   void testFirstPlayer() {
        Dealer testDealer = new Dealer();
        testDealer.initDealer();
        
        ArrayList<Card> heartCards, diamondCards, clubCards, spadeCards;

        spadeCards = new ArrayList <Card>();
        heartCards = new ArrayList <Card>();
        diamondCards = new ArrayList <Card>();
        clubCards = new ArrayList <Card>();

        for (Rank rank : Rank.values()) {
            heartCards.add(new Card(Suit.HEARTS, rank, true));
        }

        for (Rank rank : Rank.values()) {
            spadeCards.add(new Card(Suit.SPADES, rank, true));
        }
        for (Rank rank : Rank.values()) {
            diamondCards.add(new Card(Suit.DIAMONDS, rank, true));
        }

        for (Rank rank : Rank.values()) {
            clubCards.add(new Card(Suit.CLUBS, rank, true));
        }
        
        testDealer.getBotA().fillHandCards(heartCards);
        testDealer.getBotB().fillHandCards(spadeCards);
        testDealer.getBotC().fillHandCards(diamondCards);
        testDealer.getUser().fillHandCards(clubCards);

        testDealer.setPlayerStarter();

        
        assertEquals(testDealer.getUser().getId(), testDealer.getNextPlayer());
   }

    /*Test for next player to play clockwise of player who has 2 clubs (BotA has 2 clubs)*/
   @Test
   void testBotBIsNextPlayerWhenBotAHas2Clubs() {
        Dealer testDealer = new Dealer();
        testDealer.initDealer();
        
        ArrayList<Card>  clubCards;
        clubCards = new ArrayList <Card>();

        for (Rank rank : Rank.values()) {
            clubCards.add(new Card(Suit.CLUBS, rank, true));
        }
        
        testDealer.getBotA().fillHandCards(clubCards);
        testDealer.setPlayerStarter(); //botA is nextPlayer
        testDealer.getTrickPlayer();
        assertEquals(testDealer.getBotB().getId(), testDealer.getNextPlayer());
   }

    /*Test for next player to play clockwise of player who has 2 clubs (BotB has 2 clubs)*/
    @Test
    void testBotCIsNextPlayerWhenBotBHas2Clubs() {
        Dealer testDealer = new Dealer();
        testDealer.initDealer();
            
        ArrayList<Card>  clubCards;
        clubCards = new ArrayList <Card>();
    
        for (Rank rank : Rank.values()) {
            clubCards.add(new Card(Suit.CLUBS, rank, true));
        }
            
        testDealer.getBotB().fillHandCards(clubCards);
        testDealer.setPlayerStarter(); 
        testDealer.getTrickPlayer();
        assertEquals(testDealer.getBotC().getId(), testDealer.getNextPlayer());
    }
    
    /*Test for next player to play clockwise of player who has 2 clubs (BotC has 2 clubs)*/
    @Test
    void testUserIsNextPlayerWhenBotCHas2Clubs() {
        Dealer testDealer = new Dealer();
        testDealer.initDealer();
            
        ArrayList<Card>  clubCards;
        clubCards = new ArrayList <Card>();

        for (Rank rank : Rank.values()) {
            clubCards.add(new Card(Suit.CLUBS, rank, true));
        }
            
        testDealer.getBotC().fillHandCards(clubCards);
        testDealer.setPlayerStarter(); 
        testDealer.getTrickPlayer();
        assertEquals(testDealer.getUser().getId(), testDealer.getNextPlayer());
    }

    /*Test for next player to play clockwise of player who has 2 clubs (User has 2 clubs)*/
    @Test
    void testNextPlayerWhenPlayerHas2Clubs() {
        Dealer testDealer = new Dealer();
        testDealer.initDealer();
            
        ArrayList<Card>  clubCards;
        clubCards = new ArrayList <Card>();

        for (Rank rank : Rank.values()) {
            clubCards.add(new Card(Suit.CLUBS, rank, true));
        }
            
        testDealer.getUser().fillHandCards(clubCards);
        testDealer.setPlayerStarter(); 
        testDealer.getTrickPlayer();
        assertEquals(testDealer.getBotA().getId(), testDealer.getNextPlayer());
    }


    /**Test for winner of trick following a suit. Compares RANK of a card */
   @Test
   void testWinnerOfTrickFollowingClubSuit() {
        Dealer testDealer = new Dealer();
        testDealer.initDealer();

        Bot botA = testDealer.getBotA();
        Bot botB = testDealer.getBotB();
        Bot botC = testDealer.getBotC();

        ArrayList<Card> botAHand, botBHand, botCHand = new ArrayList <Card>();

        // botAHand = new ArrayList <Card>();
        botAHand = new ArrayList <Card>();
        botBHand = new ArrayList <Card>();
        botCHand = new ArrayList <Card>();

        Card thrownCard = new Card(Suit.CLUBS,Rank.FOUR,true);
        
        Card cardA1 = new Card(Suit.CLUBS,Rank.KING,true);

        Card cardB1 = new Card(Suit.CLUBS,Rank.FIVE,true);

        Card cardC1 = new Card(Suit.CLUBS,Rank.QUEEN,true);

        botAHand.add(cardA1);
        botBHand.add(cardB1);
        botCHand.add(cardC1);
        
        // botA.fillHandCards(botAHand);
        botA.fillHandCards(botAHand);
        botB.fillHandCards(botBHand);
        botC.fillHandCards(botCHand);

        testDealer.addToPlayedCards(thrownCard);
        
        Card cardToPlay = botA.playCard(thrownCard, 1);
        testDealer.addToPlayedCards(cardToPlay);
        Card largestPlayedCard = testDealer.getLargestCardFromTrick();

        cardToPlay = botB.playCard(largestPlayedCard, 2);
        testDealer.addToPlayedCards(cardToPlay);
        largestPlayedCard = testDealer.getLargestCardFromTrick();

        cardToPlay = botC.playCard(largestPlayedCard, 3);
        testDealer.addToPlayedCards(cardToPlay);

        // List<Card> allPlayedCards = testDealer.getPlayedCardsForTrick();
        assertEquals("botA", testDealer.getWinnerOfTrick(testDealer.getPlayedCardsForTrick()));
   }

   /**Test for winner of trick not following a suit. Compares SUIT of a card */
   @Test
   void testWinnerOfTrickNotFollowingSuit() {
        Dealer testDealer = new Dealer();
        testDealer.initDealer();

        Bot botA = testDealer.getBotA();
        Bot botB = testDealer.getBotB();
        Bot botC = testDealer.getBotC();

        ArrayList<Card> botAHand, botBHand, botCHand = new ArrayList <Card>();

        // botAHand = new ArrayList <Card>();
        botAHand = new ArrayList <Card>();
        botBHand = new ArrayList <Card>();
        botCHand = new ArrayList <Card>();

        Card thrownCard = new Card(Suit.CLUBS,Rank.FOUR,true);
        
        Card cardA1 = new Card(Suit.CLUBS,Rank.ACE,true);

        Card cardB1 = new Card(Suit.DIAMONDS,Rank.ACE,true);

        Card cardC1 = new Card(Suit.SPADES,Rank.ACE,true);

        botAHand.add(cardA1);
        botBHand.add(cardB1);
        botCHand.add(cardC1);
        
        // botA.fillHandCards(botAHand);
        botA.fillHandCards(botAHand);
        botB.fillHandCards(botBHand);
        botC.fillHandCards(botCHand);

        testDealer.addToPlayedCards(thrownCard);
        
        Card cardToPlay = botA.playCard(thrownCard, 1);
        testDealer.addToPlayedCards(cardToPlay);
        Card largestPlayedCard = testDealer.getLargestCardFromTrick();

        cardToPlay = botB.playCard(largestPlayedCard, 2);
        testDealer.addToPlayedCards(cardToPlay);
        largestPlayedCard = testDealer.getLargestCardFromTrick();

        cardToPlay = botC.playCard(largestPlayedCard, 3);
        testDealer.addToPlayedCards(cardToPlay);

        // List<Card> allPlayedCards = testDealer.getPlayedCardsForTrick();
        assertEquals("botA", testDealer.getWinnerOfTrick(testDealer.getPlayedCardsForTrick()));
   }

   @Test
   /*Test for points calculation, all hearts */
   void calculatePoints() {

        Dealer testDealer = new Dealer();
        ArrayList<Card> heartCards = new ArrayList<Card>();
        
        for (Rank rank : Rank.values()) {
            heartCards.add(new Card(Suit.HEARTS, rank, true));
        }
        assertEquals(13 , testDealer.calculatePlayedCardsPoints(heartCards));
        
   }

   @Test
   /*Test for points calculation, all hearts */
   void calculatePointsWithQueenSpades() {

        Dealer testDealer = new Dealer();
        ArrayList<Card> testCards = new ArrayList<Card>();
        
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

        assertEquals(16 , testDealer.calculatePlayedCardsPoints(testCards));
        
   }

  
   @Test
   /*Test for moon shooter */
   void testMoonShooter() {

        Dealer testDealer = new Dealer();
        testDealer.initDealer();
        /*Set user round score and simulate last round */
        testDealer.addRoundScore("user", 26);
        testDealer.addRoundScore("botA", 0);
        testDealer.addRoundScore("botB", 0);
        testDealer.addRoundScore("botC", 0);

        testDealer.addTotalScore("user",26);
        testDealer.addTotalScore("botA", 0);
        testDealer.addTotalScore("botB", 0);
        testDealer.addTotalScore("botC", 0);
        String moonshooter = testDealer.getMoonShooter();
        testDealer.handleMoonShooter(moonshooter);

        assertEquals(0 , testDealer.getTotalScore("user"));
        assertEquals(26 , testDealer.getTotalScore("botA"));
        assertEquals(26 , testDealer.getTotalScore("botB"));
        assertEquals(26 , testDealer.getTotalScore("botC"));

        
   }


        
    /* Check that bot pass to player on the LEFT TOP 3 cards */
    @Test
    void pass3CardsRound1(){
        Dealer testDealer = new Dealer();
        testDealer.initDealer();
        testDealer.setRoundCount(1);
   
        List<Card> heartHand, spadeHand, clubHand, diamondHand,userPassedCards;

        heartHand = new ArrayList<Card>();
        spadeHand = new ArrayList<Card>();
        clubHand = new ArrayList<Card>();
        diamondHand = new ArrayList<Card>();
        userPassedCards = new ArrayList<Card>();
        
        userPassedCards.add(new Card(Suit.DIAMONDS, Rank.ACE, true));
        userPassedCards.add(new Card(Suit.DIAMONDS, Rank.KING, true));
        userPassedCards.add(new Card(Suit.DIAMONDS, Rank.QUEEN, true));

        for(Card c:userPassedCards) {
            c.setOwnerOfCard("user");
        }

        for (Rank rank : Rank.values()) {
            heartHand.add(new Card(Suit.HEARTS, rank, true));
        }

        for (Rank rank : Rank.values()) {
            spadeHand.add(new Card(Suit.SPADES, rank, true));
        }

        for (Rank rank : Rank.values()) {
            clubHand.add(new Card(Suit.CLUBS, rank, true));
        }

        for (Rank rank : Rank.values()) {
            diamondHand.add(new Card(Suit.DIAMONDS, rank, true));
        }
        
        testDealer.getBotA().fillHandCards(heartHand);
        testDealer.getBotB().fillHandCards(spadeHand);
        testDealer.getBotC().fillHandCards(clubHand);
        testDealer.getUser().fillHandCards(diamondHand);

        testDealer.distributePassedCards(userPassedCards);

        assertEquals(13, testDealer.getUserHandCards().size());
        assertEquals(13, testDealer.getBotA().getHandCards().size());
        assertEquals(13, testDealer.getBotB().getHandCards().size());
        assertEquals(13, testDealer.getBotC().getHandCards().size());

        assertEquals("QUEEN of DIAMONDS[pickable by botA]", testDealer.getBotA().getHandCards().get(0).toString());
        assertEquals("KING of DIAMONDS[pickable by botA]", testDealer.getBotA().getHandCards().get(1).toString());
        assertEquals("ACE of DIAMONDS[pickable by botA]", testDealer.getBotA().getHandCards().get(2).toString());

        assertEquals("QUEEN of HEARTS[pickable by botB]", testDealer.getBotB().getHandCards().get(10).toString());
        assertEquals("KING of HEARTS[pickable by botB]", testDealer.getBotB().getHandCards().get(11).toString());
        assertEquals("ACE of HEARTS[pickable by botB]", testDealer.getBotB().getHandCards().get(12).toString());

        assertEquals("QUEEN of SPADES[pickable by botC]", testDealer.getBotC().getHandCards().get(10).toString());
        assertEquals("KING of SPADES[pickable by botC]", testDealer.getBotC().getHandCards().get(11).toString());
        assertEquals("ACE of SPADES[pickable by botC]", testDealer.getBotC().getHandCards().get(12).toString());
        System.out.println(testDealer.getUser().getHandCards());
        System.out.println();
        assertEquals("QUEEN of CLUBS[pickable by user]", testDealer.getUser().getHandCards().get(0).toString());
        assertEquals("KING of CLUBS[pickable by user]", testDealer.getUser().getHandCards().get(1).toString());
        assertEquals("ACE of CLUBS[pickable by user]", testDealer.getUser().getHandCards().get(2).toString());
    }

    // /* Check that bot pass to player on the RIGHT TOP 3 cards */
    @Test
    void pass3CardsRound2(){
        Dealer testDealer = new Dealer();
        testDealer.initDealer();
        testDealer.setRoundCount(2);
   
        List<Card> heartHand, spadeHand, clubHand, diamondHand,userPassedCards;

        heartHand = new ArrayList<Card>();
        spadeHand = new ArrayList<Card>();
        clubHand = new ArrayList<Card>();
        diamondHand = new ArrayList<Card>();
        userPassedCards = new ArrayList<Card>();
        
        userPassedCards.add(new Card(Suit.DIAMONDS, Rank.ACE, true));
        userPassedCards.add(new Card(Suit.DIAMONDS, Rank.KING, true));
        userPassedCards.add(new Card(Suit.DIAMONDS, Rank.QUEEN, true));

        
        for(Card c:userPassedCards) {
            c.setOwnerOfCard("user");
        }

        for (Rank rank : Rank.values()) {
            heartHand.add(new Card(Suit.HEARTS, rank, true));
        }

        for (Rank rank : Rank.values()) {
            spadeHand.add(new Card(Suit.SPADES, rank, true));
        }

        for (Rank rank : Rank.values()) {
            clubHand.add(new Card(Suit.CLUBS, rank, true));
        }

        for (Rank rank : Rank.values()) {
            diamondHand.add(new Card(Suit.DIAMONDS, rank, true));
        }
        
        testDealer.getBotA().fillHandCards(heartHand);
        testDealer.getBotB().fillHandCards(spadeHand);
        testDealer.getBotC().fillHandCards(clubHand);
        testDealer.getUser().fillHandCards(diamondHand);

        testDealer.distributePassedCards(userPassedCards);

        assertEquals(13, testDealer.getUserHandCards().size());
        assertEquals(13, testDealer.getBotA().getHandCards().size());
        assertEquals(13, testDealer.getBotB().getHandCards().size());
        assertEquals(13, testDealer.getBotC().getHandCards().size());

        assertEquals("QUEEN of SPADES[pickable by botA]", testDealer.getBotA().getHandCards().get(0).toString());
        assertEquals("KING of SPADES[pickable by botA]", testDealer.getBotA().getHandCards().get(1).toString());
        assertEquals("ACE of SPADES[pickable by botA]", testDealer.getBotA().getHandCards().get(2).toString());

        assertEquals("QUEEN of CLUBS[pickable by botB]", testDealer.getBotB().getHandCards().get(0).toString());
        assertEquals("KING of CLUBS[pickable by botB]", testDealer.getBotB().getHandCards().get(1).toString());
        assertEquals("ACE of CLUBS[pickable by botB]", testDealer.getBotB().getHandCards().get(2).toString());
        System.out.println(testDealer.getBotC().getHandCards());
        assertEquals("QUEEN of DIAMONDS[pickable by botC]", testDealer.getBotC().getHandCards().get(10).toString());
        assertEquals("KING of DIAMONDS[pickable by botC]", testDealer.getBotC().getHandCards().get(11).toString());
        assertEquals("ACE of DIAMONDS[pickable by botC]", testDealer.getBotC().getHandCards().get(12).toString());
        System.out.println(testDealer.getUser().getHandCards());
        assertEquals("QUEEN of HEARTS[pickable by user]", testDealer.getUser().getHandCards().get(10).toString());
        assertEquals("KING of HEARTS[pickable by user]", testDealer.getUser().getHandCards().get(11).toString());
        assertEquals("ACE of HEARTS[pickable by user]", testDealer.getUser().getHandCards().get(12).toString());
    }

    /* Check that bot pass to player on the OPPOSITE TOP 3 cards */
    @Test
    void pass3CardsRound3(){
        Dealer testDealer = new Dealer();
        testDealer.initDealer();
        testDealer.setRoundCount(3);
   
        List<Card> heartHand, spadeHand, clubHand, diamondHand,userPassedCards;

        heartHand = new ArrayList<Card>();
        spadeHand = new ArrayList<Card>();
        clubHand = new ArrayList<Card>();
        diamondHand = new ArrayList<Card>();
        userPassedCards = new ArrayList<Card>();
        
        userPassedCards.add(new Card(Suit.DIAMONDS, Rank.ACE, true));
        userPassedCards.add(new Card(Suit.DIAMONDS, Rank.KING, true));
        userPassedCards.add(new Card(Suit.DIAMONDS, Rank.QUEEN, true));

        
        for(Card c:userPassedCards) {
            c.setOwnerOfCard("user");
        }

        for (Rank rank : Rank.values()) {
            heartHand.add(new Card(Suit.HEARTS, rank, true));
        }

        for (Rank rank : Rank.values()) {
            spadeHand.add(new Card(Suit.SPADES, rank, true));
        }

        for (Rank rank : Rank.values()) {
            clubHand.add(new Card(Suit.CLUBS, rank, true));
        }

        for (Rank rank : Rank.values()) {
            diamondHand.add(new Card(Suit.DIAMONDS, rank, true));
        }
        
        testDealer.getBotA().fillHandCards(heartHand);
        testDealer.getBotB().fillHandCards(spadeHand);
        testDealer.getBotC().fillHandCards(clubHand);
        testDealer.getUser().fillHandCards(diamondHand);

        testDealer.distributePassedCards(userPassedCards);

        assertEquals(13, testDealer.getUserHandCards().size());
        assertEquals(13, testDealer.getBotA().getHandCards().size());
        assertEquals(13, testDealer.getBotB().getHandCards().size());
        assertEquals(13, testDealer.getBotC().getHandCards().size());

        assertEquals("QUEEN of CLUBS[pickable by botA]", testDealer.getBotA().getHandCards().get(0).toString());
        assertEquals("KING of CLUBS[pickable by botA]", testDealer.getBotA().getHandCards().get(1).toString());
        assertEquals("ACE of CLUBS[pickable by botA]", testDealer.getBotA().getHandCards().get(2).toString());

        assertEquals("QUEEN of DIAMONDS[pickable by botB]", testDealer.getBotB().getHandCards().get(0).toString());
        assertEquals("KING of DIAMONDS[pickable by botB]", testDealer.getBotB().getHandCards().get(1).toString());
        assertEquals("ACE of DIAMONDS[pickable by botB]", testDealer.getBotB().getHandCards().get(2).toString());

        assertEquals("QUEEN of HEARTS[pickable by botC]", testDealer.getBotC().getHandCards().get(10).toString());
        assertEquals("KING of HEARTS[pickable by botC]", testDealer.getBotC().getHandCards().get(11).toString());
        assertEquals("ACE of HEARTS[pickable by botC]", testDealer.getBotC().getHandCards().get(12).toString());

        assertEquals("QUEEN of SPADES[pickable by user]", testDealer.getUser().getHandCards().get(10).toString());
        assertEquals("KING of SPADES[pickable by user]", testDealer.getUser().getHandCards().get(11).toString());
        assertEquals("ACE of SPADES[pickable by user]", testDealer.getUser().getHandCards().get(12).toString());
    }

        /* NO PASSING OF CARDS FOR FOURTH ROUND */
    @Test
    void pass3CardsRound4(){
        Dealer testDealer = new Dealer();
        testDealer.initDealer();
        testDealer.setRoundCount(4);
   
        List<Card> heartHand, spadeHand, clubHand, diamondHand,userPassedCards;

        heartHand = new ArrayList<Card>();
        spadeHand = new ArrayList<Card>();
        clubHand = new ArrayList<Card>();
        diamondHand = new ArrayList<Card>();
        userPassedCards = new ArrayList<Card>();
        
        userPassedCards.add(new Card(Suit.DIAMONDS, Rank.ACE, true));
        userPassedCards.add(new Card(Suit.DIAMONDS, Rank.KING, true));
        userPassedCards.add(new Card(Suit.DIAMONDS, Rank.QUEEN, true));

        
        for(Card c:userPassedCards) {
            c.setOwnerOfCard("user");
        }

        for (Rank rank : Rank.values()) {
            heartHand.add(new Card(Suit.HEARTS, rank, true));
        }

        for (Rank rank : Rank.values()) {
            spadeHand.add(new Card(Suit.SPADES, rank, true));
        }

        for (Rank rank : Rank.values()) {
            clubHand.add(new Card(Suit.CLUBS, rank, true));
        }

        for (Rank rank : Rank.values()) {
            diamondHand.add(new Card(Suit.DIAMONDS, rank, true));
        }
        
        testDealer.getBotA().fillHandCards(heartHand);
        testDealer.getBotB().fillHandCards(spadeHand);
        testDealer.getBotC().fillHandCards(clubHand);
        testDealer.getUser().fillHandCards(diamondHand);

        testDealer.distributePassedCards(userPassedCards);

        assertEquals(13, testDealer.getUserHandCards().size());
        assertEquals(13, testDealer.getBotA().getHandCards().size());
        assertEquals(13, testDealer.getBotB().getHandCards().size());
        assertEquals(13, testDealer.getBotC().getHandCards().size());

        assertEquals("TWO of HEARTS[pickable by botA]", testDealer.getBotA().getHandCards().get(0).toString());
        assertEquals("THREE of HEARTS[pickable by botA]", testDealer.getBotA().getHandCards().get(1).toString());
        assertEquals("FOUR of HEARTS[pickable by botA]", testDealer.getBotA().getHandCards().get(2).toString());

        assertEquals("TWO of SPADES[pickable by botB]", testDealer.getBotB().getHandCards().get(0).toString());
        assertEquals("THREE of SPADES[pickable by botB]", testDealer.getBotB().getHandCards().get(1).toString());
        assertEquals("FOUR of SPADES[pickable by botB]", testDealer.getBotB().getHandCards().get(2).toString());

        assertEquals("QUEEN of CLUBS[pickable by botC]", testDealer.getBotC().getHandCards().get(10).toString());
        assertEquals("KING of CLUBS[pickable by botC]", testDealer.getBotC().getHandCards().get(11).toString());
        assertEquals("ACE of CLUBS[pickable by botC]", testDealer.getBotC().getHandCards().get(12).toString());

        assertEquals("QUEEN of DIAMONDS[pickable by user]", testDealer.getUser().getHandCards().get(10).toString());
        assertEquals("KING of DIAMONDS[pickable by user]", testDealer.getUser().getHandCards().get(11).toString());
        assertEquals("ACE of DIAMONDS[pickable by user]", testDealer.getUser().getHandCards().get(12).toString());
    }


            /* TEST EDGE CASE- ROUND 99 NO PASSING OF CARDS FOR FOURTH ROUND */
    @Test
    void pass3CardsRound99(){
        Dealer testDealer = new Dealer();
        testDealer.initDealer();
        testDealer.setRoundCount(99);
   
        List<Card> heartHand, spadeHand, clubHand, diamondHand,userPassedCards;

        heartHand = new ArrayList<Card>();
        spadeHand = new ArrayList<Card>();
        clubHand = new ArrayList<Card>();
        diamondHand = new ArrayList<Card>();
        userPassedCards = new ArrayList<Card>();
        
        userPassedCards.add(new Card(Suit.DIAMONDS, Rank.ACE, true));
        userPassedCards.add(new Card(Suit.DIAMONDS, Rank.KING, true));
        userPassedCards.add(new Card(Suit.DIAMONDS, Rank.QUEEN, true));

        
        for(Card c:userPassedCards) {
            c.setOwnerOfCard("user");
        }

        for (Rank rank : Rank.values()) {
            heartHand.add(new Card(Suit.HEARTS, rank, true));
        }

        for (Rank rank : Rank.values()) {
            spadeHand.add(new Card(Suit.SPADES, rank, true));
        }

        for (Rank rank : Rank.values()) {
            clubHand.add(new Card(Suit.CLUBS, rank, true));
        }

        for (Rank rank : Rank.values()) {
            diamondHand.add(new Card(Suit.DIAMONDS, rank, true));
        }
        
        testDealer.getBotA().fillHandCards(heartHand);
        testDealer.getBotB().fillHandCards(spadeHand);
        testDealer.getBotC().fillHandCards(clubHand);
        testDealer.getUser().fillHandCards(diamondHand);

        testDealer.distributePassedCards(userPassedCards);

        assertEquals(13, testDealer.getUserHandCards().size());
        assertEquals(13, testDealer.getBotA().getHandCards().size());
        assertEquals(13, testDealer.getBotB().getHandCards().size());
        assertEquals(13, testDealer.getBotC().getHandCards().size());

        assertEquals("QUEEN of CLUBS[pickable by botA]", testDealer.getBotA().getHandCards().get(0).toString());
        assertEquals("KING of CLUBS[pickable by botA]", testDealer.getBotA().getHandCards().get(1).toString());
        assertEquals("ACE of CLUBS[pickable by botA]", testDealer.getBotA().getHandCards().get(2).toString());

        assertEquals("QUEEN of DIAMONDS[pickable by botB]", testDealer.getBotB().getHandCards().get(0).toString());
        assertEquals("KING of DIAMONDS[pickable by botB]", testDealer.getBotB().getHandCards().get(1).toString());
        assertEquals("ACE of DIAMONDS[pickable by botB]", testDealer.getBotB().getHandCards().get(2).toString());

        assertEquals("QUEEN of HEARTS[pickable by botC]", testDealer.getBotC().getHandCards().get(10).toString());
        assertEquals("KING of HEARTS[pickable by botC]", testDealer.getBotC().getHandCards().get(11).toString());
        assertEquals("ACE of HEARTS[pickable by botC]", testDealer.getBotC().getHandCards().get(12).toString());

        assertEquals("QUEEN of SPADES[pickable by user]", testDealer.getUser().getHandCards().get(10).toString());
        assertEquals("KING of SPADES[pickable by user]", testDealer.getUser().getHandCards().get(11).toString());
        assertEquals("ACE of SPADES[pickable by user]", testDealer.getUser().getHandCards().get(12).toString());
    }

   
    @Test
    /*Test for a lowest score id */
    void testForGettingLowestScorePlayerId() {
 
         Dealer testDealer = new Dealer();
         testDealer.initDealer();
   
 
         testDealer.addTotalScore("user",26);
         testDealer.addTotalScore("botA", 26);
         testDealer.addTotalScore("botB", 10);
         testDealer.addTotalScore("botC", 5);
 
         assertEquals("botC", testDealer.getLowestScorePlayerId().get(0));
         
    }   

    @Test
    /*Test for a lowest score id */
    void testForMultipleLowestScorePlayerId() {
 
         Dealer testDealer = new Dealer();
         testDealer.initDealer();
   
 
         testDealer.addTotalScore("user",26);
         testDealer.addTotalScore("botA", 26);
         testDealer.addTotalScore("botB", 5);
         testDealer.addTotalScore("botC", 5);
 
         assertEquals("botC", testDealer.getLowestScorePlayerId().get(0));
         assertEquals("botB", testDealer.getLowestScorePlayerId().get(1));
         
    }  


}