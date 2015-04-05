package tr.com.frontech.hw.server1.service.data;

import org.apache.ibatis.annotations.Update;
import tr.com.frontech.hw.server1.service.random.RandomGenerator;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Mert on 19.2.2015.
 */
public class UpdateThread implements Runnable{

    private final Boolean parameter_LOCK = false;
    private Long parameterX;
    private Long parameterY;

    public UpdateThread(final Long parameterX, final Long parameterY){
        this.parameterX = parameterX;
        this.parameterY = parameterY;
    }


    public void setParameterX(final Long parameterX) {
        boolean done = false;
        synchronized ( parameter_LOCK ) {
            if ( null == parameter_LOCK ){
                this.parameterX = parameterX;
                done = true;
            }
        }
        if( !done ){
            throw new RuntimeException("UpdateThread - parameterX not clear.");
        }
    }

    public void setParameterY(final Long parameterY) {
        this.parameterY = parameterY;
        boolean done = false;
        synchronized ( parameter_LOCK ) {
            if ( null == parameter_LOCK ){
                this.parameterY = parameterY;
                done = true;
            }
        }
        if( !done ){
            throw new RuntimeException("UpdateThread - parameterY not clear.");
        }
    }

    public void clearparameterX(){

        synchronized( parameter_LOCK )
        {
            this.parameterX = null;
        }
    }

    public void clearparameterY(){

        synchronized( parameter_LOCK )
        {
            this.parameterY = null;
        }
    }

    @Override
    public void run() {
        while(true){
        Long localParameterX;
        Long localParameterY;
        synchronized (parameter_LOCK) {
            localParameterX = this.parameterX;
            localParameterY = this.parameterY;
        }
        if (null != localParameterX && null != localParameterY) {
            try {
                URL url = new URL("http://localhost:8082/push" + "?rLong=" + RandomGenerator.getRandomLong() + "&rString=" + RandomGenerator.getRandomString(20) + "&Y=" + localParameterY);
                URLConnection connection = null;
                connection = url.openConnection();
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);
                String input = "{rLong:" + localParameterX + ", rString:" + localParameterY + "}";
                OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
                out.write(input);
                out.close();

                System.out.println("\nREST Service Invoked Successfully..");

                Thread.sleep(localParameterX * 1000);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    }

    public static  void  main(String...args){
        Thread t = new Thread( new UpdateThread(new Long(10),new Long(3)));
        t.start();
    }
}
