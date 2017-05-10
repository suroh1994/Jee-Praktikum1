/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeepraktikum1.server;

import java.io.IOException;
import java.net.ServerSocket;

/**
 *
 * @author Sebastian
 */
public class JEEPraktikum1Server implements Runnable {
	
	final int serverPort;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
        	int port = 4567;
        	// if diferent port is specified in arguments use it.
        	if(args != null && args.length == 1) {
        		port = Integer.parseInt(args[0]);
        	}
        	
            // register mysql connector
            Class.forName("com.mysql.jdbc.Driver").newInstance();
                        
            final Thread serverThread = new Thread( new JEEPraktikum1Server(port) );
            serverThread.start();
            serverThread.join();

        } catch (Exception e) {
        	System.err.println("Server initialization failed! Server was not started!");
        	e.printStackTrace();
        }
    }
    
    /**
     * constructor for server
     * @param port
     */
    public JEEPraktikum1Server(int port) {
    	serverPort = port;
    }

	@Override
	public void run() {
		ServerSocket sS = null;
        try {
            sS = new ServerSocket(4567, 100);
            System.out.printf("Server running at: %s:%s \n",
                    sS.getInetAddress().getHostAddress().toString(),
                    sS.getLocalPort());
            
            // server life cycle
            while (true) {
                new Thread(new SessionHandler( sS.accept() )).start();	// wait for client connections and pass them to new session handlers
            }

        } catch (IOException ioe) {
            System.err.printf("Serversocket threw unexpected " + ioe.getClass().getSimpleName() + ". Server stoped!");
            ioe.printStackTrace();
        } catch (Exception e) {
            System.err.println("Unhandled exception on server occured! Server stoped!");
            e.printStackTrace();
        } finally {
			if(sS != null) {
				try {
					sS.close();
				} catch(Exception e) {
					System.err.println("Failed to close server socket on server shutdown! Shuting down anyway.");
					e.printStackTrace();
				}
			}
		}
	}
    
}
