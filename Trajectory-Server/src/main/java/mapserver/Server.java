package mapserver;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Multithreded server forming class running nonstop
 * 
 */
public class Server extends Thread {
    
    public static final int port = 5000;    // port number to be connected
    public Socket socket = null;
    //public ServerSocket serverSocket = null;
    
    public Server(Socket socket) {
        this.socket = socket;
    }  

    @Override
    public void run() {
        long startTime = System.nanoTime(); // When the thread starts processing (nanoseconds)
        System.out.println("Thread: " + this.getName());        
        
        try {            
            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());       
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());    
            
            mapclient.OutgoingData clientData = (mapclient.OutgoingData) input.readObject();
            //printPoints(clientData.getData());
            System.out.println("Key: " + clientData.getKey());    

            // Runs the relevant field by using the key information in the data from the client.

            switch (clientData.getKey()) {
                case "reduction":
                {
                    List<mapclient.Point> result = new ArrayList<>();
                    double epsilon = Double.parseDouble(
                            clientData.getTextBoxValue());
                    double reductionRate;

                    Reduction.DouglasPeucker(
                            clientData.getCrudedData(), epsilon, result);

                    printPoints(result);                            
                    long endTime = System.nanoTime(); // Time at which the reduction process ends (nanoseconds)
                    String threadTime = calculateTime(startTime, endTime);
                    System.out.println("Time: " + threadTime);

                    IncomingData serverData = new IncomingData(
                            clientData.getCrudedData());
                    serverData.setProcessedData(result);
                    reductionRate = Reduction.calculateReductionRate(
                            clientData.getCrudedData().size(),
                            serverData.getProcessedData().size());

                    serverData.setReductionRate(reductionRate);
                    serverData.setThreadTime(threadTime);

                    output.writeObject(serverData);
                    break;  
                }
                case "searchinfield":   
                {           
                    IncomingData serverData = new IncomingData(clientData.getCrudedData());
                    QuadTree.searchInTree(clientData);  // search is performed
                    serverData.setProcessedData(QuadTree.getResult()); // The resulting list of results is written to the object to be sent.
                    long endTime = System.nanoTime(); // the time in which the field query ends (nanoseconds)
                    String threadTime = calculateTime(startTime, endTime);
                    System.out.println("Time: " + threadTime);
                    serverData.setThreadTime(threadTime);
                    
                    output.writeObject(serverData); // Sends the resulting object to the client after the operations.

                    System.out.println("Search in field succeed!");
                    break;
                }
                case "searchinreductedfield":   
                {
                    List<mapclient.Point> result = new ArrayList<>();
                    String[] values = clientData.getTextBoxValue().split(",");
                    double epsilon = Double.parseDouble(values[0]);
                    double reductionRate;
                    
                    // reduction is performed
                    Reduction.DouglasPeucker(
                            clientData.getCrudedData(), epsilon, result);

                    // The data object to send
                    IncomingData serverData = new IncomingData(
                                clientData.getCrudedData());
                    serverData.setProcessedData(result); // The data generated after the reduction process is assigned to the object
                    // reduction rate is calculated
                    reductionRate = Reduction.calculateReductionRate(
                                clientData.getCrudedData().size(),
                                serverData.getProcessedData().size());

                    serverData.setReductionRate(reductionRate); // the reduction ratio is assigned to the object
                    
                    // After the reduction process is done, the data set is searched.
                    QuadTree.searchInTree(clientData, serverData);
                    // The data generated after the search is written to the object to be sent.
                    serverData.setProcessedData(QuadTree.getResult()); 
                    long endTime = System.nanoTime();
                    String threadTime = calculateTime(startTime, endTime);
                    System.out.println("Time: " + threadTime);                       


                    serverData.setThreadTime(threadTime);
                    output.writeObject(serverData); // Sends the resulting object to the client after the operations.

                    System.out.println("Search in reducted field succeed!");
                    break;
                }
                default:
                    System.out.println("Server processing error! (Critical error)");
                    break;
            }            
        } catch (IOException | NullPointerException ex) {
            // The MapClient.jar file for ClassNotFoundException has been added to the project.
            System.out.println("Socket error: " + this.getName()
                                                           + " --> " + ex);
        } catch (Exception ex){
            System.out.println("Unknown error: " + this.getName()
                                                           + " --> " + ex);
        } finally {
            try {
                socket.close();
                System.out.println("Server disconnected.");
            } catch (IOException ex) {
                System.out.println("socket could not be closed: " + this.getName()
                                                           + " --> " + ex);
            }
            
        }               
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Server starts: " + serverSocket);
        /** 
         * After the server starts, an infinite loop is created. A new thread is 
         * created as the connection with the clients is established. If the communication 
         * between the thread and the client is not done within 5 seconds, the thread is 
         * terminated and the loop continues to listen to the port that communicates with 
         * the client for a new request. 
        */
        try {
            for (;;) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connection accepted: " + clientSocket);
                clientSocket.setSoTimeout(5*1000);
                new Server(clientSocket).start(); // Thread starts
            }            
        } catch (IOException ex) {
            System.err.println("Error in main structure: " + ex);
        } finally {
            serverSocket.close(); // Close the socket
        }
        
    }
    
    private static void printPoints(List<mapclient.Point> points) {
        for(int i=0; i<points.size(); i++) {
            System.out.println(points.get(i).getLatitude() + ","
            + points.get(i).getLongitude());
        }
    }
    /**
     * Creates a meaningful time object using nanosecond information.
     * 
     * @param startTime Start time(nanoseconds)
     * @param endTime End time(nanoseconds)
     * @return The elapsed time returns a String object such as "seconds.salads sn".
     */
    private static String calculateTime(long startTime, long endTime) {
        long elapsedTime = endTime - startTime; // nanoseconds
        long miliseconds = elapsedTime / 1000000;    // 10^6
        long seconds = elapsedTime / 1000000000;    // 10^9
        
        return seconds + "." + miliseconds + " sn"; 
    }
}
