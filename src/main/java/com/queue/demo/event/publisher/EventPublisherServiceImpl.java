package com.queue.demo.event.publisher;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventPublisherServiceImpl implements EventPublisherService {

	private final ApplicationEventPublisher applicationEventPublisher;

	@Override
	public void publishTransactional(Object source, QueueEvent event) {
		applicationEventPublisher.publishEvent(new SynchronousTransactionalApplicationEvent<>(source, event));
	}

	@Override
	public void publish(Object source, QueueEvent event) {
		applicationEventPublisher.publishEvent(new SynchronousApplicationEvent<>(source, event));
	}

}
