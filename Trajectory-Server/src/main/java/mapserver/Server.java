package mapserver;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Durmadan çalışan multithreded server oluşturan sınıf
 * 
 */
public class Server extends Thread {
    
    public static final int port = 5000;    // bağlantı kurulacak port numarası
    public Socket socket = null;
    //public ServerSocket serverSocket = null;
    
    public Server(Socket socket) {
        this.socket = socket;
    }  

    @Override
    public void run() {
        long startTime = System.nanoTime(); // Thread işleme başladığı andaki zaman(nanosaniye)
        System.out.println("Thread: " + this.getName());        
        
        try {            
            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());       
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());    
            
            mapclient.OutgoingData clientData = (mapclient.OutgoingData) input.readObject();
            //printPoints(clientData.getData());
            System.out.println("Key: " + clientData.getKey());    

            // İstemciden gelen veri içerisindeki key bilgisini kullanarak 
            // ilgili alanı çalıştırır.

            switch (clientData.getKey()) {
                case "indirgeme":
                {
                    List<mapclient.Point> result = new ArrayList<>();
                    double epsilon = Double.parseDouble(
                            clientData.getTextBoxValue());
                    double reductionRate;

                    Reduction.DouglasPeucker(
                            clientData.getCrudedData(), epsilon, result);

                    printPoints(result);                            
                    long endTime = System.nanoTime(); // İndirgeme işlemi sonlandığı andaki zaman(nanosaniye)
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
                case "alansorgusu":   
                {           
                    IncomingData serverData = new IncomingData(clientData.getCrudedData());
                    QuadTree.searchInTree(clientData);  // arama işlemi yapılır
                    serverData.setProcessedData(QuadTree.getResult()); // bulunan sonuç listesi gönderilecek nesneye yazılır.
                    long endTime = System.nanoTime(); // alan sorgusu bittiği sıradaki zaman(nanosaniye)
                    String threadTime = calculateTime(startTime, endTime);
                    System.out.println("Time: " + threadTime);
                    serverData.setThreadTime(threadTime);
                    
                    output.writeObject(serverData); // İşlemler sonrası oluşan nesneyi istemciye gönderir.

                    System.out.println("Alan sorgusu yapıldı.");
                    break;
                }
                case "indirgemesorgusu":   
                {
                    List<mapclient.Point> result = new ArrayList<>();
                    String[] values = clientData.getTextBoxValue().split(",");
                    double epsilon = Double.parseDouble(values[0]);
                    double reductionRate;
                    
                    // indirgeme işlemi yapılır
                    Reduction.DouglasPeucker(
                            clientData.getCrudedData(), epsilon, result);

                    // Gönderilecek veri nesnesi
                    IncomingData serverData = new IncomingData(
                                clientData.getCrudedData());
                    serverData.setProcessedData(result); // indirgeme işlemi sonrası oluşan veri nesneye atanır
                    // indirgeme oranı hesaplanır
                    reductionRate = Reduction.calculateReductionRate(
                                clientData.getCrudedData().size(),
                                serverData.getProcessedData().size());

                    serverData.setReductionRate(reductionRate); // indirgeme oranı nesneye atanır
                    
                    // İndirgeme işlemi yapıldıktan sonra oluşan veri seti üzerinde arama işlemi yapılır.
                    QuadTree.searchInTree(clientData, serverData);
                    // Arama işlemi sonrası oluşan veri gönderilecek nesneye yazılır.
                    serverData.setProcessedData(QuadTree.getResult()); 
                    long endTime = System.nanoTime();
                    String threadTime = calculateTime(startTime, endTime);
                    System.out.println("Time: " + threadTime);                       


                    serverData.setThreadTime(threadTime);
                    output.writeObject(serverData); // İşlemler sonrası oluşan nesneyi istemciye gönderir.

                    System.out.println("İndirgeme sorgusu yapıldı.");
                    break;
                }
                default:
                    System.out.println("Server işlem hatası!! (Kritik hata)");
                    break;
            }            
        } catch (IOException | NullPointerException ex) {
            // ClassNotFoundException için MapClient.jar dosyası projeye eklenmiştir.
            System.out.println("Socket hatası: " + this.getName()
                                                           + " --> " + ex);
        } catch (Exception ex){
            System.out.println("Bilinmeyen hata: " + this.getName()
                                                           + " --> " + ex);
        } finally {
            try {
                socket.close();
                System.out.println("Sunucu bağlantısı kesildi.");
            } catch (IOException ex) {
                System.out.println("Socket kapatılamadı: " + this.getName()
                                                           + " --> " + ex);
            }
            
        }               
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Sunucu başladı: " + serverSocket);
        /*
        * Sunucu başladıktan sonra sonsuz bir döngü oluşturulur.
        * İstemciler ile bağlantı sağlandıkça yeni bir thread oluşturulur.
        * Eğer thread ile istemci arasındaki haberleşme 5 saniye içerisinde
        * yapılmazsa thread sonlandırılır ve yeni bir istek için döngü
        * istemciyi ile iletişim sağlanan portu dinlemeye devam eder.
        */
        try {
            for (;;) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Bağlantı kabul edildi: " + clientSocket);
                clientSocket.setSoTimeout(5*1000);
                new Server(clientSocket).start(); // Thread başlangıcı
            }            
        } catch (IOException ex) {
            System.err.println("Ana yapıda hata var: " + ex);
        } finally {
            serverSocket.close(); // Soketi kapat
        }
        
    }
    
    private static void printPoints(List<mapclient.Point> points) {
        System.out.println("print");
        for(int i=0; i<points.size(); i++) {
            System.out.println(points.get(i).getLatitude() + ","
            + points.get(i).getLongitude());
        }
    }
    /**
     * Nanosaniye bilgilerini kullanarak anlamlı bir zaman nesnesi oluşturur.
     * 
     * @param startTime Başlangıç zamanı(nanosaniye)
     * @param endTime Bitiş zamanı(nanosaniye)
     * @return Geçen zaman "Saniye.Salise sn" şeklinde bir String nesnesi döndürür.
     */
    private static String calculateTime(long startTime, long endTime) {
        long elapsedTime = endTime - startTime; // nanoseconds
        long miliseconds = elapsedTime / 1000000;    // 10^6
        long seconds = elapsedTime / 1000000000;    // 10^9
        
        return seconds + "." + miliseconds + " sn"; 
    }
}
