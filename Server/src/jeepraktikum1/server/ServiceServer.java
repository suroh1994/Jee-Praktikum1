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
 * @author Lars
 */
public class ServiceServer implements Runnable {
	
	final int serverPort;
    
    /**
     * constructor for server
     * @param port
     */
    public ServiceServer(int port) {
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
