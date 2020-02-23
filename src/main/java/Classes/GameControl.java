/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aamandajuhl
 */
public class GameControl {

    private ServerSocket ss;

    private List<APlayer> players = new ArrayList();
    private static List<APlayer> allplayers = new ArrayList();
    private Dicecup cup;
    private int numOfCom = 1;

    public GameControl() {
        cup = new Dicecup();
    }

    public void game() throws IOException {

        APlayer previous = null;

        gameStart();

        writeToAll("You are playing against " + (players.size() - 1) + " other player(s)");

        while (true) {
            for (int i = 0; i < players.size(); i++) {

                if (i == -1) {
                    i = players.size() - 1;
                }

                if (players.get(i).getLifes() <= 0) {
                    writeToAll(players.get(i).getName() + " has died!\n");
                    players.remove(i);
                    writeToAll("There are " + players.size() + " player(s) left!\n");

                } else {
                    int next = players.get(i).taketurn(cup, previous);
                    previous = players.get(i);

                    i += next - 1;
                }

            }
            if (players.size() == 1) {
                gameFinished();
                break;
            }

        }
    }

    public static void writeToAll(String str) {
        for (APlayer player : allplayers) {
            if (player.getClass() == Player.class) {
                player.out(str);
            }
        }
    }

    private void gameStart() throws IOException {

        ss = new ServerSocket(49247);
        while (true) {
            System.out.println("Server is waiting for client request");
            Socket s = ss.accept();
            Player player = new Player(s);

            System.out.println("Client connected");

            player.out("Welcome to the Mayer Game!");
            player.out("Enter playername: \n.");

            player.setName(player.in());
            players.add(player);
            allplayers.add(player);
            player.out(player.getName() + " is ready to play");

            System.out.println(player.getName() + " is ready to play");

            String start = "";
            while (true) {
                player.out("Start game? (Y/N) \n.");
                start = player.in().toLowerCase();

                if ("y".equals(start) || "n".equals(start)) {
                    break;
                }

            }

            if ("y".equals(start)) {

                if (players.size() < 2) {
                    Computer com = new Computer();
                    com.setName("AI-2000." + numOfCom);
                    numOfCom++;
                    players.add(com);
                    player.out(com.getName() + " is ready to beat your carbon based ass");
                }
                break;
            }
        }
    }

    private void gameFinished() throws IOException {
        writeToAll("GAME OVER! \n");
        writeToAll("The winner is " + players.get(0).getName() + ", with " + players.get(0).getLifes() + " lifes left. \n.");

        for (APlayer player : players) {
            player.close();
        }
        ss.close();
    }

}
