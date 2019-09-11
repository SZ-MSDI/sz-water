package com.sz.water;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MainApplication {

	// 主启
	public static void main(String[] args) {
		System.out.println("启动成功...");
		System.out.println("12345678900");
		SpringApplication.run(MainApplication.class, args);
	}

}
