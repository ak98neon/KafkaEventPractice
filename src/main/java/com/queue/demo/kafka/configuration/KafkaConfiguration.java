package com.queue.demo.kafka.configuration;

import java.util.Collections;
import java.util.Map;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
public class KafkaConfiguration {

	@Value("${spring.kafka.message-topic}")
	private String topic;

	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapServer;

	public Map<String, Object> kafkaProducerConfigs() {
		return DefaultKafkaComponentsBuilder.buildProducerConfigs(bootstrapServer);
	}

	public Map<String, Object> kafkaConsumerConfigs() {
		return DefaultKafkaComponentsBuilder.buildConsumerConfigs(bootstrapServer);
	}

	public ProducerFactory<String, String> kafkaProducerFactory() {
		StringSerializer stringSerializer = new StringSerializer();
		return new DefaultKafkaProducerFactory<>(kafkaProducerConfigs(), stringSerializer, stringSerializer);
	}

	@Bean
	@Primary
	public KafkaTemplate<String, String> kafkaTemplate() {
		return new KafkaTemplate<>(kafkaProducerFactory());
	}

	@Bean
	public KafkaConsumer<String, String> kafkaConsumer() {
		final KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(kafkaConsumerConfigs());
		kafkaConsumer.assign(
			Collections.singletonList(
				new TopicPartition(
					topic,
					0/*default partition*/)
			));
		return kafkaConsumer;
	}
}
