package com.example;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
public class PetstoreApplication
{
	private static final Logger log = LoggerFactory.getLogger(PetstoreApplication.class);

	public static void main(String[] args)
	{
		SpringApplication.run(PetstoreApplication.class, args);
	}

	@PostConstruct
	private void start()
	{
		log.info("################## I'm alive again - what's up? ##################");
	}

	@PreDestroy
	private void shutdown()
	{
		log.info("################## I'm going down - c-ya! ##################");
	}
}
