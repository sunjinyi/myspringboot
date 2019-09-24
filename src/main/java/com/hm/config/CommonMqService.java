package com.hm.config;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class CommonMqService {

	@Autowired
	private RabbitTemplate rabbitTemplate;
	@Autowired
	private Environment env;

	public void sendMessage(String mobile) {
		try {
			// System.out.println(env.getProperty("user.order.exchange.name"));
			rabbitTemplate.setExchange(env.getProperty("user.order.exchange.name"));
			rabbitTemplate.setRoutingKey(env.getProperty("user.order.routing.key.name"));
			Message message = MessageBuilder.withBody(mobile.getBytes("UTF-8"))
					.setDeliveryMode(MessageDeliveryMode.PERSISTENT).build();
			rabbitTemplate.send(message);
		} catch (Exception e) {
			System.out.println("发送抢单信息队列发生异常mobile：" + mobile);
		}
	}

}
