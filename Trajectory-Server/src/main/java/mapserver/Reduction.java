
package mapserver;

import java.util.ArrayList;
import java.util.List;

/**
 * İndirgeme işleminin yapıldığı sınıf
 *
 */
public class Reduction {
    /**
     * Gelen noktalar kümesini ve epsilon değerini kullanarak indirgeme işlemi
     * yapar. Sonuçları kendisine gönderilen 3. parametredeki listeye atar.
     * 
     * @param points İndirgenecek noktaların bulunduğu liste
     * @param epsilon Epsilon değeri
     * @param output İşlemler sonrası oluşan sonuçların atanacağı liste
     */
    public static void DouglasPeucker(List<mapclient.Point> points, double epsilon, List<mapclient.Point> output) {
        // Nokta sayısı 2'den azsa indirgeme yapılamaz.
        if(points.size() < 2) {
            throw new IllegalArgumentException("Yeterli miktarda nokta yok!");
        }
        double maxDistance = 0.0;   // Maksimum dikey uzaklık
        int index = 0;  // Maksimum dikey uzaklığa sahip noktanın indeksi
        int end = points.size() - 1;    // Liste üzerindeki son noktanın indeksi
        
        for (int i=1; i<end; ++i) {
            // Başlangıç ve bitiş noktası arasında, maksimum dikey uzaklığı bulmak için
            // dikey uzaklık fonksiyonuna listedeki veriler tek tek gönderilir.
            double d = perpendicularDistance(points.get(i), points.get(0), points.get(end));
            if(d > maxDistance) {
                index = i;
                maxDistance = d;
            }
        }

        // Eğer max mesafe epsilondan büyükse, özyinelemeli olarak basitleştir
        if(maxDistance > epsilon) {
            // İşlemler sonucunda başlangıç noktası ile geçerli nokta arasındaki noktaları tutan liste
            ArrayList<mapclient.Point> result1 = new ArrayList<>(); 
            // İşlemler sonucunda geçerli nokta ile bitiş noktası arasındaki noktaları tutan liste
            ArrayList<mapclient.Point> result2 = new ArrayList<>();
            
            // Başlangıç noktası ile geçerli nokta arasındaki tüm noktalar
            List<mapclient.Point> firstLine = points.subList(0, index +1);
            // Geçerli nokta ile bitiş arasındaki tüm noktalar
            List<mapclient.Point> lastLine = points.subList(index, points.size());
            
            // Özyinelemeli olarak başlangıç noktası ile geçerli noktayı baz alarak indirgeme yapar.
            DouglasPeucker(firstLine, epsilon, result1);
            // Özyinelemeli olarak geçerli nokta ile bitiş noktasını baz alarak indirgeme yapar.
            DouglasPeucker(lastLine, epsilon, result2);
            
            // O anki tüm sonuçları output listesine atar
            output.addAll(result1.subList(0, result1.size() - 1));
            output.addAll(result2);
            
        } else {
            // Eğer o anki maksimum dikey uzaklık epsilondan küçük veya eşitse 
            // özyinelemenin o adımı durur ve o adımdaki sonuç listesine 
            // o adımdaki başlangıç ve bitiş değeri değeri eklenir.
            output.clear();
            output.add(points.get(0));
            output.add(points.get(points.size() - 1));
        }     
            
    }

    /**
     * O anki noktanın, başlangıç ve bitiş noktalarını birleştiren hayali 
     * bir çizgiye olan dikey uzaklığı verir.
     * 
     * @param currentPoint Şu anki nokta bilgisi
     * @param startPoint Başlangıç noktası
     * @param endPoint Bitiş noktası
     * @return 
     */
    private static double perpendicularDistance(mapclient.Point currentPoint, mapclient.Point startPoint, mapclient.Point endPoint) {
        
        // Başlangıç ve bitiş noktasınının enlem ve boylam farkları
        double x = endPoint.getLatitude() - startPoint.getLatitude();
        double y = endPoint.getLongitude() - startPoint.getLongitude();
        
        // Normalleştirme işlemi
        double h = Math.hypot(x, y);    // sqrt(x^2 + y^2)
        if(h > 0.0) {
            x /= h;
            y /= h;
        }
        
        double pX = currentPoint.getLatitude() - startPoint.getLatitude();
        double pY = currentPoint.getLongitude() - startPoint.getLongitude();

        // Skaler çarpım(O anki nokta normalleştirilmiş doğrultuya bakar)
        double pDot = x*pX + y*pY;
        
        // Ölçeklendirilmiş vektör var olan yön vektöründen çıkartılır.
        double aX = pX - pDot*x;
        double aY = pY - pDot*y;
               
        // Noktaların hipotenüsü bize dikey uzaklığı verir.
        return Math.hypot(aX, aY);
    }
    /**
     * (1-(İndirgenme sonucu oluşan veri sayısı/Ham veri sayısı))*100
     * işleminin sonucunu double olarak döndürür. Bu işlem bize indirgenme
     * oranını verir.
     * 
     * @param crudedSize Ham veri sayısı
     * @param processedSize İndirgenme sonucu oluşan veri sayısı
     * @return 
     */
    public static double calculateReductionRate(int crudedSize, int processedSize) {
        return ((1.0 - (double) processedSize/crudedSize)*100.0);
    }
}
