
package mapclient;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * It is the class that performs the operations related to the map.
 * 
 */
public class Map {
    
    private static String API_KEY = "AIzaSyAgls3MF-F3DOBSW_ci3VepC1Om2Fg-jHY";
    
    /**
     * imageUrl makes a request to Google servers and returns a picture. 
     * This rotating image is saved in the project file as imageName.jpg.
     * 
     * @param imageUrl Required URL for Google API
     * @param imageName The name of the image taken from Google servers and created in the project file
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
     * imageUrl to change the zoom parameter to search and zoom the map 
     * on the map within the "&zoom=" text in the imageUrl and after finding 
     * the value of the parameter is changed to zoomAmount ".
     * 
     * @param imageUrl Required URL for Google API
     * @param zoomAmount The number in the url instead of the amount of zoom
     * @return URL generated after zooming
     */
    public static String changeZoomAmount(String imageUrl, int zoomAmount) {
        String find = "&zoom=";
        StringBuffer bf = new StringBuffer(imageUrl);
        int i = imageUrl.lastIndexOf(find);
        
        // &zoom=10 and &zoom=9 transition error fix
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
     * The points in the points variable create a url using the color 
     * in the markerColor variable for those points and the color for 
     * the path lines in the pathColor variable.
     * 
     * @param points Points to be created on the map
     * @param markerColor Color of point markers on the map (red, blue)
     * @param pathColor Color of lines connecting dots on map (0xff0000ff)
     * @return Map url created for Google API
     */
    public static String createUrl(List<mapclient.Point> points, String markerColor, 
            String pathColor) {
        
        String imageUrl = null;
        int zoomAmount = 17;
        double avrgLatitude = 0.0;
        double avrgLongitude = 0.0;
        
        // To find the midpoint of the dots
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
