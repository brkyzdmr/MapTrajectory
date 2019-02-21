
package mapclient;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Sunucuya toplu veri gönderebilmek için oluşturulmuş sınıf
 * 
 */
public class OutgoingData implements Serializable{
    private String key; // Hangi servisin çağrılacağı bilgisini taşır.
    private ArrayList<Point> crudedData;  // Ham veri
    private String textBoxValue;    // Servisler için gereken texbox verilerini taşır.

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
