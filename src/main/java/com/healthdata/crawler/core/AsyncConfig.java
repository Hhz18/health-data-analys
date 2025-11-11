package com.healthdata.crawler.core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync // 启用异步
public class AsyncConfig {
    //配置线程池来分配资源
    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5); // 核心线程池大小
        executor.setMaxPoolSize(10); // 最大线程池大小
        executor.setQueueCapacity(100); // 队列容量
        executor.setThreadNamePrefix("async-task-"); // 线程名称前缀
        executor.initialize();
        return executor;
    }
}
