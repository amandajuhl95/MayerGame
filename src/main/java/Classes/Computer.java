/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.io.IOException;
import java.util.Random;

/**
 *
 * @author aamandajuhl
 */
public class Computer extends APlayer {

    private Random random = new Random();

    public Computer() throws IOException {
    }

    private int generateTold() {
        int dice1 = random.nextInt(6) + 1;
        int dice2 = random.nextInt(6) + 1;
        int guess = 0;

        if (dice1 >= dice2) {
            guess = Integer.parseInt(dice1 + "" + dice2);
        } else {
            guess = Integer.parseInt(dice2 + "" + dice1);
        }

        return guess;

    }

    @Override
    public int taketurn(Dicecup cup, APlayer previous) {

        int told = cup.getTold();
        int rand = random.nextInt(3) + 1;

        if (told != 0 && previous != null) {
            GameControl.writeToAll(previous.getName() + " said " + cup.getTold() + "\n");
        }

        GameControl.writeToAll("It is " + this.getName() + "'s turn\n");

        if (rand == 1 && previous != null && told != 0) {

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

        int rolled = cup.roll();
        if (rollcheck(told, rolled)) {
            switch (told) {
                case 21:
                case 31:
                    cup.setTold(21);
                    break;
                case 66:
                    if (rand == 2) {
                        cup.setTold(21);

                    } else {
                        cup.setTold(31);
                    }
                    break;
                default:
                    int guess = generateTold();
                    while (rollcheck(told, guess)) {
                        guess = generateTold();
                    }
                    cup.setTold(guess);
                    break;
            }
            return 1;
        } else {
            cup.setTold(rolled);
            return 1;
        }

    }

}
