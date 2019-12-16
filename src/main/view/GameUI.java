package main.view;

import main.controller.*;
import main.model.*;

import java.util.*;

/**
 * This class contains the interface display methods for the HeartsApplication. It acts as the view in MVC framework
 * @author Lam Guolun Amos
 * @version 1.0 Mar 01, 2019
 */

public class GameUI{

    /**
     * Prints welcome message for user in starting the Hearts game.
     */
    public void printWelcome(){
       System.out.println("================================================================================");
       System.out.println("   Welcome to the Hearts game.\n");
       System.out.println("   Instructions: ");
       System.out.println("       type \'Quit\' to exit game.");
       System.out.println("================================================================================\n");
    
       System.out.println("Our deal will distribute your cards shortly.");
    }

    /**
     * Prints error message.
     * @param msg  Error message
     */
    public void printError(String msg){
        System.out.println(msg);
        for(int i = 0; i<msg.length(); i++){
            System.out.print("-");
        }
        System.out.println();
        System.out.println();
    }

    /**
     * Prints start of new trick message
     */
    public void printNewTrick(){
        System.out.printf("============ Start of Trick ============%n");
        System.out.println();
    }

    /**
     * Prints the list of cards that the user owns
     * @param cards the user's list of hand cards
     */
    public void printUserHandCards(List<Card> cards){
        System.out.println("These are your cards:");
        System.out.println();

        for(int i=1; i<=cards.size(); i++){
            Card card = cards.get(i-1);
            System.out.printf("%2d. %s of %s\t", i, card.getRank(), card.getSuit());
            if(i%3==0){
                System.out.println();
            }
        }
        System.out.println();
    }

    /**
    * Prints hand cards for passing or playing 
    * @param cards  The user's list of hand {@link Card}(s)
    * @param pass if not true means display for playing cards. Otherwise, display for passing cards
    */
    public void displayCardsOption(List<Card> cards, boolean pass){
        System.out.println("== Select Cards by their numbering ==");
        System.out.println();
        for(int i=1; i<=cards.size(); i++){
            Card card = cards.get(i-1);
            
            if(!card.getPickable()){
                String playable = pass?"(Chosen)":"(Unpickable)";
                System.out.printf("%2d. %-5s of %-7s %-12s\t", i, card.getRank(), card.getSuit(), playable);
            }else{
                System.out.printf("%2d. %-5s of %-7s %-12s\t", i, card.getRank(), card.getSuit(),"");
            }
            
            if(i%3==0){
                System.out.println();
            }
            
        }
        System.out.println();
    }

    /**
    * Prints card selection interface for users to pick 3 passing cards
    * @param cards  the user's list of hand cards
    * @param roundCount  the nth round of the game
    * @return user's picked list of 3 cards for passing. Empty list if no passing is required
    */
    public List<Card> printSelectPassingCards(List<Card> cards, int roundCount){

        /* 0=nowhere, 1=left, 2=right, 3=opposite. If 0, no need to execute queries*/
        roundCount = roundCount % 4;

        List<Card> passingCards = new ArrayList<Card>();
        
        String passWhere = "No passes are required for this round";
        if(roundCount == 1){
            passWhere = "left player";
        }
        else if(roundCount==2){
            passWhere = "right player";
        }
        else if(roundCount == 3){
            passWhere = "opposite player";
        }
        else{
            System.out.println(passWhere);
            /* return empty list of cards */
            return passingCards;
        }

        System.out.printf("\nPlease pick 3 cards to pass to: %s%n",passWhere.toUpperCase());
        
        Scanner sc = new Scanner(System.in);
        
        int size = cards.size();
        int passCount = 1;

        while(passCount<=3){
            System.out.println();
            displayCardsOption(cards,true);

            /* this requires exception handling for non-int values */
            System.out.print("\nEnter Card "+ passCount +" Choice: ");
            String input = sc.nextLine();

            try{
                int choice = Integer.parseInt(input);
                if(choice == 0 || choice>size){
                    printError("Incorrect number input. Please try again");
                    //passCount -= 1;
                }
                else{
                    Card card = cards.get(choice-1);
    
                    if(card.getPickable()){
                        card.setPickable(false);
                        passingCards.add(card);
                        passCount = passingCards.size()+1;
                    }
    
                    else{
                        printError("You have already chose this card to pass. Please try again.");
                        //passCount -= 1;
                    }
    
                }

            }catch(NumberFormatException e){
                if(input.equalsIgnoreCase("Quit")){
                    System.exit(0);
                }
                printError("Incorrect number input. Please try again.");

            }
        }

        /* turn the cards in passingCards into pickable again */
        for(Card card: passingCards) {
            card.setPickable(true);
        }
        return passingCards;

    }

