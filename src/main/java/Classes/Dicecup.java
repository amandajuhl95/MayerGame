/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.util.Random;

/**
 *
 * @author Amalie, Amanda og Benjamin
 */
public class Dicecup {

    private int current;
    private int told;
    private static Random random = new Random();

    public Dicecup() {
    }

    public int roll() {
        int dice1 = random.nextInt(6) + 1;
        int dice2 = random.nextInt(6) + 1;

        if (dice1 >= dice2) {
            current = Integer.parseInt(dice1 + "" + dice2);
        } else {
            current = Integer.parseInt(dice2 + "" + dice1);
        }

        return current;

    }

    public boolean flipCup(APlayer currentPlayer, APlayer previousPlayer) {
        if (current == told) {
            currentPlayer.lostLife();

            if (current == 21) {
                currentPlayer.lostLife();
            }

            current = 0;
            told = 0;
            return false;

        }
        previousPlayer.lostLife();
        if (told == 21) {
            previousPlayer.lostLife();

        }

        current = 0;
        told = 0;
        return true;
    }

    public int getTold() {
        return told;
    }

    public void setTold(int told) {
        this.told = told;
    }

    public int getCurrent() {
        return current;
    }

}
