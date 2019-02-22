
package mapclient;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class created to send wholesale data to the server
 * 
 */
public class OutgoingData implements Serializable{
    private String key; // Information about which service to call.
    private ArrayList<Point> crudedData;  // Raw data
    private String textBoxValue;    // It carries texbox data required for services.

    public OutgoingData(String key, ArrayList<Point> crudedData, String textBoxValue) {
        this.key = key;
        this.crudedData = crudedData;
        this.textBoxValue = textBoxValue;
    }

    public String getKey() {
        return key;
    }

    public ArrayList<Point> getCrudedData() {
        return crudedData;
    }

    public String getTextBoxValue() {
        return textBoxValue;
    }      

}
