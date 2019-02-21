
package mapserver;

import java.io.Serializable;
import java.util.List;
import mapclient.Point;

/**
 * İstemci tarafına toplu veri gönderebilmek için oluşturulan sınıf
 *  
 */
public class IncomingData implements Serializable{
    private List<mapclient.Point> crudedData; // Ham veri
    private List<mapclient.Point> processedData; // İşlem görmüş veri
    private String threadTime;  // Thread süresi
    private double reductionRate; // İndirgeme oranı

    public IncomingData(List<Point> crudedData) {
        this.crudedData = crudedData;
    }

    public List<Point> getCrudedData() {
        return crudedData;
    }

    public List<Point> getProcessedData() {
        return processedData;
    }

    public String getThreadTime() {
        return threadTime;
    }

    public double getReductionRate() {
        return reductionRate;
    }    

    public void setThreadTime(String threadTime) {
        this.threadTime = threadTime;
    }

    public void setReductionRate(double reductionRate) {
        this.reductionRate = reductionRate;
    }   

    public void setProcessedData(List<Point> processedData) {
        this.processedData = processedData;
    }   
    
}
