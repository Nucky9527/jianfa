package com.atguigu.atcrowdfunding;

import java.util.List;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ch.qos.logback.core.net.SyslogOutputStream;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Activiti5ApplicationTests {

	@Autowired
	private ProcessEngine processEngine;
	
	@Autowired
	private RepositoryService repositoryService;
	
	@Autowired
	private RuntimeService runtimeService ;
	
	@Autowired
	private TaskService taskService ;
	
	
	@Autowired
	private HistoryService historyService;
	
	//7.历史流程实例查询
	@Test
	public void test7() {
		HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
						.processInstanceId("15001")  //指定具体流程实例id
						.finished() //查询流程实例是否结束
						.singleResult();  //查询唯一结果。
		System.out.println(historicProcessInstance);
	}
	
	//6.完成任务
	@Test
	public void test6() {
		List<Task> listPage = taskService.createTaskQuery()
					.processDefinitionKey("myProcess2")
					.taskAssignee("lisi")
					.listPage(0, 10);
		for (Task task : listPage) {
			System.out.println(task.getId() +" - " +task.getName());
			taskService.complete(task.getId()); 
		}		
	}
	
	
	//5.查询任务列表
	@Test
	public void test5() {
		List<Task> listPage = taskService.createTaskQuery()
					.processDefinitionKey("myProcess2")
					.taskAssignee("lisi")
					.listPage(0, 10);
		for (Task task : listPage) {
			System.out.println(task.getId() +" - " +task.getName());
		}		
	}
	
	
	//4.启动流程实例。就是一个具体请假。
	@Test
	public void test4() {
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
													.processDefinitionKey("email")
													.latestVersion()
													.singleResult();
		
		ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId());
		System.out.println(processInstance);
	}
	
	//3.查询流程定义   act_re_procdef
	@Test
	public void test3() {
		ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
		
		//分页查询
		int pageno = 1;			//起始页
		int pagesize = 10 ;		//每页的记录条数
		int startIndex = (pageno-1)*pagesize ;		//起始索引
		//遍历流程定义集合，得到流程定义对象
		List<ProcessDefinition> listPage = processDefinitionQuery.listPage(startIndex, pagesize);
		for (ProcessDefinition pd : listPage) {
			System.out.println(pd.getId() +" - " + pd.getVersion() +" - " + pd.getName() +" - " +pd.getKey());
		}
		
		long count = processDefinitionQuery.count();
		System.out.println(count);
		
	}
	
	
	//2.流程定义部署。将bpmn流程定义信息存储到相关表中。
	@Test
	public void test2() {
		Deployment deploy = repositoryService.createDeployment().addClasspathResource("email.bpmn").deploy();
		System.out.println(deploy);
	}
	
	
	//一.Caused by: java.lang.ClassNotFoundException: org.apache.ibatis.annotations.Mapper
	//引入mybatis框架jar包版本存在问题。
	//通过Maven依赖原则：先声明者优先解决jar包冲突。
	
	//二. Caused by: java.io.FileNotFoundException: class path resource [processes/] 
	//  cannot be resolved to URL because it does not exist
	
	//因为类路径下缺少processes文件夹。
	//在src/main/resources下创建processes文件夹，但是，必须文件夹中至少有一个文件。不能为空。
	//或者，在src/main/java下创建processes文件夹下,这个可以是空文件夹。
	//这个文件夹是用于自动部署流程定义的。可以将流程定义文件(bpmn)存放到该目录下。
	
	//1.创建流程引擎对象。自动创建25张流程表。processEngine(流程引擎)
	@Test
	public void test1() {
		System.out.println(processEngine);
	}

}
