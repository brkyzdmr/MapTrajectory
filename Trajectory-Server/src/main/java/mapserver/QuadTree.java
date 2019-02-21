
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
     * Quad tree oluşturabilmek için gerek node sınıfı
     */
    public static class Node {
        private double latitude;    // enlem
        private double longitude;   // boylam
        Node NE, NW, SE, SW;    // Node'un bağlandığı yönsel nodelar

        public Node(double latitude, double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }
    }
    /**
     * 
     * @return Arama sonucu oluşturulan sonuç verilerinin bulunduğu liste
     */
    public static List<mapclient.Point> getResult() {
        return result;
    }
    
    /**
     * Ağaç içerisinde, köke göre yönsel olarak ekleme işlemi yapar.
     * 
     * @param latitude Enlem bilgisi
     * @param longitude Boylam bilgisi
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
                        System.out.println("NW");
                        return;
                    }
                } else if(latitude > parent.latitude && longitude < parent.longitude) {
                    focusedNode = focusedNode.NE;
                    
                    if(focusedNode == null) {
                        Node newNode = new Node(latitude, longitude);
                        parent.NE = newNode;
                        System.out.println("NE");
                        return;
                    }
                } else if(latitude < parent.latitude && longitude > parent.longitude) {
                    focusedNode = focusedNode.SW;
                    
                    if(focusedNode == null) {
                        Node newNode = new Node(latitude, longitude);
                        parent.SW = newNode;
                        System.out.println("SW");
                        return;
                    }
                } else if(latitude > parent.latitude && longitude > parent.longitude) {
                    focusedNode = focusedNode.SE;
                    
                    if(focusedNode == null) {
                        Node newNode = new Node(latitude, longitude);
                        parent.SE = newNode;
                        System.out.println("SE");
                        return;
                    }
                } else {
                    System.out.println("Aynı noktaya ekleme işlemi yapılamaz!");
                    return;
                }
            }
        }            
    }
    
    /**
     * Kök node'u alarak içerisinde preorder olarak dolaşır. Bu dolaşma esnasında
     * eğer geçerli node'un enlem ve boylam bilgisi, aranılacak dikdörtgen 
     * koordinatları içerisinde kalıyorsa, geçerli node sonuç listesine eklenir.
     * 
     * @param wanted Aranmak istenen ağaçtaki kök node
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
            // Preorder dolaşma
            searchNode(focusedNode.NW);
            searchNode(focusedNode.NE);
            searchNode(focusedNode.SW);
            searchNode(focusedNode.SE);
        }       
    }
    
    /**
     * Liste ile birlikte gelen noktaları kullanarak, yönsel bir ağaç oluşturmak
     * için noktaları sıra ile addNode fonksiyonuna gönderir.
     * 
     * @param points Ağaç oluşturulması için gerekli noktaların listesi
     */
    private static void makeTree(List<mapclient.Point> points) {        
        for(int i=0; i<points.size(); i++) {
            addNode(points.get(i).getLatitude(),
                    points.get(i).getLongitude());
             
        }
    }
    
    /**
     * Ham veri üzerinde arama işlemi yapılması için gerek işlemleri yönetir.
     * Görevi tamamlandığında result değişkeninde sonuç bilgileri bulunur.
     * 
     * @param data Client'tan gelen veri nesnesi
     */
    public static void searchInTree(mapclient.OutgoingData data) {
        String tb = data.getTextBoxValue();     
        String[] values = tb.split(",");
        
        clickX = Double.parseDouble(values[0]); // aranacak merkez noktası enlemi
        clickY = Double.parseDouble(values[1]); // aranacak merkez noktası boylamı
        edgeX = Double.parseDouble(values[2]);  // kenar bilgisi 1
        edgeY = Double.parseDouble(values[3]);  // kenar bilgisi 2
        System.out.println(clickX + "," + clickY + "," + edgeX + "," + edgeY);
        
        result = new ArrayList<>();
        makeRoot(data.getCrudedData());
        makeTree(data.getCrudedData());
        System.out.println("Root: " + root.latitude + "," + root.longitude);
        searchNode(root);
        printResult();
        System.out.println("Search completed!");
    }
    
    /**
     * İndirgenmiş veri üzerinde dikdörtgensel arama gerçekleştirmek için 
     * gereken işlemleri yöneten fonsksiyondur. Görevini tamamladığında
     * result değişkenine, indirgenmiş veriler içerisinde, dikdörtgensel alan
     * ile çakışan sonuçları atar.
     * 
     * @param data Client'tan gelen veri nesnesi
     * @param indata Sunucudan gönderilecek olan veri bilgisi(indirgenmiş veriyi kullanmak için)
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
        System.out.println("Root: " + root.latitude + "," + root.longitude);

        searchNode(root);

        printResult();
        System.out.println("Search completed!");
    }
    
    /**
     * Ağaç oluşturmak için gereken kök noktayı, gelen noktalar listesini
     * kullanarak oluşturur. Noktaların enlem ve boylam ortalamaları ile 
     * kök noktanın enlem ve boylam bilgisi oluşuturulur.
     * 
     * @param points Oluşturulacak ağaç için gereken noktalar listesi
     */
    private static void makeRoot(List<mapclient.Point> points) {
        double avrgLatitude = 0.0;       
        double avrgLongitude = 0.0;
        
        for (int i = 0; i < points.size(); i++) {
            avrgLatitude += points.get(i).getLatitude();
            avrgLongitude += points.get(i).getLongitude();
        }
        avrgLatitude /= points.size();  // enlem ortalaması
        avrgLongitude /= points.size(); // boylam ortalaması
        System.out.println("Size: " + points.size());
        
        rootLat = avrgLatitude;
        rootLong = avrgLongitude;
        root = new Node(avrgLatitude, avrgLongitude);


    }
    
    /**
     * Hesaplanan sonuç bilgisini konsola yazdırmak için kullanılır.
     */
    private static void printResult() {
        System.out.println("Result: ");
        for(int i=0; i<result.size(); i++) {
            System.out.println(result.get(i).getLatitude() + ","
            + result.get(i).getLongitude());
        }
    }
}
