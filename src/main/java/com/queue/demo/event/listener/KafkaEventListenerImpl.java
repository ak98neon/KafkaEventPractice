package com.queue.demo.event.listener;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.queue.demo.event.publisher.GenericApplicationEvent;
import com.queue.demo.event.publisher.SimpleMessageEvent;
import com.queue.demo.event.publisher.SynchronousApplicationEvent;
import com.queue.demo.event.publisher.SynchronousTransactionalApplicationEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class KafkaEventListenerImpl implements KafkaEventListener {

	private final KafkaTemplate<String, String> kafkaTemplate;

	@Value("${spring.kafka.message-topic}")
	private String topic;

	@EventListener
	@Override
	public void handleSynchronousEvent(SynchronousApplicationEvent<?> event) {
		send(event);
	}

	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, fallbackExecution = true)
	@Override
	public void handleSynchronousTransactionEvent(SynchronousTransactionalApplicationEvent<?> event) {
		send(event);
	}

	private void send(GenericApplicationEvent<?> event) {
		SimpleMessageEvent queueEvent = (SimpleMessageEvent) event.getEvent();
		kafkaTemplate.send(topic, queueEvent.getMsg());
	}
}
