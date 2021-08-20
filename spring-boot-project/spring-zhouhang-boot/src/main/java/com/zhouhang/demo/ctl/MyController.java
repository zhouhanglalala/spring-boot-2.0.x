package com.zhouhang.demo.ctl;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zhouhang.demo.service.MyPublisherService;


/**
 * @author zhouhang
 */
@RestController
public class MyController {
    @Resource
	MyPublisherService service;

    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name,
                       @RequestParam(value = "permission", defaultValue = "经理") String permission) {
        // 请求来的时候 service 已经有值了 看一下启动过程
        return service.deal(name);
    }

}
