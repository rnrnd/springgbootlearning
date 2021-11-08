package com.example.demo.service;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class TestService implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public String test(){
        return applicationContext.getEnvironment().getProperty("key0") + " " + applicationContext.getEnvironment().getProperty("key1") + " " + applicationContext.getEnvironment().getProperty("key2");
    }
}
