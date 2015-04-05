package tr.com.frontech.hw.server1.service.data;


/**
 * Created by Mert on 13.2.2015.
 */
public class XYDataContainer {

    /**
     * x : update interval
     */
    private Integer x = 10;

    /**
     * y : lower boundary for UI display
     */
    private Integer y = 3;

    /**
     * default constructor
     */
    public XYDataContainer(){

    }

    /**
     * constructor
     */
    public XYDataContainer(Integer x, Integer y){
        this.x = x;
        this.y = y;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public String toString(){
        return "x:"+x+"\ny:"+y;
    }
}