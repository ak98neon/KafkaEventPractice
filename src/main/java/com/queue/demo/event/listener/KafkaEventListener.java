package com.queue.demo.event.listener;

import com.queue.demo.event.publisher.SynchronousApplicationEvent;
import com.queue.demo.event.publisher.SynchronousTransactionalApplicationEvent;

public interface KafkaEventListener {

	void handleSynchronousEvent(SynchronousApplicationEvent<?> event);

	void handleSynchronousTransactionEvent(SynchronousTransactionalApplicationEvent<?> event);

}
