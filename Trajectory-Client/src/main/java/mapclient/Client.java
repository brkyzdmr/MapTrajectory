
package mapclient;

// A Java program for a Client

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import mapserver.IncomingData;

/**
 * The class that provides communication with the server
 * 
 */
public class Client {

    private static String address = "127.0.0.1";
    private static int port = 5000;
    private static mapserver.IncomingData serverData = null;
    
    /**
     * Lets us send an OutgoingData type object to the server.
     * 
     * @param outgoingData Object to be sent to the server
     * @return An IncomingData type object is returned from the server.
     * @throws IOException 
     */    
    public static mapserver.IncomingData sendMapData(OutgoingData outgoingData) throws IOException {
        
        Socket socket = null;
        String mapUrl = null;
        ObjectOutputStream output = null;
        ObjectInputStream input = null;


        try {                          
            
            socket = new Socket(address, port);
            System.out.println("Connected.");
            
            output = new ObjectOutputStream(socket.getOutputStream());              
            output.writeObject(outgoingData);
            
            System.out.println("output");
            input = new ObjectInputStream(socket.getInputStream());
            serverData = (mapserver.IncomingData) input.readObject();   // promise
            
            if(serverData != null) {            
                return serverData;
            } else {
                JOptionPane.showMessageDialog(null, "Map data did not send correctly!",
                        "Warning", JOptionPane.WARNING_MESSAGE);
                return null;

            }
        } catch (IOException ex) {  
            System.out.println("Client error: " + ex);
            JOptionPane.showMessageDialog(null, "Server not available!",
                    "Uyarı", JOptionPane.WARNING_MESSAGE); 

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            socket.close();
        }
        return null;
    }      

    public static IncomingData getServerData() {
        return serverData;
    }      
}
