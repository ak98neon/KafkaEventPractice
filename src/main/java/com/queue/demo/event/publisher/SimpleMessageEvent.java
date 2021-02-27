package com.queue.demo.event.publisher;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class SimpleMessageEvent extends QueueEvent {

	private String msg;
}
