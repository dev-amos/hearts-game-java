
package main.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;
import main.constant.*;

/**
 * This class simulates a real dealer who deals the cards and keeps track of the players' scores
 * @version 5th Apr 2019
 * @author Amos Lam, Chang Ernrae, Lim Zhaowei
 */
public class Dealer{

    private Human user;
    private Bot botA, botB, botC;

    /* cumulative score throughout the many rounds till 100 points are accumulated */
    private int userTotalScore, botATotalScore, botBTotalScore, botCTotalScore;

    /* one round = 13 tricks with 4 cards in each trick played by the players */
    private int userRoundScore, botARoundScore, botBRoundScore, botCRoundScore;

    /* dealer will keep track how many rounds have passed to decide which direction should the passing of 3 cards take place */
    private int roundCount;

    /* dealer will keep track of all players' scores */
    public static final int goalScore = 100;

    /* track whose turn is it to play card 
       either user/botA/botB/botC */
    private String nextPlayer;


    /* keep track of played cards */
    private List<Card> playedCardsForTrick = new ArrayList<Card>();


    /* Instance fields that are needed to update players on rules */
    private boolean isHeartsBroken;
    private boolean isNotFirstTrick;

    /**
    * Instantiates 3 bots and 1 user to play the HeartsApplication. 
    * Total score for all players is also created at 0 value.
    * @see            Human
    * @see            Bot           
    */
    public void initDealer() {
        user = new Human();
        botA = new Bot();
        botB = new Bot();
        botC = new Bot();

        user.setId("user");
        botA.setId("botA");
        botB.setId("botB");
        botC.setId("botC");

        userTotalScore = 0;
        botATotalScore = 0;
        botBTotalScore = 0;
        botCTotalScore = 0;

        roundCount = 1;
    }

    /**
    * Resets new round score for all players after all 13 cards have been played and a loser has not been announced yet.
    * Players receive new set of 13 random cards again.
    * @see Deck        
    * @see Card
    * @see Player
    */
    public void newRound(){
    
        /* reset round scores. */
        userRoundScore = 0;
        botARoundScore = 0;
        botBRoundScore = 0;
        botCRoundScore = 0;

        /* start new deck, distribute to the players */
        Deck deck = new Deck();

        /* Returns a Stack object that contains Cards */
        Stack<Card> shuffledCards = deck.getDeckCards();

        /* Creates a empty hand for each players */
        List<Card> userHandCards = new ArrayList<Card>();
        List<Card> botAHandCards = new ArrayList<Card>();
        List<Card> botBHandCards = new ArrayList<Card>();
        List<Card> botCHandCards = new ArrayList<Card>();

        while (!shuffledCards.empty()) {
            Card userCard = shuffledCards.pop();
            userCard.setOwnerOfCard("user");
            userHandCards.add(userCard);

            Card botACard = shuffledCards.pop();
            botACard.setOwnerOfCard("botA");
            botAHandCards.add(botACard);

            Card botBCard = shuffledCards.pop();
            botBCard.setOwnerOfCard("botB");
            botBHandCards.add(botBCard);

            Card botCCard = shuffledCards.pop();
            botCCard.setOwnerOfCard("botC");
            botCHandCards.add(botCCard);
        }

        /* Removes all existing cards in hand and distributed hand to the players */
        user.fillHandCards(userHandCards);
        botA.fillHandCards(botAHandCards);
        botB.fillHandCards(botBHandCards);
        botC.fillHandCards(botCHandCards);

   }

    /**
    * Gets user's list of hand cards
    * @return user's current hand cards
    * @see Card
    */
    public List<Card> getUserHandCards() {
        return user.getHandCards();
    }

    /**
    * Sets the round count. (Each round consists of 13 tricks)
    * @param roundCount  The round count
    */
    public void setRoundCount(int roundCount){
        this.roundCount = roundCount;
    }

    /**
    * Gets the current round count
    * @return current round count
    */
    public int getRoundCount(){
        return this.roundCount;
    }

    /**
    * Sets id of next player to play in the trick
    * @param id  id of next player(user/botA/botB/botC)
    */
    public void setNextPlayer(String id){
        this.nextPlayer = id;
    }

    /**
    * Gets the id of next player to play in the trick
    * @return next player id
    */
    public String getNextPlayer(){
        return this.nextPlayer;
    }

