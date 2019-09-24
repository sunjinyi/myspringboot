package com.hm.config;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class RabbitMQConfig {
	@Autowired
	private CachingConnectionFactory connectionFactory;
	@Autowired
	private Environment env;
	@Autowired
	private UserOrderListener userOrderListener;

	@Bean
	public Queue userOrderQueue() {
		return new Queue(env.getProperty("user.order.queue.name"), true);
	}

	@Bean
	public TopicExchange userOrderExchange() {
		return new TopicExchange(env.getProperty("user.order.exchange.name"), true, false);
	}

	@Bean
	public Binding userOrderBinding() {
		return BindingBuilder.bind(userOrderQueue()).to(userOrderExchange())
				.with(env.getProperty("user.order.routing.key.name"));
	}

	@Bean
	public SimpleMessageListenerContainer listenerContainer(@Qualifier("userOrderQueue") Queue queue) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setMessageConverter(new Jackson2JsonMessageConverter());

		container.setConcurrentConsumers(env.getProperty("spring.rabbitmq.listener.simple.concurrency", Integer.class));
		container.setMaxConcurrentConsumers(
				env.getProperty("spring.rabbitmq.listener.simple.max-concurrency", Integer.class));
		container.setPrefetchCount(env.getProperty("spring.rabbitmq.listener.simple.prefetch", Integer.class));

		// TODO:消息确认机制
		container.setQueues(queue);
		container.setMessageListener(userOrderListener);
		container.setAcknowledgeMode(AcknowledgeMode.MANUAL);

		return container;
	}
}
