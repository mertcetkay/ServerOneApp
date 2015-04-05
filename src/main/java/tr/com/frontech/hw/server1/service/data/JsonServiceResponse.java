package tr.com.frontech.hw.server1.service.data;

/**
 * Created by Mert on 13.2.2015.
 */
public class JsonServiceResponse {

    /**
     * service operation status
     */
    private String status;
    /**
     * data of service response
     */
    private String data;
    /**
     * service operation response message
     */
    private String message;

    /**
     * default constructor
     */
    public JsonServiceResponse(){

    }

    /**
     * constructor
     */
    public JsonServiceResponse(String status, String data, String message){
        this.data = data;
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
