package com.queue.demo.web;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.queue.demo.event.publisher.EventPublisherService;
import com.queue.demo.event.publisher.SimpleMessageEvent;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TestController {

	private final EventPublisherService eventPublisherService;

	@PostMapping("/transactional")
	public void publishEventTransactional(@RequestParam String msg) {
		eventPublisherService.publishTransactional(this,
			new SimpleMessageEvent(msg)
		);
	}

	@PostMapping("/")
	public void publishEvent(@RequestParam String msg) {
		eventPublisherService.publish(this,
			new SimpleMessageEvent(msg)
		);
	}
}
