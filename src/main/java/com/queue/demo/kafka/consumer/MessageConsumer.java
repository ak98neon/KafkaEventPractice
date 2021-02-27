package com.queue.demo.kafka.consumer;

import java.time.Duration;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MessageConsumer {

	private final KafkaConsumer<String, String> kafkaConsumer;

	@EventListener(ApplicationReadyEvent.class)
	public void consumeMessage() {
		while (true) {
			final ConsumerRecords<String, String> poll = kafkaConsumer.poll(Duration.ofSeconds(10));
			if (!poll.isEmpty()) {
				for (ConsumerRecord<String, String> record : poll) {
					System.out.println("[" + kafkaConsumer.groupMetadata().groupId() + "]: " + record.value());
				}
			}
		}
	}
}
