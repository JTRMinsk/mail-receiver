package com.huytmb.mail.receiver.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.boot.autoconfigure.task.TaskExecutionProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync  //多线程的配置类， 类似extend Thread或者实现Runnable接口
                //在别处使用@Async("asyncTaskExecutor") 就可以用同线程池的资源
public class AsyncConfiguration implements AsyncConfigurer {

    private final Logger log = LoggerFactory.getLogger(AsyncConfiguration.class);

    private final TaskExecutionProperties taskExecutionProperties;

    public AsyncConfiguration(TaskExecutionProperties taskExecutionProperties) {
        this.taskExecutionProperties = taskExecutionProperties;
    }

    @Override
    @Bean(name = "asyncTaskExecutor")//1, 用于生成异步线程池 async,异步
    public Executor getAsyncExecutor() {
        log.debug("Creating Async Task Executor");
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(taskExecutionProperties.getPool().getCoreSize());
        executor.setMaxPoolSize(taskExecutionProperties.getPool().getMaxSize());
        executor.setQueueCapacity(taskExecutionProperties.getPool().getQueueCapacity());
        executor.setThreadNamePrefix(taskExecutionProperties.getThreadNamePrefix());
        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new SimpleAsyncUncaughtExceptionHandler();
    }
}
