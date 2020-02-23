/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Amalie, Amanda og Benjamin
 */
public class Player extends APlayer {

    public Player(Socket socket) throws IOException {
        super(socket);
    }

    public int inInteger() throws IOException {
        while (true) {
            String sNum = in();
            try {
                return Integer.parseInt(sNum);

            } catch (NumberFormatException e) {
                out("Please enter an Integer! \n: \n.");
            }
        }
    }

    public int inInteger(int min, int max) throws IOException {
        if (max < min) {
            throw new IllegalArgumentException();
        }
        while (true) {
            int i = inInteger();
            if (i >= min && i <= max) {
                return i;
            }
            out("Please enter an Integer between " + min + " and " + max + "\n: \n.");
        }
    }

    public int choice(List<String> choices) throws IOException {
        if (choices == null || choices.isEmpty()) {
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < choices.size(); i++) {
            out("     " + (i + 1) + ": " + choices.get(i));
        }
        out("\nPlease choose: \n.");
        return inInteger(1, choices.size()) - 1;
    }

    @Override
    public int taketurn(Dicecup cup, APlayer previous) throws IOException {

        List<String> choices = new ArrayList();
        choices.add("Roll");

        if (cup.getTold() != 0 && previous != null) {
            choices.add("Flip cup");
            if (cup.getTold() == 21) {
                choices.add("Lose a life and reset game");
            }
            GameControl.writeToAll(previous.getName() + " said " + cup.getTold() + "\n");
        } else {
            this.out("Roll to start the game \n");
        }

        GameControl.writeToAll("It is " + this.getName() + "'s turn\n");
        this.out("You have " + this.getLifes() + " life(s)\n");

        int takenchoice = this.choice(choices);

        if (takenchoice == 0) {
            int rolled = cup.roll();
            this.out("You have rolled " + rolled);
            this.out("What do you want to tell the next player?\n.");
            int told = this.inInteger();

            while (rollcheck(cup.getTold(), told)) {

                this.out("Your answer must be higher than " + cup.getTold() + ". Please enter another value!\n.");
                told = this.inInteger();
            }

            cup.setTold(told);
            return 1;
        }
        if (takenchoice == 2) {
            this.lostLife();
            cup.setTold(0);
            GameControl.writeToAll(this.getName() + " gave up a life, and now has " + this.getLifes() + " life(s) left\n");
            return 0;
        }
        int current = cup.getCurrent();

        boolean winner = cup.flipCup(this, previous);

        if (winner) {
            GameControl.writeToAll(this.getName() + " got lucky. The actual value was: " + current + ".");
            GameControl.writeToAll(previous.getName() + " was lying and now has " + previous.getLifes() + " life(s) left.\n");
            return -1;
        } else {
            GameControl.writeToAll("Unfortunately " + previous.getName() + " is a decent person and " + this.getName() + " should be ashamed for distrusting " + previous.getName() + ".");
            GameControl.writeToAll(this.getName() + " now has " + this.getLifes() + " life(s) left.\n");
            return 0;
        }

    }

}
