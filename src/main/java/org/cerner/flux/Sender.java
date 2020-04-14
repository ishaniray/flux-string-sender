package org.cerner.flux;

import java.time.Duration;

import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Sender {

	public static final int N = 1; // number of Flux objects to send

	public void sendFlux() {
		Flux<String> daysOfTheWeek = Flux.just("Monday", "Tuesday", "Wednesday", "Thursday", "Friday")
				.delayElements(Duration.ofMillis(500));

		Mono<Void> monoResponse = WebClient.create("http://localhost:8083").method(HttpMethod.POST).uri("/")
				.body(BodyInserters.fromPublisher(daysOfTheWeek.map(day -> day + "\n"), String.class)).retrieve()
				.bodyToMono(Void.class);

		for (int i = 0; i < N; ++i) {
			monoResponse.subscribe(); // use block() for the blocking pattern of execution
		}
	}
}