    /**
    * Checks if a player has scored over 100 points and can declare lowest scorer as winner
    * @return true if the game has a winner, false otherwise
    */
    public boolean hasAWinner(){
        if(userTotalScore >= goalScore){
            return true;
        }

        else if(botATotalScore >= goalScore){
            return true;
        }

        else if(botBTotalScore >= goalScore){
            return true;
        }
        else if(botCTotalScore >= goalScore){
            return true;
        }

        return false;
    }

    /**
    * Get the id of players with the lowest score
    * There could be more than one player with minimum score when one of the players reach more than 100 points
    * @return List of player ids with lowest score
    */
    public ArrayList<String> getLowestScorePlayerId(){

        ArrayList<String> targetId = new ArrayList<>();
        HashMap<String,Integer> playerDetails = new HashMap<>();

        playerDetails.put("user",userTotalScore);
        playerDetails.put("botA",botATotalScore);
        playerDetails.put("botB",botBTotalScore);
        playerDetails.put("botC",botCTotalScore);
        
        targetId.add("user");

        for(String id:playerDetails.keySet()) {
            if(id!="user") {
                int minScore = playerDetails.get(targetId.get(0));
                if(minScore > playerDetails.get(id)) {
                    minScore = playerDetails.get(id);
                    targetId.clear();
                    targetId.add(id);
                }

                else if(minScore == playerDetails.get(id)) {
                    targetId.add(id);
                }
            }
        }

        return targetId;
    }

    /**
    * After receiving user' chosen cards to pass, this method simulates a dealer
    * passing the cards for all the players to each other. 
    * Bots will have their passing of cards automated 
    * @param userPassCards  list of {@link Card}(s) selected by user to pass
    * @see Bot
    */
    public void distributePassedCards(List<Card> userPassCards) {
        int roundCount = getRoundCount() % 4;

        if(roundCount != 0) {
            List<Card> botAPassCards, botBPassCards, botCPassCards;

            botAPassCards = new ArrayList<Card>();
            botBPassCards = new ArrayList<Card>();
            botCPassCards = new ArrayList<Card>();

            /* Retrieve bot passed cards */
            botAPassCards.addAll(botA.passThreeCards());
            botBPassCards.addAll(botB.passThreeCards());
            botCPassCards.addAll(botC.passThreeCards());

            /* Remove the selected passing cards from bots and players' handcards. 
               Do not need to check if true/false because our frontend ensures users pass cards they possess only 
            */
            user.removeHand(userPassCards);
            botA.removeHand(botAPassCards);
            botB.removeHand(botBPassCards);
            botC.removeHand(botCPassCards);
 
            /* 
            Carry out passing of cards
            roundCount: 0= no need pass, 1= pass left, 2= pass right, 3= pass opposite.
            */
            if (roundCount == 1) {
                /* left passing */
                user.addCardsToHand(botCPassCards);
                botA.addCardsToHand(userPassCards);
                botB.addCardsToHand(botAPassCards);
                botC.addCardsToHand(botBPassCards);
            }
            else if (roundCount == 2) {
                /* right passing */
                user.addCardsToHand(botAPassCards);
                botA.addCardsToHand(botBPassCards);
                botB.addCardsToHand(botCPassCards);
                botC.addCardsToHand(userPassCards);
            } 
            else if (roundCount == 3) {
                /* opposite passing */
                user.addCardsToHand(botBPassCards);
                botA.addCardsToHand(botCPassCards);
                botB.addCardsToHand(userPassCards);
                botC.addCardsToHand(botAPassCards);
            } 
        }

        
    }

    /**
    * Set next player to start a round
    * Determined by checking if a {@link Player} holds onto 2 of clubs
    */
    public void setPlayerStarter() {
        if (user.hasTwoOfClubs()) {
            nextPlayer = user.getId();
        }
        else if (botA.hasTwoOfClubs()) {
            nextPlayer = botA.getId();
        } else if (botB.hasTwoOfClubs()) {
            nextPlayer = botB.getId();
        } else {
            nextPlayer = botC.getId();
        }
    }

    /**
    * Checks if 13 tricks have been played and a round has ended
    * @return true if user has no more cards to play, false otherwise
    */
    public boolean isEndOfThirteenTricks() {
        if (user.getHandCards().size() == 0) {
            return true;
        }
        return false;
    }

