package com.healthdata;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

//@SpringBootApplication
@SpringBootApplication(scanBasePackages = "com.healthdata")
@EnableRedisRepositories
@MapperScan({"com.healthdata.mappers", "com.healthdata.service.impl"})

public class HealthDataSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(HealthDataSystemApplication.class, args);
	}

}
