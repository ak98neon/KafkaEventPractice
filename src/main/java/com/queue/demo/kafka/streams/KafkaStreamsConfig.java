package com.queue.demo.kafka.streams;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.queue.demo.entity.User;

@Configuration
@EnableKafkaStreams
public class KafkaStreamsConfig {

	@Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
	public KafkaStreamsConfiguration kStreamsConfigs() {
		Map<String, Object> props = new HashMap<>();
		props.put(StreamsConfig.APPLICATION_ID_CONFIG, "id");
		props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
		props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
		return new KafkaStreamsConfiguration(props);
	}

	@Bean
	public Serde<User> userSerde() {
		return Serdes.serdeFrom(new JsonSerializer<>(), new JsonDeserializer<>(User.class));
	}

	@Bean
	public KStream<String, User> kStream(StreamsBuilder kStreamBuilder) {
		KStream<String, String> stream = kStreamBuilder
			.stream("src", Consumed.with(Serdes.String(), Serdes.String()));

		KStream<String, User> userStream = stream
			.mapValues(this::getUserFromString)
			.filter((key, value) -> value.getBalance() <= 0);

		userStream.to("out", Produced.with(Serdes.String(), userSerde()));
		return userStream;
	}

	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}

	User getUserFromString(String userString) {
		User user = null;
		try {
			user = objectMapper().readValue(userString, User.class);
		} catch (JsonProcessingException e) {
			System.err.println(e.getMessage());
		}
		return user;
	}
}