    /**
    * Prints card selection interface for user to pick a card to play for the trick. Returns the selected card
    * @param playerHandCards  the user's list of hand cards
    * @param playedCards  list of cards that have been played in the trick
    * @return selected card to play
    */
    public Card printSelectPlayingCard(List<Card> playerHandCards, List<Card> playedCards){
        String cardsPlayedInfo = "You are starting the turn!\n";

        if(playedCards.size() != 0) {
            cardsPlayedInfo = "Played cards: ";
            for(Card playCard: playedCards){
                cardsPlayedInfo+= String.format("%s",playCard.getRank()) + " of "+ String.format("%s",playCard.getSuit())+", ";
             }
             cardsPlayedInfo+="\n";
        }

        System.out.println(cardsPlayedInfo);
      
        int passCount = 1;
        int size = playerHandCards.size();
        Scanner sc = new Scanner(System.in);
        Card chosenCard = null;

        while(chosenCard == null){
            displayCardsOption(playerHandCards,false);

            System.out.print("Enter Card Choice: ");
            String input = sc.nextLine();
            try{
                int choice = Integer.parseInt(input);
                if(choice == 0 || choice>size){
                    printError("Incorrect number input. Please try again");
                }
                else{
                    Card card = playerHandCards.get(choice-1);
                    System.out.println();
                    if(card.getPickable()){
                        System.out.printf("   You have played %1s of %s.%n%n", card.getRank(), card.getSuit());
                        chosenCard = card;
                    }
    
                    else{
                        printError("Card is not pickable. Please try again.");
                    }
    
                }

            }catch(NumberFormatException e) {
                if(input.equalsIgnoreCase("Quit")){
                    System.exit(0);
                }
                printError("Incorrect number input. Please try again.");

            }
        }

        return chosenCard;
    
    }

    /**
    * Prints card played by BOT for a trick
    * @param card  The bot's selected {@link Card} to play in the trick
    */
    public void displayBotPlayedCard(Card card){
        String botName = card.getOwnerOfCard();
        System.out.printf("----- %s's turn to play card -----%n",botName);

        System.out.printf("   %s played %s of %s.%n\n",botName.toUpperCase(),card.getRank(), card.getSuit());
    }

    /**
    * Prints victory message for a trick
    * @param winnerId  The winner's id
    * @param scoreAwarded  The total score value accumulated from the point cards played in the trick
    */
    public void displayTrickWinner(String winnerId, int scoreAwarded){
        System.out.println("+-------------------------------------------------------+");
        System.out.printf("|%5s wins this trick. %d points was awarded! \t\t|%n",winnerId, scoreAwarded);
        System.out.println("+-------------------------------------------------------+");

    }

    /**
    * Prints victory message for winners of the game
    * @param winners  list of winner id who has scored the lowest score in the game after a player scores more than 100 points.
    */
    public void displayGameWinners(ArrayList<String> winners){
        System.out.println("+----------------------END-OF-GAME---------------------------------+");
        /* more than 1 player with same lowest score */
        int size = winners.size();
        if(size > 1) {
            String declareWinnersMsg = "";

            for(int i=0;i<size;i++) {
                String winnerId = winners.get(i);
                if(i==size-2) {
                    declareWinnersMsg += winnerId + " and ";
                }
                else if(i<size-1) {
                    declareWinnersMsg += winnerId + " , ";
                }
                else {
                    declareWinnersMsg += winnerId;
                }
            }

            System.out.printf("There is a tie between players %s%n",declareWinnersMsg);    
        }
        else {
            System.out.printf("%s is the winner!! congrats :) %n", winners.get(0));
        }
        
        System.out.println("+-------------------------------------------------------+");

    }

    /**
    * Prints message for moonshooting
    * @param shooterId  The moonshooter's id
    * @param winnerId  The trick winner's id
    */
    public void displayMoonShooter(String shooterId,String winnerId){
        System.out.println("+-------------------------------------------------------+");
        System.out.printf("|%5s wins the last trick of the ROUND!\t\t|%n",winnerId);
        System.out.println("+-------------------------------------------------------+");
        System.out.printf("|However, %5s SHOT THE MOON! 26 points are awarded to all other players \t\t|%n",shooterId);
        System.out.println("+-------------------------------------------------------+");
    }

    /**
    * Prints cumulative score of each player from the first round till current round
    * @param  dealer  {@link Dealer} of the game
    */
    public void displayPlayerCumulativeScore(Dealer dealer) {
        System.out.printf("|%1s%-17s|%1s%-17s|%1s%-16s|%n", " ","Player"," ", "Round", " ","Total Score");
        System.out.println("+-------------------------------------------------------+");
        System.out.printf("|%1s%-17s|%1s%-17s|%1s%-16s|%n", " ","User"," ", dealer.getRoundCount(), " ",dealer.getTotalScore("user"));
        System.out.printf("|%1s%-17s|%1s%-17s|%1s%-16s|%n", " ","botA"," ", dealer.getRoundCount(), " ",dealer.getTotalScore("botA"));
        System.out.printf("|%1s%-17s|%1s%-17s|%1s%-16s|%n", " ","botB"," ", dealer.getRoundCount(), " ",dealer.getTotalScore("botB"));
        System.out.printf("|%1s%-17s|%1s%-17s|%1s%-16s|%n", " ","botC"," ", dealer.getRoundCount(), " ",dealer.getTotalScore("botC"));
        System.out.println("+-------------------------------------------------------+");
        System.out.println();
    }

}