/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClientS;

import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author benja
 */
public class SocketClient {

    public static void main(String[] args) throws IOException {
        Socket s = new Socket("10.58.132.71", 49247);
        Client client = new Client(s);

        while (true) {
            client.read();
            client.write();
        }
    }
}
