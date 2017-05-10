/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeepraktikum1.client;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.Socket;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import jeepraktikum1.client.services.IService;
import jeepraktikum1.client.transport.ProtocolParser;

/**
 *
 * @author Sebastian
 */
public class JEEPraktikum1Client {

    private String host;
    private int port;
    
    private Socket socket;

    public JEEPraktikum1Client(String host, int port) {
        this.host = host;
        this.port = port;
    }
    
    private void connectToHost(){
        try {
            System.out.printf("connecting to %s:%d...\n", host, port);
            socket = new Socket(InetAddress.getByName(host), port);
            System.out.println("Connected!");
        } catch (IOException ioe) {
            System.err.println("failed to open socket.");
            ioe.printStackTrace();
        }
    }
    
    public boolean isConnected() {
        return socket != null;
    }
    
    private void closeConnection() {
        try{
            if (socket != null) {
                socket.close();
            }
        }catch (IOException ioe) {
            System.err.println("failed to close socket.");
            ioe.printStackTrace();
        }
    }
    
    private static void printMenu(){
        System.out.println("Please select one of the following options:");
        Method[] options = IService.class.getMethods();
        int i = 0;
        for (Method o : options) {
            System.out.printf("(%d) %s\n", i, o.getName());
            i++;
        }
        System.out.printf("(%d) Print this menu\n", i++);
        System.out.printf("(%d) Exit the program\n", i);
    }
    
    private void handleOption(int option, Scanner scanner) {
        Method[] methods = IService.class.getMethods();
        
        if (option > methods.length + 1 || option < 0){
            throw new InvalidParameterException("The given Parameter is out of range.");
        }
        
        switch (option - methods.length){
            case 0:
                printMenu();
                break;
            case 1:
                System.err.println("Terminating program.");
                if (isConnected()){
                    closeConnection();
                }
                System.exit(0);
                break;
            default:
                List<Serializable> params = new ArrayList<>();
                System.out.println("Please enter the phonenumber for the searchrequest:");
                params.add(scanner.nextInt());
                System.out.println("Please enter the ordernumber for the searchrequest:");
                params.add(scanner.nextInt());
                try{
                sendRequest(methods[option].getName(), params);
                } catch (IOException ioe) {
                    System.err.println("Error sending request.");
                    ioe.printStackTrace();
                }
                System.err.println("TestDefault");
        }
    }
    
    private void sendRequest(String name, List<Serializable> parameters) throws IOException {
        OutputStream os = socket.getOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(os);
        
        List<Serializable> req = ProtocolParser.makeRequest(name, parameters);

        oos.writeObject(req);
        oos.close();
        os.close();
    }
            
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please provide a server ip or press ENTER for default:");
        String ip = "0.0.0.0";
        int port = 4567;
        final String input = scanner.nextLine();
        if (!input.isEmpty()) {
            ip = input;
            System.out.println("Please provide a server port:");
            port = scanner.nextInt();
        }
        JEEPraktikum1Client client = new JEEPraktikum1Client(ip, port);
        
        client.connectToHost();
        if (client.isConnected()){
            printMenu();
            while(true) {
                try{
                    int selection = scanner.nextInt();
                    
                    client.handleOption(selection, scanner);
                } catch (InputMismatchException ime) {
                    System.err.println("Invalid input! Please only use integers.");
                    scanner.next();
                } catch (InvalidParameterException ipe) {
                    System.err.println(ipe.getMessage());
                }
            }
        }
        /*
        try {
            socket = new Socket(InetAddress.getByName(ip), port);
            System.out.println("Connected!");
            
            printMenu();
            //get selection
            int selection = scanner.nextInt();
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
        } finally {
            if (socket != null) {
                try{
                    socket.close();
                } catch (Exception e) {
                    System.err.printf("Error closing socket: %s\n", e.getMessage());
                    e.printStackTrace();
                }
            }
        }
        */
    }
    
}
