package com.queue.demo.event.publisher;

import org.springframework.context.ApplicationEvent;

public abstract class GenericApplicationEvent<T> extends ApplicationEvent {

	private static final long serialVersionUID = -6032735192590415310L;

	private final T event;

	public GenericApplicationEvent(Object source, T event) {
		super(source);
		this.event = event;
	}

	public T getEvent() {
		return event;
	}

	@Override
	public String toString() {
		return "GenericApplicationEvent{" + "event=" + event + ", source=" + source + '}';
	}

}
