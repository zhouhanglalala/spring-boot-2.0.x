package com.zhouhang.demo;


import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zhouhang
// */ //
@SpringBootApplication
public class SpringbootDemoApplication {

	public static void main(String[] args) {
//		System.out.println(MappingJackson2HttpMessageConverter.class);
//		System.out.println(111);
		SpringApplication.run(SpringbootDemoApplication.class, args);
	}

}

            