package com.example.CoinApp;

import com.example.CoinApp.config.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@Import(AppConfig.class)
public class CoinAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoinAppApplication.class, args);

	}

}
