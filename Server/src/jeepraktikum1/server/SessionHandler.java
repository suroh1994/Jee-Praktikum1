package jeepraktikum1.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import jeepraktikum1.reflection.ServiceRequestHandler;

/**
 * handles a single RCP request.
 * 
 * @author Lars
 *
 */
public class SessionHandler implements Runnable {
	
	/**
	 * the connection socket.
	 */
	private final Socket socket;
	
	/**
	 * constructor.
	 * @param socket
	 */
	public SessionHandler(final Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		Exception occuredExp = null;
		try {
			if(socket == null) {
				throw new NullPointerException("Sessionhandler contained NULL socket!");
			}
			
            System.out.println("Session started for client at "  + socket.getRemoteSocketAddress() );
            
	        final Object receivedObject = readRequestObject();
	        final Object response = generateResponse(receivedObject);
	        sendResponse(response);
	        
            System.out.println("Session closed for client at "  + socket.getRemoteSocketAddress() );
            
		} catch(IOException ioe) {
			System.err.println("I/O Exception in session handler occured: ");
			ioe.printStackTrace();
			occuredExp = new Exception("An " + ioe.getClass().getSimpleName() + " occured on the server. Try again!");

		// occurs if objectinputstream can't parse received object.
		} catch(ClassNotFoundException cnfe) {
			System.out.println("Invalid request class type received!");
			cnfe.printStackTrace();
			occuredExp = new ClassNotFoundException("Your request/object could not be deserialized by the server.");
			
		// populate unhandled excpetion information to client
		} catch(Exception e) {
			System.err.println("Unhandled exception thrown in an instance of " + this.getClass().getSimpleName() + ". Try to send error notification to client!");
			e.printStackTrace();
			occuredExp = new Exception("Unhandled exception occured on server. Try again later!");
			
		} finally {
			
        	if(socket != null && !socket.isClosed()) {
        		
        		// try to inform client if an exception occured during message receiving/sending.
        		if(occuredExp != null) {
	    			try {
	    				sendResponse(occuredExp);
	    			} catch (Exception e1) {
	    				System.err.println("Failed to send error notification to client. Shuting down socket without notifying client now!");
	    				e1.printStackTrace();
	    			}
        		}
        		
    	        // try to close socket and its streams
		        try {
		        	socket.close();
				} catch (IOException e) {
					System.err.println("Failed to close socket in an " + this.getClass().getSimpleName() + " instance!");
					e.printStackTrace();
				}
        	}
		}
	}

	/**
	 * generates a response for the received object.
	 * @param receivedObject
	 * @return the response object or an Exception obejct if request is not processable.
	 */
	private Object generateResponse(final Object receivedObject) {
		Object response;
		// check request for validity (an object of type arraylist is expected)
		if (receivedObject instanceof ArrayList<?>) {
			response = ServiceRequestHandler.invokeRequestedServiceIfValid((ArrayList<?>) receivedObject);
		} else {
		    response = new Exception("Invalid request type! Handling of received object is not supported on this server!");
		}
		return response;
	}

	/**
	 * @return the object received as request from the client
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private Object readRequestObject() throws IOException, ClassNotFoundException {
		// read object from in stream
		return new ObjectInputStream( socket.getInputStream() ).readObject();
	}

	/**
	 * writes given response object to outputstream
	 * @param response
	 * @throws IOException
	 */
	private void sendResponse(Object response) throws IOException {
		final ObjectOutputStream oos = new ObjectOutputStream( socket.getOutputStream() );
		oos.writeObject(response);
		oos.flush();
	}

}
