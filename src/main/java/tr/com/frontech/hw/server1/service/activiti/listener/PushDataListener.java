package tr.com.frontech.hw.server1.service.activiti.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

/**
 * Created by Mert on 14.2.2015.
 */
public class PushDataListener  implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
       // Integer x = Integer.parseInt((String)execution.getVariable("x") );
       // Integer y = Integer.parseInt((String)execution.getVariable("y") );
        pushServer2ToLoadUpdatedData(10,3);
    }


    public void pushServer2ToLoadUpdatedData(Integer x,Integer y){
            System.out.println("...pushServer2ToLoadUpdatedData()...");
            System.out.println("x: " + x);
            System.out.println("y: " + y);
            System.out.println("...pushServer2ToLoadUpdatedData()...");
        /**
         *  server2 deki load browseri cagir
         *  parametre olarak kendindeki current long stringi verir
         *  todo
         */
    }
}
