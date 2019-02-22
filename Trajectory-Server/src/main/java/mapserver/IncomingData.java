
package mapserver;

import java.io.Serializable;
import java.util.List;
import mapclient.Point;

/**
 * This class created for the send data, collectively.
 *  
 */
public class IncomingData implements Serializable{
    private List<mapclient.Point> crudedData;
    private List<mapclient.Point> processedData;
    private String threadTime;
    private double reductionRate;

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
