package tr.com.frontech.hw.server1.service.data;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Mert on 19.2.2015.
 */
public class ChangeRequestQueue {

    public static BlockingQueue<String> queue = new LinkedBlockingQueue<>();

}
