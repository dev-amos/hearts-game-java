
package main;

import main.view.GameUI;
import main.model.Dealer;
import main.controller.GameController;

/**
 * This class is the entry point to start the HeartsApplication.
 * @version 5th Apr 2019
 * @author Amos Lam, Chang Ernrae, Lim Zhaowei
 */
public class HeartsApplication{

    /* This is the starting point of the program.
       The main method creates an instance of Dealer (model) and GameUI (view) class
       An instance of GameController class is then created with the model and view instance.
       Start of the hearts game is then triggered here */

    public static void main(String[] args){

        Dealer dealer = new Dealer();
        GameUI game = new GameUI();

        GameController gameController = new GameController(dealer, game);

        gameController.startGame();

    }
}