    /**
    * Checks if a trick has ended according to the number of played cards in the trick
    * @return true if trick has four cards played, false otherwise    
    */
    public boolean isEndOfTrick() {
        if (playedCardsForTrick.size() < 4) {
            return false;
        }

        return true;
    }

    /**
    * Clear the cards played in the trick
    */
    public void clearPlayedCards() {
        playedCardsForTrick.clear();
    }

    /**
    * Gets a list of played {@link Card}(s) in the current trick
    * @return Cards that have been played in the trick
    */
    public List<Card> getPlayedCardsForTrick() {
        return playedCardsForTrick;
    }

    /**
    * Adds card played by player to list of played cards
    * @param card  {@link Card} selected by player for the trick
    */
    public void addToPlayedCards(Card card) {
        /*
          If the player discards a point card, then the constraint of leading a round with a ♥ card is broken.
          This means if played hearts or queen of Spade, set hearts broken 
          Queen of spades card value = 36
        */
        if(card.getSuit() == Suit.HEARTS || card.getCardValue() == 36) {
            setHeartBroken();
        }
        playedCardsForTrick.add(card);
    }

    /**
    * Retrieves largest Card based on leading suit from the played cards in a trick
    * @return  card that has largest rank following leading suit. If trick has no cards, returns null
    */
    public Card getLargestCardFromTrick() {
        int size = playedCardsForTrick.size();

        Card largest = null;
        if(size == 0) {
            return largest;
        }
        else if(size == 1) {
    
            return playedCardsForTrick.get(0);
        }

        largest = playedCardsForTrick.get(0);

        /* if more than 1 card, check for largest according to lead suit */
        for(int i=1; i<size; i++) {
            Card card = playedCardsForTrick.get(i);
            if((largest.getSuit() == card.getSuit()) && (largest.getCardValue() < card.getCardValue())) {
                largest = card;
            }
        }

        return largest;
    }

    /**
    * Gets the player whose turn to play a card
    * @return player whose turn to play a card
    */
    public Player getTrickPlayer() {
        if (nextPlayer.equals(user.getId())) {
            nextPlayer = botA.getId();
            return user;
        }

        else if (nextPlayer.equals(botA.getId())) {
            nextPlayer = botB.getId();
            return botA;
        }

        else if (nextPlayer.equals(botB.getId())) {
            nextPlayer = botC.getId();
            return botB;
        }

        else {
            nextPlayer = user.getId();
            return botC;
        }

    }

    /**
    * Gets the boolean value of whether hearts has been broken
    * @return true if hearts has been broken, false if otherwise
    */
    public boolean getIsHeartsBroken() {
        return isHeartsBroken;
    }  

    /**
    * Gets the boolean value of whether current trick is not the first trick of a round
    * @return true if is not first trick, false if otherwise
    */
    public boolean getIsNotFirstTrick() {
        return isNotFirstTrick;
    }

    /**
    * Sets hearts to be broken
    */
    public void setHeartBroken() {
        isHeartsBroken = true;
    }

    /**
    * Sets the boolean value of whether current trick is the first trick in the current round
    * @param notFirst  true if not first trick, false otherwise
    */
    public void setNotFirstTrick(boolean notFirst) {
        isNotFirstTrick = notFirst;
    }

    /**
    * Calculates accumulated points of a list of cards
    * @param   cards  List of {@link Card}(s) 
    * @return  sum of all the point cards in the parameter list
    */
    public int calculatePlayedCardsPoints(List<Card> cards) {
        int score = 0;

        for(Card card:cards) {
            if(card.getSuit() == Suit.HEARTS) {
                score++;
            }
            else if(card.getSuit() == Suit.SPADES 
            && card.getRank() == Rank.QUEEN) {
                score += 13;
            }
        }

        return score;
    }

    /**
    * Gets player id whose card has highest value from a list of cards
    * @param   cards  List of {@link Card}(s) 
    * @return  The player id of the card which has the highest value in the parameter list
    * @see     Player
    */
    public String getWinnerOfTrick(List<Card> cards) {
        /* return null if is not end of trick */
        if(cards.size() != 4) {
            return null;
        }
        
        Card largestCard = getLargestCardFromTrick();
        return largestCard.getOwnerOfCard();
    }

