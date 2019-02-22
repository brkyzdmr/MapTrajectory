
package mapclient;

import java.io.Serializable;

/**
 * This is the class created to keep the data read from the file wholesale.
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
