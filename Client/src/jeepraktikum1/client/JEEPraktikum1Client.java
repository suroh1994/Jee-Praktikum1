/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeepraktikum1.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import jeepraktikum1.transport.Request;
import jeepraktikum1.transport.RequestIF;
import jeepraktikum1.transport.ResponseIF;
import jeepraktikum1.client.transport.ProtocolParser;

/**
 *
 * @author Sebastian
 */
public class JEEPraktikum1Client {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please provide a server ip:");
        String ip = "0.0.0.0";
        int port = 4567;
        final String input = scanner.nextLine();
        if (!input.isEmpty()) {
            ip = input;
            System.out.println("Please provide a server port:");
            port = scanner.nextInt();
        }

        System.out.printf("connecting to %s:%d...\n", ip, port);
        try {
            Socket socket = new Socket(InetAddress.getByName(ip), port);
            System.out.println("Connected!");
            OutputStream outStream = socket.getOutputStream();

            objectTest(outStream);

            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            try {
                List<Serializable> response = ProtocolParser.parseResponse(ois.readObject());
                System.err.println(response);
            } catch (Exception e) {
                e.printStackTrace();
            }

            socket.close();
        } catch (Exception ioe) {
            System.err.println("failed to open socket");
            ioe.printStackTrace();
        }

    }

    private static void objectTest(OutputStream mOutStream) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(mOutStream);
        List<Serializable> tmp = new ArrayList<>();
        tmp.add(new Integer(12));
        tmp.add(new Integer(2));
        List<Serializable> req = ProtocolParser.makeRequest("getRow", tmp);

        oos.writeObject(req);
    }
}
