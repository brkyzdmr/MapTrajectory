
package mapclient;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Harita ile ilgili işlemleri yapan sınıf
 * 
 */
public class Map {
    
    private static String API_KEY = "AIzaSyAgls3MF-F3DOBSW_ci3VepC1Om2Fg-jHY";
    
    /**
     * imageUrl ile Google sunucularına istek yapar, geriye bir resim döner.
     * Dönen bu resim proje dosyasına imageName.jpg şeklinde kaydedilir.
     * 
     * @param imageUrl Google API için gereken url
     * @param imageName Google sunucularından alınıp, proje dosyasında oluşturulacak resmin adı
     * @throws MalformedURLException
     * @throws IOException 
     */
    public static void getImageFromUrl(String imageUrl, String imageName) 
            throws MalformedURLException, IOException {
        
        System.out.println(imageUrl);
        String destinationFile = imageName + ".jpg";
        URL url = new URL(imageUrl);
        InputStream is = url.openStream();
        OutputStream os = new FileOutputStream(destinationFile);
        byte[] b = new byte[2048];
        int length;
        while ((length = is.read(b)) != -1) {
            os.write(b, 0, length);
        }
        is.close();
        os.close();       
    }
    
    /**
     * imageUrl içerisinde bulunan &zoom parametresini değiştirerek harita
     * üzerinde zoom-in ve zoom-out işlemlerinin yapılması için imageUrl
     * içerisinde "&zoom=" metnini arar ve bulduktan sonra parametrenin 
     * aldığı değeri zoomAmount ile değiştirir.
     * 
     * @param imageUrl Google API için gereken url
     * @param zoomAmount Url'deki zoom miktarı yerine gelecek sayı
     * @return Zoom işlemi sonrası oluşan url
     */
    public static String changeZoomAmount(String imageUrl, int zoomAmount) {
        String find = "&zoom=";
        StringBuffer bf = new StringBuffer(imageUrl);
        int i = imageUrl.lastIndexOf(find);
        
        // &zoom=10 yerine &zoom=9 yazılacağı zaman sonunda fazladan sıfır kalıyor
        // bunu önlemek için böyle bir işlem yapıyoruz.
        if(zoomAmount > 8) { 
            bf.replace(i+6, i+8, String.valueOf(zoomAmount));
        } else {
            bf.replace(i+6, i+7, String.valueOf(zoomAmount));
        }
        System.out.println(bf);
        System.out.println("Zoom Amount : " + zoomAmount);
        
        return bf.toString();
    }
    
    /**
     * points değişkenindeki noktaları, bu noktalar için markerColor
     * değişkeninde bulunan renk ve pathColor değişkeninde yol çizgileri
     * için bulunan rengi kullanarak bir url oluşturur.
     * 
     * @param points Haritada oluşturulacak olan noktalar
     * @param markerColor Haritadaki nokta işaretçilerinin rengi(red,  blue)
     * @param pathColor Haritada noktaları birleştiren çizgilerin rengi(0xff0000ff)
     * @return Google API için oluşturulan harita urlsi
     */
    public static String createUrl(List<mapclient.Point> points, String markerColor, 
            String pathColor) {
        
        String imageUrl = null;
        int zoomAmount = 17;
        double avrgLatitude = 0.0;
        double avrgLongitude = 0.0;
        
        // Noktaların orta noktasını bulmak için 
        for (int i = 0; i < points.size(); i++) {
            avrgLatitude += points.get(i).getLatitude();
            avrgLongitude += points.get(i).getLongitude();
        }
        avrgLatitude /= points.size();
        avrgLongitude /= points.size();

        imageUrl = "https://maps.googleapis.com/maps/api/staticmap?"
                + "center=" + avrgLatitude + "," + avrgLongitude
                + "&zoom=" + zoomAmount
                + "&size=800x600";
        for (int i = 0; i < points.size(); i++) {
            imageUrl = imageUrl + "&markers=color:" + markerColor + "%7C"
                    + points.get(i).getLatitude() + "," + points.get(i).getLongitude();
        }
        imageUrl = imageUrl + "&path=color:" + pathColor + "|weight:5";
        for (int i = 0; i < points.size(); i++) {
            imageUrl = imageUrl + "|" + points.get(i).getLatitude() + "," 
                    + points.get(i).getLongitude();
        }
        imageUrl = imageUrl + "&key=" + API_KEY;

        return imageUrl;
    }    
    
    public static String createUrlWithSearchField(List<mapclient.Point> points, 
            String markerColor, String val) {
        
        int zoomAmount = 17;
        String imageUrl = null;
        String[] values = val.split(",");
        double clickX = Double.parseDouble(values[0]);
        double clickY = Double.parseDouble(values[1]);
        double edgeX = Double.parseDouble(values[2]);
        double edgeY = Double.parseDouble(values[3]);
        
        imageUrl = "https://maps.googleapis.com/maps/api/staticmap?"
                + "center=" + clickX + "," + clickY
                + "&zoom=" + zoomAmount
                + "&size=800x600";
        for (int i = 0; i < points.size(); i++) {
            imageUrl = imageUrl + "&markers=color:" + markerColor + "%7C"
                    + points.get(i).getLatitude() + "," + points.get(i).getLongitude();
        }
        imageUrl = imageUrl + "&path=color%3ablue|weight:5|" + "fillcolor%3ablue|"
                + (clickX-edgeX) + "," + (clickY+edgeY) + "|" 
                + (clickX+edgeX) + "," + (clickY+edgeY) + "|" 
                + (clickX+edgeX) + "," + (clickY-edgeY) + "|" 
                + (clickX-edgeX) + "," + (clickY-edgeY) + "|" 
                + (clickX-edgeX) + "," + (clickY+edgeY) 
                + "&key=" + API_KEY;
        
        return imageUrl;
    }
}
