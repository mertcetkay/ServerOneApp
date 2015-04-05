package tr.com.frontech.hw.server1.service.data;

/**
 * Created by Mert on 16.2.2015.
 */
public class RandomDataContainer {

    private String randomLong;
    private String randomString;
    private String y;

    /**
     * default constructor
     */
    public RandomDataContainer(){

    }

    public RandomDataContainer(String randomLong, String randomString, String y){
        this.randomLong = randomLong;
        this.randomString = randomString;
        this.y = y;
    }

    public String getRandomLong() {
        return randomLong;
    }

    public void setRandomLong(String randomLong) {
        this.randomLong = randomLong;
    }

    public String getRandomString() {
        return randomString;
    }

    public void setRandomString(String randomString) {
        this.randomString = randomString;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }
}
