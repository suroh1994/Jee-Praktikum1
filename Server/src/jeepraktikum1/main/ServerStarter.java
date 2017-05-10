package jeepraktikum1.main;

import jeepraktikum1.server.ServiceServer;

/**
 * Starts the server providing the services.
 * 
 * @author Lars
 *
 */
public class ServerStarter {

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
                        
            final Thread serverThread = new Thread( new ServiceServer(port) );
            serverThread.setName(ServiceServer.class.getSimpleName());
            serverThread.start();
            serverThread.join();

        } catch (Exception e) {
        	System.err.println("Server initialization failed! Server was not started!");
        	e.printStackTrace();
        }
    }
}
