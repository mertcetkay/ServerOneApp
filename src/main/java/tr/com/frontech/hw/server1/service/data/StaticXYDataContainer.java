package tr.com.frontech.hw.server1.service.data;


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Mert on 13.2.2015.
 */
public class StaticXYDataContainer {

    /**
     * x : update interval
     */
    public static Integer x = 10;

    /**
     * y : lower boundary for UI display
     */
    public static Integer y = 3;

    /**
     * thread-safe queue holds change requests of X
     */
    public static final BlockingQueue<XYDataContainer> queue = new LinkedBlockingQueue<XYDataContainer>();

    /**
     *
     */
    public static int processId = 101;

    /**
     *
     */
    public static int deploymentId = 101;

    /**
     * default constructor
     */
    public StaticXYDataContainer(){

    }

    /**
     * constructor
     */
    public StaticXYDataContainer(Integer x, Integer y, Integer processId, Integer deploymentId){
        this.x = x;
        this.y = y;
        this.processId = processId;
        this.deploymentId = deploymentId;
    }

}
