package com.zhouhang.demo.service;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class MyListenerService {


    @EventListener
    public void beforeCall(String name) {
        System.out.println(name+"叫我");
    }
}