    /**
    * Retrieves the id of player who scores 26 points in a round(13 tricks)
    * @return  player id who shot the moon.  Returns null if is not end of trick or no one shot the moon
    */
    public String getMoonShooter() {
        if(isEndOfThirteenTricks()){
            //return id
            if(getRoundScore("user") == 26) {
                return "user";
            }

            else if(getRoundScore("botA") == 26) {
                return "botA";
            }

            else if(getRoundScore("botB") == 26) {
                return "botB";
            }

            else if(getRoundScore("botC") == 26) {
                return "botC";
            }
        }

        return null;
    }

    /**
    * Redistributes score according to "shoot the moon" rules
    * Penalises other players with additional score of 26
    * @param id  Id of player who shot the moon
    */
    public void handleMoonShooter(String id) {

        ArrayList<String> players = new ArrayList<>();
        players.add("users");
        players.add("botA");
        players.add("botB");
        players.add("botC");

        for(String pid:players) {
            if(!pid.equals(id)) {
                addTotalScore(pid,26);
            }
        }
        addTotalScore(id,-26);
        
    }

    /**
    * Retrieves one out of the 4 players(user/botA/botB/botC) based on their id
    * @param   id  id of player
    * @return  Player whose id matches the argument. Returns null if no matches
    */
    public Player getPlayer(String id) {
        if(id.equals("user")) {
            return user;
        }

        else if(id.equals("botA")) {
            return botA;
        }

        else if(id.equals("botB")) {
            return botB;
        }

        else if(id.equals("botC")) {
            return botC;
        }

        return null;
    }

    /**
    * Retrieves specified player score based on player id for current round
    * @param   id  Player id
    * @return  Round score for the player in the round
    */
    public int getRoundScore(String id) {
        if(id.equals("user")) {
            return userRoundScore;
        }

        else if(id.equals("botA")) {
            return botARoundScore;
        }

        else if(id.equals("botB")) {
            return botBRoundScore;
        }

        else if(id.equals("botC")) {
            return botCRoundScore;
        }

        return 0;
    }

    /**
    * Retrieves the cumulative score for specified player identified from  player id
    * @param   id  player id
    * @return  cumulative score for the entire game
    */
    public int getTotalScore(String id) {
        if(id.equals("user")) {
            return userTotalScore;
        }

        else if(id.equals("botA")) {
            return botATotalScore;
        }

        else if(id.equals("botB")) {
            return botBTotalScore;
        }

        else if(id.equals("botC")) {
            return botCTotalScore;
        }

        return 0;
    }

    /**
    * Adds to player's round score
    * @param id  player id
    * @param score  Earned score over a trick
    */
    public void addRoundScore(String id, int score) {
        if(id.equals("user")) {
            userRoundScore += score;
        }

        else if(id.equals("botA")) {
            botARoundScore += score;
        }

        else if(id.equals("botB")) {
            botBRoundScore += score;
        }

        else if(id.equals("botC")) {
            botCRoundScore += score;
        }
    }

    /**
    * Adds to player's total cumulative score
    * @param id  player id
    * @param score  Earned score over a trick   
    */
    public void addTotalScore(String id, int score) {
        if(id.equals("user")) {
            userTotalScore += score;
        }

        else if(id.equals("botA")) {
            botATotalScore += score;
        }

        else if(id.equals("botB")) {
            botBTotalScore += score;
        }

        else if(id.equals("botC")) {
            botCTotalScore += score;
        }

    }

    /**
    * Gets the user Dealer is serving in the Hearts Game
    * @return The user who is a {@link Human} object
    */
    public Human getUser() {
        return this.user;
    }

    /**
    * Gets botA which the Dealer is serving in the Hearts Game
    * @return botA who is a {@link Bot} object 
    */
    public Bot getBotA() {
        return this.botA;
    }

    /**
    * Gets botB which the Dealer is serving in the Hearts Game
    * @return botB who is a {@link Bot} object
    */
    public Bot getBotB() {
        return this.botB;
    }

    /**
    * Gets botC which the Dealer is serving in the Hearts Game
    * @return botC who is a {@link Bot} object 
    */
    public Bot getBotC() {
        return this.botC;
    }

}