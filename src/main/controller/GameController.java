package main.controller;

import main.model.*;
import main.constant.*;
import main.view.GameUI;
import java.util.*;

/**
 * This contains the GameController class which represents the connection between the model and view in MVC framework. The controller transfer data mainly
 * between the Dealer model and the GameUI view. 
 * Dealer model then interacts with the other models. 
 * This is just like a real cards game where the dealer is the person handling all middle-man details
 * @version 5th Apr 2019
 * @author Amos Lam, Chang Ernrae, Lim Zhaowei
 */
public class GameController {
    private Dealer dealer;
    private GameUI gameUI;
 
    /**
     * Create a GameController with a specific dealer and gameUI
     * @param  dealer The dealer instance for Hearts Game
     * @param  gameUI The UI instance for Hearts Game
     */
    public GameController(Dealer dealer,GameUI gameUI) {
        this.dealer = dealer;
        this.gameUI = gameUI;
    }


    /**
     * Starts the game by initializing the instance fields in dealer.
     * This method triggers a game loop that ends only when there is a loser in the game ( scores over 100 points )
     * @see Dealer
     * @see GameUI
     */
    public void startGame() {

        gameUI.printWelcome();
        dealer.initDealer();

        while(!dealer.hasAWinner()){
            // start round 
            newRound();
            
            /* begin passing and update all players' handCards with changes */
            initiatePassing();

            /* After passing of cards */
            Player user = dealer.getUser();
            System.out.println();
            
            /* dealer tracks which player should start first. The one with 3 clubs */
            dealer.setPlayerStarter();

            /* set the trick to be first-> since start of new round */
            dealer.setNotFirstTrick(false);
            
             
            while(!dealer.isEndOfThirteenTricks()) {
                gameUI.printNewTrick();  
                while(!dealer.isEndOfTrick()) {

                    /* can be human/bot */
                    Player player = dealer.getTrickPlayer();
                    player.updateCardPickability(dealer.getPlayedCardsForTrick(),
                                                dealer.getIsNotFirstTrick(),
                                                dealer.getIsHeartsBroken());
                    
                    /* determine if player is bot or human. If BOT, 
                    trigger auto-selection. 
                    If is human user, trigger UI to query for selection */
                    if(player instanceof Bot) {
                        Bot botPlayer = (Bot)player;
                        Card largestPlayedCard = dealer.getLargestCardFromTrick();
                        int numberOfPlayedCards = dealer.getPlayedCardsForTrick().size();

                        /* get Bot to make play card decision */
                        Card cardToPlay = botPlayer.playCard(largestPlayedCard, numberOfPlayedCards);
                        dealer.addToPlayedCards(cardToPlay);
                        gameUI.displayBotPlayedCard(cardToPlay);                    

                    }

                    /* player choose card to play */
                    else {
                        Card userPlayedCard = gameUI.printSelectPlayingCard(player.getHandCards(), dealer.getPlayedCardsForTrick());
                        player.playCard(userPlayedCard);
                        dealer.addToPlayedCards(userPlayedCard);
                    }


                    /* if this is end of first trick, then set firstTrick to false */
                    if (!dealer.getIsNotFirstTrick()) {
                        dealer.setNotFirstTrick(true);
                    }
                    
                }

                //end of trick, calculate score -> need a method for that
                //end of trick, assign NextPlayer = winner. 

                String ownerOfWinnerCard = dealer.getWinnerOfTrick(dealer.getPlayedCardsForTrick());
                dealer.setNextPlayer(ownerOfWinnerCard);

                
                int scoreAccumulatedInTrick = dealer.calculatePlayedCardsPoints(dealer.getPlayedCardsForTrick());
                dealer.addTotalScore(ownerOfWinnerCard, scoreAccumulatedInTrick);
                dealer.addRoundScore(ownerOfWinnerCard, scoreAccumulatedInTrick);

                /*At the end of the round, while winner of the trick is ... , someone has shot the moon
                display total score */
                String moonShooter = dealer.getMoonShooter();
                if(moonShooter!=null) {
                  dealer.handleMoonShooter(moonShooter);
                  gameUI.displayMoonShooter(moonShooter,ownerOfWinnerCard);
                  
                }

                else {
                    /* gameUI announces winner */
                    gameUI.displayTrickWinner(ownerOfWinnerCard,scoreAccumulatedInTrick);
                }
                
                
                gameUI.displayPlayerCumulativeScore(dealer);
                /* clear played cards */
                dealer.clearPlayedCards();
            }

            dealer.setRoundCount(dealer.getRoundCount() + 1);
       
 
        }

        /* if exit hasAWinner loop, declare winner for the game */
        ArrayList<String> winners = dealer.getLowestScorePlayerId();

        gameUI.displayGameWinners(winners);

    }

    /**
     * Starts new round(one round contains 13 tricks)
     */
    private void newRound() {
        dealer.newRound();
        gameUI.printUserHandCards(dealer.getUserHandCards());
    }


    /**
     * Start the passing and update all players' handCards with changes .
     */
    private void initiatePassing() {
        /* empty list when no passing is required */
        List<Card> cards = gameUI.printSelectPassingCards(dealer.getUserHandCards(),dealer.getRoundCount());
        dealer.distributePassedCards(cards);
        gameUI.printUserHandCards(dealer.getUserHandCards());
    }

}