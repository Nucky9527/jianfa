package com.atguigu.atcrowdfunding.service;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("atcrowdfunding-boot-activiti-service")
public interface ActivitiService {
	
	//远程调用传递参数情况
	//1、简单参数  采用占位符即可
	//2、复杂参数（User，Map，List等） 采用@RequestBody
	//将数据存放到请求体中提交给远程服务。
	@RequestMapping("/activiti/queryProcDefList")
	public List<Map<String,Object>> queryProcDefList(@RequestBody Map<String,Object>param);
	
	@RequestMapping("/activiti/countProcDef")
	public Integer countProcDef(@RequestBody Map<String,Object>param);
	
	@RequestMapping("/activiti/deleteDeploy/{pdDeployId}")
	public void deleteDeploy(@PathVariable("pdDeployId") String pdDeployId);
	
	
	
}
