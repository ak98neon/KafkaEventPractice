package com.queue.demo.event.publisher;

public class SynchronousApplicationEvent<T extends QueueEvent> extends GenericApplicationEvent<T> {

	private static final long serialVersionUID = 5414904994835099672L;

	public SynchronousApplicationEvent(Object source, T event) {
		super(source, event);
	}

}
