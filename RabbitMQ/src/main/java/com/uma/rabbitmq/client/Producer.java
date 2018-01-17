package com.uma.rabbitmq.client;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.uma.rabbitmq.config.RabbitMQConfiguration;

public class Producer implements Runnable {

	private final String message;
	 
	 private static final String EXCHANGE_NAME = "test";
	 
	 private static final String ROUTING_KEY = "test";
	 
	 public Producer(final String message) {
	 this.message = message;
	 }
	 
	 @Override
	 public void run() {
		 ConnectionFactory factory = new ConnectionFactory();
		 factory.setUsername(RabbitMQConfiguration.USERNAME);
		 factory.setPassword(RabbitMQConfiguration.PASSWORD);
		 factory.setHost(RabbitMQConfiguration.HOSTNAME);
		 factory.setPort(RabbitMQConfiguration.PORT);
		 Connection conn;
		 try {
		 conn = factory.newConnection();
		 Channel channel = conn.createChannel();
		 
		 channel.exchangeDeclare(EXCHANGE_NAME, "direct", true);
		 String queueName = channel.queueDeclare().getQueue();
		 channel.queueBind(queueName, EXCHANGE_NAME, ROUTING_KEY);
		 System.out.println("Producing message: " + message + " in thread: " + Thread.currentThread().getName());
		 channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, null, message.getBytes());
		 
		 channel.close();
		 conn.close();
		 } catch (IOException e) {
		 e.printStackTrace();
		 }
		 }
}
