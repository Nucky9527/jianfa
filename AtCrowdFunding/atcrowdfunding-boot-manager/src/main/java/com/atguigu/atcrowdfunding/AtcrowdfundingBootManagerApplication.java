package com.atguigu.atcrowdfunding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@ServletComponentScan
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class AtcrowdfundingBootManagerApplication {
	
	@LoadBalanced
	@Bean
	public RestTemplate restTemplate(	) {
		return new RestTemplate();
	}
		public static void main(String[] args) {
		SpringApplication.run(AtcrowdfundingBootManagerApplication.class, args);
	}
}
