package com.queue.demo.event.publisher;

public class SynchronousTransactionalApplicationEvent<T extends QueueEvent> extends GenericApplicationEvent<T> {

	private static final long serialVersionUID = 5583530618320630325L;

	public SynchronousTransactionalApplicationEvent(Object source, T event) {
		super(source, event);
	}

}
