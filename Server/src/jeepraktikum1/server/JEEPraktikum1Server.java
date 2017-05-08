/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeepraktikum1.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import jeepraktikum1.transport.RequestIF;
import jeepraktikum1.transport.Response;

/**
 *
 * @author Sebastian
 */
public class JEEPraktikum1Server {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            //register mysql connector
            Class.forName("com.mysql.jdbc.Driver").newInstance();

            final ServerSocket sS = new ServerSocket(4567, 100);
            System.out.printf("Server IP: %s; Server Port: %s \n",
                    sS.getInetAddress().getHostAddress().toString(),
                    sS.getLocalPort());

            while (true) {
                final Socket mSocket = sS.accept();
                System.out.println("client connected");

                final ObjectInputStream ois = new ObjectInputStream( mSocket.getInputStream() );

                final Object receivedObject = ois.readObject();
                Object response;
                if (receivedObject instanceof ArrayList<?>) {
                    response = ReflectionHelper.doReflection((ArrayList<Serializable>) receivedObject);
                } else {
                    response = new Exception("Invalid request! Handling is not supported on this server!");
                }

                final ObjectOutputStream oos = new ObjectOutputStream( mSocket.getOutputStream() );
                oos.writeObject(response);

                mSocket.close();
            }

        } catch (IOException ioe) {
            System.err.printf("Error opening socket: %s", ioe.getMessage());
            ioe.printStackTrace();
        } catch (Exception e) {
            System.err.println("Whoops...");
            e.printStackTrace();
        }
    }
    
}
