package com.zl.fund;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
@SpringBootApplication
@MapperScan("com.zl.fund.mapper")
@EnableTransactionManagement //开启事务功能
@EnableAsync//开启异步注解功能
@EnableScheduling//开启基于注解的定时任务
public class FundTradingCustomerApplication {

	public static void main(String[] args) {
		SpringApplication.run(FundTradingCustomerApplication.class, args);
	}
	
}
