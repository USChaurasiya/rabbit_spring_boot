package com.uma.rabbitmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.uma.rabbitmq.client.Consumer;
import com.uma.rabbitmq.client.Producer;

@SpringBootApplication
public class RabbitMqApplication {

	public static void main(String[] args) {
		SpringApplication.run(RabbitMqApplication.class, args);
		
		for (int i = 1; i < 6; i++) {
			final String message = "This is message numero " + i;
			Producer producer = new Producer(message);
			new Thread(producer).start();
		}

		Thread consumerThread = new Thread(new Consumer());
		consumerThread.start();

	}
}
