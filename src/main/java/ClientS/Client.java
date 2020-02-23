/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClientS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author sofieamalielandt
 */
public class Client {

    private final Socket socket;
    private final BufferedReader reader;
    private final OutputStreamWriter writer;
    private final PrintWriter out;
    private final Scanner in;

    public Client(Socket socket) throws IOException {
        this.socket = socket;
        this.reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        this.writer = new OutputStreamWriter(this.socket.getOutputStream());
        this.out = new PrintWriter(writer);
        this.in = new Scanner(System.in);
    }

    public void read() throws IOException {
        String response;
        while ((response = reader.readLine()) != null) {
            if (response.equals(".")) {
                break;
            }

            if (response.equals("GAME OVER!")) {
                System.out.println(response);
                System.out.println(reader.readLine());
                this.close();
            }

            System.out.println(response);
        }
    }

    public void write() {
        String str = in.nextLine();
        out.println(str);
        out.flush();
    }

    private void close() throws IOException {
        this.reader.close();
        this.writer.close();
        this.out.close();
        this.socket.close();
    }

}
