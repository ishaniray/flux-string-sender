package org.cerner.flux;

import java.time.Duration;

import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;

public class Sender {

	public void sendFlux() {
		Flux<String> daysOfTheWeek = Flux.just("Monday", "Tuesday", "Wednesday", "Thursday", "Friday")
				.delayElements(Duration.ofMillis(500));

		WebClient.create("http://localhost:8083").method(HttpMethod.POST).uri("/")
				.body(BodyInserters.fromPublisher(daysOfTheWeek.map(day -> day + "\n"), String.class)).retrieve()
				.bodyToMono(Void.class).block();
	}
}
