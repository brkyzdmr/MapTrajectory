
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
 * Sunucu ile iletişimi sağlayan sınıf
 * 
 */
public class Client {

    private static String address = "127.0.0.1";
    private static int port = 5000;
    private static mapserver.IncomingData serverData = null;
    
    /**
     * Sunucuya OutgoingData tipinde bir nesne göndermemizi sağlar.
     * 
     * @param outgoingData Sunucuya gönderilecek nesne
     * @return Sunucudan IncomingData tipinde bir nesne döner.
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
                JOptionPane.showMessageDialog(null, "Sunucu hatalı harita verisi gönderdi!", 
                        "Uyarı", JOptionPane.WARNING_MESSAGE);
                return null;

            }
        } catch (IOException ex) {  
            System.out.println("Client hatası: " + ex);
            JOptionPane.showMessageDialog(null, "Sunucuya ulaşılamıyor!", 
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
