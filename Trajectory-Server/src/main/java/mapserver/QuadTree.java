
package mapserver;

import java.util.ArrayList;
import java.util.List;
import mapclient.Point;

public class QuadTree {
    private static Node root;
    private static double rootLat;
    private static double rootLong;
    private static List<mapclient.Point> result;
    private static double clickX;
    private static double clickY;
    private static double edgeX;
    private static double edgeY;
    
    /**
     * This class created for creating the Quad Tree data structure.
     */
    public static class Node {
        private double latitude; 
        private double longitude;
        Node NE, NW, SE, SW;    // Node direction

        public Node(double latitude, double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }
    }
    /**
     * 
     * @return It contains result data for the search process.
     */
    public static List<mapclient.Point> getResult() {
        return result;
    }
    
    /**
     * It makes the process of adding direction according to the root within the tree.
     * 
     * @param latitude Latitude value
     * @param longitude Longitude value
     */
    private static void addNode(double latitude, double longitude) {
        if(root == null) {
            System.out.println("First Node");
            Node newNode = new Node(rootLat, rootLong);
            root = newNode;
        } else {
            Node focusedNode = root;
            Node parent;
            
            while(true) {
                parent = focusedNode;
                
                if(latitude < parent.latitude && longitude < parent.longitude) {
                    focusedNode = focusedNode.NW;
                    
                    if(focusedNode == null) {
                        Node newNode = new Node(latitude, longitude);
                        parent.NW = newNode;
                        return;
                    }
                } else if(latitude > parent.latitude && longitude < parent.longitude) {
                    focusedNode = focusedNode.NE;
                    
                    if(focusedNode == null) {
                        Node newNode = new Node(latitude, longitude);
                        parent.NE = newNode;
                        return;
                    }
                } else if(latitude < parent.latitude && longitude > parent.longitude) {
                    focusedNode = focusedNode.SW;
                    
                    if(focusedNode == null) {
                        Node newNode = new Node(latitude, longitude);
                        parent.SW = newNode;
                        return;
                    }
                } else if(latitude > parent.latitude && longitude > parent.longitude) {
                    focusedNode = focusedNode.SE;
                    
                    if(focusedNode == null) {
                        Node newNode = new Node(latitude, longitude);
                        parent.SE = newNode;
                        return;
                    }
                } else {
                    System.out.println("Cannot add to same point!");
                    return;
                }
            }
        }            
    }
    
    /**
     * It circulates as a preorder in the root node. During this run, 
     * if the current node's latitude and longitude information is within 
     * the rectangular coordinates to be searched, the current node is added 
     * to the list of results.
     * 
     * @param wanted Root node in the desired tree
     */
    private static void searchNode(Node wanted) {
        Node focusedNode = wanted;        
        
        if(wanted != null) {
            if((Math.abs(Math.abs(focusedNode.latitude) - Math.abs(clickX)) < edgeX)
                    && (Math.abs(Math.abs(focusedNode.longitude) - Math.abs(clickY)) < edgeY) 
                    && (wanted != root)) {
                
                mapclient.Point newPoint = new mapclient.Point(focusedNode.latitude, focusedNode.longitude);               
                
                result.add(newPoint);
            }    
            // Preorder circulation
            searchNode(focusedNode.NW);
            searchNode(focusedNode.NE);
            searchNode(focusedNode.SW);
            searchNode(focusedNode.SE);
        }       
    }
    
    /**
     * Using the points that come with the list, it sends the points
     * to the addNode function with the sequence to create a directional tree.
     * 
     * @param points List of points needed to create a tree
     */
    private static void makeTree(List<mapclient.Point> points) {        
        for(int i=0; i<points.size(); i++) {
            addNode(points.get(i).getLatitude(),
                    points.get(i).getLongitude());
             
        }
    }
    
    /**
     * Manages processes for searching on raw data. When the task is completed, 
     * the result variable contains the result information.
     * 
     * @param data Data object from Client
     */
    public static void searchInTree(mapclient.OutgoingData data) {
        String tb = data.getTextBoxValue();     
        String[] values = tb.split(",");
        
        clickX = Double.parseDouble(values[0]); // center point latitude to be searched
        clickY = Double.parseDouble(values[1]); // center point longitude to be searched
        edgeX = Double.parseDouble(values[2]);  // edge data X
        edgeY = Double.parseDouble(values[3]);  // edge data Y
        
        result = new ArrayList<>();
        makeRoot(data.getCrudedData());
        makeTree(data.getCrudedData());
        searchNode(root);
        //printResult();
        System.out.println("Search completed!");
    }
    
    /**
     * It is the function that manages the operations required to perform 
     * rectangular search on reduced data. When it completes its task, it assigns 
     * the result variable, within the reduced data, to the overlapping results with 
     * the rectangular area.
     * 
     * @param data Data object from Client
     * @param indata Data information to be sent from the server (to use the reduced data)
     */
    public static void searchInTree(mapclient.OutgoingData data, IncomingData indata) {
        String tb = data.getTextBoxValue();    
        String[] values = tb.split(",");
        
        clickX = Double.parseDouble(values[1]);
        clickY = Double.parseDouble(values[2]);
        edgeX = Double.parseDouble(values[3]);
        edgeY = Double.parseDouble(values[4]);
        
        result = new ArrayList<>();
        makeRoot(indata.getProcessedData());
        makeTree(indata.getProcessedData());

        searchNode(root);

        //printResult();
        System.out.println("Search completed!");
    }
    
    /**
     * Creates the root point that is needed to create a tree using 
     * a list of incoming dots. The latitude and longitude of the 
     * points and the latitude and longitude of the root point are formed.
     * 
     * @param points List of points required for the tree to be created
     */
    private static void makeRoot(List<mapclient.Point> points) {
        double avrgLatitude = 0.0;       
        double avrgLongitude = 0.0;
        
        for (int i = 0; i < points.size(); i++) {
            avrgLatitude += points.get(i).getLatitude();
            avrgLongitude += points.get(i).getLongitude();
        }
        avrgLatitude /= points.size();  // latitude average
        avrgLongitude /= points.size(); // longitude average
        
        rootLat = avrgLatitude;
        rootLong = avrgLongitude;
        root = new Node(avrgLatitude, avrgLongitude);


    }
    
    /**
     * Used to print the calculated result information to the console.
     */
    private static void printResult() {
        System.out.println("Result: ");
        for(int i=0; i<result.size(); i++) {
            System.out.println(result.get(i).getLatitude() + ","
            + result.get(i).getLongitude());
        }
    }
}
