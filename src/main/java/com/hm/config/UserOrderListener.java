package com.hm.config;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;

@Component("userOrderListener")
public class UserOrderListener implements ChannelAwareMessageListener {
	@Autowired
	private ConcurrencyService concurrencyService;

	@Override
	public void onMessage(Message message, Channel channel) throws Exception {
		long tag = message.getMessageProperties().getDeliveryTag();
		try {
			byte[] body = message.getBody();
			String mobile = new String(body, "UTF-8");
			// System.out.println("监听到抢单手机号：" + mobile);
			concurrencyService.manageRobbing(mobile);
			channel.basicAck(tag, true);
		} catch (Exception e) {
			System.out.println("用户商城抢单发生异常");
			channel.basicReject(tag, false);
		}
	}

}
