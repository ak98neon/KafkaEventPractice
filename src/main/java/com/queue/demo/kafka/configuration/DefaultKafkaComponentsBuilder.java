package com.queue.demo.kafka.configuration;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;

public interface DefaultKafkaComponentsBuilder {

	static Map<String, Object> buildConsumerConfigs(String bootstrapServers) {
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, "test-1");
		return props;
	}

	static Map<String, Object> buildProducerConfigs(String bootstrapServers) {
		Map<String, Object> props = new HashMap<>();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		props.put(ProducerConfig.RETRIES_CONFIG, 0);
		props.put(ProducerConfig.LINGER_MS_CONFIG, 100);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		return props;
	}
}
