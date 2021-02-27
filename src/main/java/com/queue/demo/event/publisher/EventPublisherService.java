package com.queue.demo.event.publisher;

public interface EventPublisherService {

	void publishTransactional(Object source, QueueEvent event);

	void publish(Object source, QueueEvent event);

}
