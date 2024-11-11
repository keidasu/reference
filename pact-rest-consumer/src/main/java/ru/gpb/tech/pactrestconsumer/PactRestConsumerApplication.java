package ru.gpb.tech.pactrestconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class PactRestConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PactRestConsumerApplication.class, args);
	}

}
