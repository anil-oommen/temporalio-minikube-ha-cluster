package com.oom.temporal;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import java.util.Arrays;

@SpringBootApplication
@Slf4j
public class LauncherMain {
    public static void main(String[] args) {

        ApplicationContext ctx = SpringApplication.run(LauncherMain.class, args);
        //debugBeans(ctx);
    }

    private static void debugBeans(ApplicationContext ctx){
        String[] springBootBeans = ctx.getBeanDefinitionNames();
        Arrays.sort(springBootBeans);
        Arrays.stream(springBootBeans)
                .map(beanName-> String.format("bean://%s:%s",beanName,
                        ctx.getBean(beanName).getClass().toGenericString()))
                .forEach(System.out::println);
    }
}