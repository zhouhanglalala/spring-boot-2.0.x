package com.zhouhang.demo.service;

import javax.annotation.Resource;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 * @author zhouhang
 */
@Service
public class MyPublisherService {

    @Resource
    private ApplicationEventPublisher eventListener;

    public String deal(String name) {
        eventListener.publishEvent(name);
        return String.format("Hello %s!", name);
    }




}
