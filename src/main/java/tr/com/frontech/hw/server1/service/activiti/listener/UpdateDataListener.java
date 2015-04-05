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

/**
 * Created by Mert on 14.2.2015.
 */
public class UpdateDataListener implements JavaDelegate {


    RuntimeService runtimeService;

    RepositoryService repositoryService;


    @Override
    public void execute(DelegateExecution execution) throws Exception {
      //  Integer x = Integer.parseInt(execution.getVariable("x")+"" );
      //  Integer y = Integer.parseInt(execution.getVariable("y")+"" );
        System.out.println("updateDataListener.execute... x: " + execution.getVariable("x"));
        System.out.println("updateDataListener.execute... y: " + execution.getVariable("y"));
       // updateXYDataInternal(x, y);
        //todo surece yeni random datalari yaz
    }



    public void updateXYDataInternal(Integer x, Integer y){
        StaticXYDataContainer.x = x;
        StaticXYDataContainer.y = y;

        System.out.println("...updateXYDataInternal()...");
       // runtimeService.deleteProcessInstance(StaticXYDataContainer.processId+"","dataupdate");
        repositoryService.deleteDeployment(StaticXYDataContainer.deploymentId+"");
        System.out.println("deployment id: "+StaticXYDataContainer.deploymentId+" killed..");

        System.out.println("...startFirstProcess()...");
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("/activiti/SampleProcess1.bpmn20.xml")
                .deploy();
        StaticXYDataContainer.deploymentId = Integer.parseInt(deployment.getId());
        System.out.println("sample process has been RE-deployed with id: " + StaticXYDataContainer.deploymentId);

        HashMap<String,Object> variablesKeyValuePair = new HashMap();
        variablesKeyValuePair.put("x",StaticXYDataContainer.x);
        variablesKeyValuePair.put("y",StaticXYDataContainer.y);
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("myProcess", variablesKeyValuePair);
        StaticXYDataContainer.processId = Integer.parseInt( processInstance.getId());
        System.out.println("initial process started...   pid: " + processInstance.getId());

        runtimeService.startProcessInstanceById( ++StaticXYDataContainer.processId  + "", variablesKeyValuePair);
        System.out.println("...new process started, process id :"+StaticXYDataContainer.processId);
        System.out.println("...updateXYDataInternal()...");
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
