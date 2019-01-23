package com.atguigu.atcrowdfunding.controller;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.atguigu.atcrowdfunding.bean.Member;

@RestController
public class ActivitiController extends BaseController {
	
	@Autowired
	private RepositoryService repositoryService;
	
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private TaskService taskService;
	
	
	@RequestMapping("/activiti/completeTask")
	public void completeTask(@RequestBody Map<String,Object> param) {
		Task task = taskService.createTaskQuery()
											.processInstanceId((String)param.get("piid"))
											.taskAssignee((String)param.get("loginacct"))
											.singleResult();
		taskService.complete(task.getId(),param);
	}
	
	@RequestMapping("/activiti/startProcessInstance")
	public String startProcessInstance(@RequestBody Member member) {
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
								.processDefinitionKey("workflow2")
								.latestVersion()
								.singleResult();
		
		Map<String,Object> variable = new HashMap<String,Object>();
		variable.put("loginacct", member.getLoginacct());
		
		ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId(),variable);
		return processInstance.getId();
	}
	
	@RequestMapping("activiti/deleteDeploy/{pdDeployId}")
	public void deleteDeploy(@PathVariable("pdDeployId")String pdDeployId) {
		repositoryService.deleteDeployment(pdDeployId,true);
		//cascade = true  级联
	}
	
	@RequestMapping("/activiti/loadImgById/{pdId}")
	public byte[] loadImgById(@PathVariable("pdId")String pdId) {
		//部署ID-->流程定义ID
		//从数据库中读取流程定义的图片
		//根据流程部署id和部署资源名称获取部署图片的输入流
		ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();
		ProcessDefinition pd = query.processDefinitionId(pdId).singleResult();
		InputStream in = repositoryService.getResourceAsStream(pd.getDeploymentId(), pd.getDiagramResourceName());
		ByteArrayOutputStream swapStream = new ByteArrayOutputStream();//内存缓存流
		byte[] buff = new byte[100];//buff用于存放循环读取的临时数据
		int rc = 0;
		try {
			while((rc = in.read(buff,0,100))>0) {
				swapStream.write(buff, 0, rc);
				
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		byte[] in_b = swapStream.toByteArray(); //in_b为转换之后的结果
		return in_b;
	}
	
	@RequestMapping("/activiti/deploy")
	public String deploy(@RequestParam("pdfile") MultipartFile file){
		try {
			Deployment deploy = repositoryService.createDeployment().addInputStream(file.getOriginalFilename(), file.getInputStream()).deploy();
			
			System.out.println(deploy);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return "falied";
	}
	
	@RequestMapping("/activiti/queryProcDefList")
	public List<Map<String,Object>> queryProcDefList(@RequestBody Map<String,Object> param){
		Integer startIndex = (Integer)param.get("startIndex");
		Integer pagesize = (Integer)param.get("pagesize");
		//
		ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
		//得到分页集合 里面都是一个个流程定义的对象
		List<ProcessDefinition> listPage = processDefinitionQuery.listPage(startIndex, pagesize);
		//手动生成相应的集合实例
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		for(ProcessDefinition pd : listPage) {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("pdId", pd.getId());
			map.put("pdName", pd.getName());
			map.put("pdVersion", pd.getVersion());
			map.put("pdKey", pd.getKey());
			map.put("pdDeployId", pd.getDeploymentId());
			list.add(map);
		}
		return list;
	}
	
	@RequestMapping("/activiti/countProcDef")
	public Integer countProDef(@RequestBody Map<String,Object>param) {
		ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
		Long count = processDefinitionQuery.count();
		return count.intValue();
	}
}
