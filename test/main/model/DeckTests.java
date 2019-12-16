package main.model;

import java.util.Collections;
import java.util.Stack;
import java.util.ArrayList;

import main.constant.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import main.constant.*;

class DeckTests {

    // @BeforeEach
    // public void setup() {
    //     Deck testDeck = new Deck();
    //     Stack<Card> shuffledCards = testDeck.getDeckCards();
    // }

    /*Test for deck size, 52 */
   @Test
   void testNumberofCards() {
       Deck testDeck = new Deck();
       Stack<Card> shuffledCards = testDeck.getDeckCards();
       assertEquals(52, shuffledCards.size());
   }

   /*Test that each suit has 13 cards*/
   @Test
   void checkCards() {
       int spades = 0;
       int diamonds = 0;
       int clubs = 0;
       int hearts = 0;

       Deck testDeck = new Deck();
       Stack<Card> shuffledCards = testDeck.getDeckCards();
       ArrayList<Card> spadeCards = new ArrayList <Card>();
       ArrayList<Card> heartCards = new ArrayList <Card>();
       ArrayList<Card> diamondCards = new ArrayList <Card>();
       ArrayList<Card> clubCards = new ArrayList <Card>();

       for(Card card:shuffledCards){
           if(card.getSuit() == Suit.SPADES){
               spades += 1;
               spadeCards.add(card);
           }
           if(card.getSuit() == Suit.CLUBS){
                clubs += 1;
                clubCards.add(card);
            }
            if(card.getSuit() == Suit.DIAMONDS){
                diamonds += 1;
                diamondCards.add(card);
            }
            if(card.getSuit() == Suit.HEARTS){
                hearts += 1;
                heartCards.add(card);
        }
       }
       assertEquals(13, spadeCards.size());
       assertEquals(13, heartCards.size());
       assertEquals(13, clubCards.size());
       assertEquals(13, diamondCards.size());
   }
}