package com.frenzi.firstSpring;

import com.frenzi.firstSpring.Service.CrashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableAutoConfiguration
@EnableAsync(proxyTargetClass=true)
@EnableTransactionManagement
public class FirstSpringApplication {


	public static void main(String[] args) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		SpringApplication.run(FirstSpringApplication.class, args);
	}

	/*@Bean
	CrashService myBean () {
		CrashService crashService = new CrashService();
		return crashService;
	}*/
}
