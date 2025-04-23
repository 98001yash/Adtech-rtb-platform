package com.company.Adtech_rtb_platform.Bid_handler_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;

@SpringBootApplication
@EnableFeignClients
public class BidHandlerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BidHandlerServiceApplication.class, args);
	}

}
