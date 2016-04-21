package hello;

import java.util.concurrent.CountDownLatch;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;

import com.rabbitmq.client.Channel;

public class Receiver implements ChannelAwareMessageListener {

	private CountDownLatch latch = new CountDownLatch(1);

	public void receiveMessage(String message) {
		System.out.println("Received <" + message + ">");
		latch.countDown();
	}

	public CountDownLatch getLatch() {
		return latch;
	}
	
	
	@Override
	public void onMessage(Message message, Channel channel) throws Exception {
		System.out.println("Received <" + new String(message.getBody()) + ">");
		channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
	}

}
