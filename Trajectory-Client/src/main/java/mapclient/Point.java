
package mapclient;

import java.io.Serializable;

/**
 * Dosyadan okunan verileri toplu halde tutmak için oluşturulmuş sınıf
 * 
 */
public class Point implements Serializable {
    private double latitude;
    private double longitude;
    
    public Point(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }    
}
