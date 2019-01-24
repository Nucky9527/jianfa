package com.atguigu.atcrowdfunding;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import feign.Retryer;
@EnableFeignClients
@EnableDiscoveryClient
@MapperScan("com.atguigu.atcrowdfunding.dao")
@EnableTransactionManagement
@SpringBootApplication
public class AtcrowdfundingBootMemberServiceApplication {
	
	@Bean
	public Retryer feignRetryer() {
		return Retryer.NEVER_RETRY;
		
	}

	public static void main(String[] args) {
		SpringApplication.run(AtcrowdfundingBootMemberServiceApplication.class, args);
	}
}
