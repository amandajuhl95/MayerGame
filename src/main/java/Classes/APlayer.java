/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author aamandajuhl
 */
public abstract class APlayer {

    private String name;
    private int lifes;
    private final Socket socket;
    private final BufferedReader in;
    private final OutputStreamWriter writer;
    private PrintWriter out;

    public APlayer(Socket socket) throws IOException {
        this.lifes = 6;
        this.socket = socket;
        this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        this.writer = new OutputStreamWriter(this.socket.getOutputStream());
        this.out = new PrintWriter(this.writer);
    }

    public APlayer() throws IOException {
        this.lifes = 6;
        this.socket = null;
        this.in = null;
        this.writer = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLifes() {
        return lifes;
    }

    public void lostLife() {
        lifes--;
    }

    public Socket getSocket() {
        return socket;
    }

    public String in() throws IOException {
        return in.readLine();
    }

    public void out(String str) {
        out.println(str);
        out.flush();
    }

    public void close() throws IOException {
        socket.getInputStream().close();
        socket.getOutputStream().close();
        socket.close();
    }

    public abstract int taketurn(Dicecup cup, APlayer previous) throws IOException;

    public boolean rollcheck(int told, int newTold) {
        if (newTold > 66 || newTold < 11) {
            return true;
        }
        if (newTold == 21) {
            return false;
        }
        if (told == 21) {
            return true;
        }
        if (newTold == 31) {
            return false;
        }
        if (newTold != 0 && newTold % 11 == 0) {
            if (told % 11 != 0) {
                return false;
            }
        }
        if (told != 0 && told % 11 == 0) {
            if (newTold % 11 != 0) {
                return true;
            }

            return newTold < told;

        }
        return newTold < told;

    }
}
