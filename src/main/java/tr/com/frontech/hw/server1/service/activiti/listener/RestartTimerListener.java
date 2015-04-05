package tr.com.frontech.hw.server1.service.activiti.listener;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import tr.com.frontech.hw.server1.service.data.StaticXYDataContainer;

import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Mert on 14.2.2015.
 */
public class RestartTimerListener implements JavaDelegate {

    private AtomicInteger order = new AtomicInteger(0);
    RuntimeService runtimeService;
    RepositoryService repositoryService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        Integer x = Integer.parseInt(execution.getVariable("x")+"" );
        Integer y = Integer.parseInt(execution.getVariable("y")+"" );
        order.getAndIncrement();
        System.out.println("x: " + x);
        int sleeptimeMilliseconds = x * 1000;
        long id = Thread.currentThread().getId();
        System.out.println("Thread will sleep" + x + " seconds");
        Thread.sleep(sleeptimeMilliseconds);
        System.out.println("Thread wake up!!");

        //repositoryService.deleteDeployment(StaticXYDataContainer.deploymentId+"");
        //System.out.println("process id: "+StaticXYDataContainer.processId+" killed..");
        /*
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("/activiti/SampleProcess1.bpmn20.xml")
                .deploy();
        StaticXYDataContainer.deploymentId = Integer.parseInt(deployment.getId());
        HashMap<String,Object> variablesKeyValuePair = new HashMap();
        variablesKeyValuePair.put("x",StaticXYDataContainer.x);
        variablesKeyValuePair.put("y",StaticXYDataContainer.y);
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("myProcess", variablesKeyValuePair);
        StaticXYDataContainer.processId = Integer.parseInt( processInstance.getId());
        System.out.println("initial process started...   pid: " + processInstance.getId());

        runtimeService.startProcessInstanceById( ++StaticXYDataContainer.processId  + "", variablesKeyValuePair);
        System.out.println("...new process started, process id :"+StaticXYDataContainer.processId);
        */

    }

    @Autowired
    @Qualifier("runtimeService")
    public void setRuntimeService(RuntimeService runtimeService) {
        this.runtimeService = runtimeService;
    }

    @Autowired
    @Qualifier("repositoryService")
    public void setRepositoryService(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }
}
