package tr.com.frontech.hw.server1.service;

import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import tr.com.frontech.hw.server1.service.data.JsonServiceResponse;
import tr.com.frontech.hw.server1.service.data.RandomDataContainer;
import tr.com.frontech.hw.server1.service.data.StaticXYDataContainer;
import tr.com.frontech.hw.server1.service.data.XYDataContainer;
import tr.com.frontech.hw.server1.service.random.RandomGenerator;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

@Controller
public class ServerOneController {


    ProcessEngine processEngine;

    RuntimeService runtimeService;

    ManagementService managementService;

    RepositoryService repositoryService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView dispatchToMain() {
        ModelAndView model = new ModelAndView("index");
        return model;
    }

    @RequestMapping(value = "/updatexy")
    public void updateXYHandler(final HttpSession session,
                                final HttpServletResponse response,
                                @RequestParam("X") final String x,
                                @RequestParam("Y") final String y) {

        JsonServiceResponse JsonResponse = new JsonServiceResponse();
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json; charset=UTF-8");

        try {
            StaticXYDataContainer.queue.put(new XYDataContainer(Integer.parseInt(x), Integer.parseInt(y)));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        /*
        System.out.println("...updateXYData()...");
        // butun calisan instanceleri oldur
        repositoryService.deleteDeployment(StaticXYDataContainer.deploymentId+"");
        //runtimeService.deleteProcessInstance(StaticXYDataContainer.processId+"","dataupdate");
        System.out.println("DeploymentID "+StaticXYDataContainer.deploymentId+" has been deleted");
        System.out.println("process id: "+StaticXYDataContainer.processId+" killed..");
        HashMap<String,Object> variablesKeyValuePair = new HashMap();
        variablesKeyValuePair.put("x",Integer.parseInt(x));
        variablesKeyValuePair.put("y",Integer.parseInt(y));
        runtimeService.startProcessInstanceById( ++StaticXYDataContainer.processId  + "", variablesKeyValuePair);
        System.out.println("...new process started, process id :"+StaticXYDataContainer.processId);
        System.out.println("...updateXYData()...");
        */
        JsonResponse.setStatus("success");
        try {
            response.getOutputStream().write(mapper.writeValueAsString(JsonResponse).getBytes("UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @RequestMapping(value = "/init")
    public void startFirstProcess(final HttpSession session,
                                  final HttpServletResponse response) {
        JsonServiceResponse JsonResponse = new JsonServiceResponse();
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json; charset=UTF-8");

        String message = "";
        message += "x:"+10+",y:"+3+",rLong:"+getNewRandomLong()+",rString:"+getNewRandomString();
        JsonResponse.setData(message);
        JsonResponse.setMessage(message);

        StaticXYDataContainer.queue.offer(new XYDataContainer(10,3));

        Thread running = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try {
                        boolean isEmpty = StaticXYDataContainer.queue.isEmpty();
                        System.out.println("isEmpty: " + isEmpty);
                        System.out.println("size: "+ StaticXYDataContainer.queue.size());
                        if( !isEmpty ){
                            XYDataContainer localData = StaticXYDataContainer.queue.take();
                            System.out.println(localData);
                            try {
                                String input = "{rLong:"+localData.getX()+", rString:"+localData.getY()+"}";
                                URL url = new URL("http://localhost:8082/push"+"?rLong="+getNewRandomLong()+"&rString="+getNewRandomString()+"&Y="+localData.getY());
                                URLConnection connection = url.openConnection();
                                connection.setDoOutput(true);
                                connection.setRequestProperty("Content-Type", "application/json");
                                connection.setConnectTimeout(5000);
                                connection.setReadTimeout(2000);
                                OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
                                out.write(input);
                                out.close();
                                BufferedReader in = new BufferedReader(new InputStreamReader(
                                        connection.getInputStream()));
                                in.close();

                            } catch (Exception e) {
                              //  System.out.println("\nError while calling REST Service");
                               // System.out.println(e);
                            }
                            System.out.println("REST Service Invoked Successfully; blocking-queue data");
                            System.out.println("Sleeping starts " + localData.getX() + "seconds");
                            StaticXYDataContainer.x = localData.getX();
                            StaticXYDataContainer.y = localData.getY();
                            Thread.sleep(localData.getX() * 1000);
                            System.out.println("Sleeping ends..");
                            System.out.println();

                        } else{
                            try {
                                String input = "{rLong:"+StaticXYDataContainer.x+", rString:"+StaticXYDataContainer.y+"}";
                                URL url = new URL("http://localhost:8082/push"+"?rLong="+getNewRandomLong()+"&rString="+getNewRandomString()+"&Y="+StaticXYDataContainer.y);
                                URLConnection connection = url.openConnection();
                                connection.setDoOutput(true);
                                connection.setRequestProperty("Content-Type", "application/json");
                                connection.setConnectTimeout(5000);
                                connection.setReadTimeout(2000);
                                OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
                                out.write(input);
                                out.close();
                                BufferedReader in = new BufferedReader(new InputStreamReader(
                                        connection.getInputStream()));
                                in.close();

                            } catch (Exception e) {
                                //  System.out.println("\nError while calling REST Service");
                                // System.out.println(e);
                            }
                            System.out.println("REST Service Invoked Successfully; old data");
                            System.out.println("Sleeping starts "+ StaticXYDataContainer.x + "seconds");
                            Thread.sleep(StaticXYDataContainer.x * 1000);
                            System.out.println("Sleeping ends..");
                            System.out.println();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        System.out.println("concurrency error");
                    }
                }
            }
        });
        running.start();
        /*
        System.out.println("...startFirstProcess()...");
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("/activiti/SampleProcess1.bpmn20.xml")
                .deploy();
        StaticXYDataContainer.deploymentId = Integer.parseInt(deployment.getId());

        System.out.println("sample process has been deployed with id: " + StaticXYDataContainer.deploymentId);
        HashMap<String,Object> variablesKeyValuePair = new HashMap();
        variablesKeyValuePair.put("x",new Integer(10));
        variablesKeyValuePair.put("y",new Integer(3));
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("myProcess", variablesKeyValuePair);
        StaticXYDataContainer.processId = Integer.parseInt( processInstance.getId());
        System.out.println("initial process started...   pid: " + processInstance.getId());
        System.out.println("...startFirstProcess()...");
        */
        JsonResponse.setStatus("success");
        try {
            response.getOutputStream().write(mapper.writeValueAsString(JsonResponse).getBytes("UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @RequestMapping(value = "/updatex")
    public void updateXHandler(final HttpSession session,
                               final HttpServletResponse response,
                               @RequestParam("X") final String x){
        JsonServiceResponse JsonResponse = new JsonServiceResponse();
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json; charset=UTF-8");
        StaticXYDataContainer.x = Integer.parseInt(x);
        JsonResponse.setData(StaticXYDataContainer.x+"");
        JsonResponse.setStatus("success");
        try {
            response.getOutputStream().write(mapper.writeValueAsString(JsonResponse).getBytes("UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/updatey")
    public void updateYHandler(final HttpSession session,
                               final HttpServletResponse response,
                               @RequestParam("Y") final String y){

        JsonServiceResponse JsonResponse = new JsonServiceResponse();
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json; charset=UTF-8");
        StaticXYDataContainer.y = Integer.parseInt(y);
        JsonResponse.setData(StaticXYDataContainer.y+"");
        JsonResponse.setStatus("success");
        try {
            response.getOutputStream().write(mapper.writeValueAsString(JsonResponse).getBytes("UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @RequestMapping(value = "/getx")
    public void getRandomLong(final HttpSession session,
                              final HttpServletResponse response) {
        JsonServiceResponse JsonResponse = new JsonServiceResponse();
        response.setContentType("application/json; charset=UTF-8");
        JsonResponse.setData(StaticXYDataContainer.x+"");
    }

    @RequestMapping(value = "/gety")
    public void getRandomString(final HttpSession session,
                                  final HttpServletResponse response) {
        JsonServiceResponse JsonResponse = new JsonServiceResponse();
        response.setContentType("application/json; charset=UTF-8");
        JsonResponse.setData(StaticXYDataContainer.y+"");
    }



    /**
     * helper function responsible of random long generation
     */
    private String getNewRandomLong() {
        return RandomGenerator.getRandomLong() + "";
    }

    /**
     * helper function responsible of string long generation
     */
    private String getNewRandomString() {
        return RandomGenerator.getRandomString(20);
    }

    public ProcessEngine getProcessEngine() {
        return processEngine;
    }

    @Autowired
    @Qualifier("processEngine")
    public void setProcessEngine(ProcessEngine processEngine) {
        this.processEngine = processEngine;
    }

    public RuntimeService getRuntimeService() {
        return runtimeService;
    }

    @Autowired
    @Qualifier("runtimeService")
    public void setRuntimeService(RuntimeService runtimeService) {
        this.runtimeService = runtimeService;
    }

    public ManagementService getManagementService() {
        return managementService;
    }

    @Autowired
    @Qualifier("managementService")
    public void setManagementService(ManagementService managementService) {
        this.managementService = managementService;
    }

    public RepositoryService getRepositoryService() {
        return repositoryService;
    }

    @Autowired
    @Qualifier("repositoryService")
    public void setRepositoryService(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }
}