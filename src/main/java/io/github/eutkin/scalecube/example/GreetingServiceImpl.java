package io.github.eutkin.scalecube.example;

import reactor.core.publisher.Flux;

public class GreetingServiceImpl  implements GreetingService {

    @Override
    public Flux<String> toUpperCase(Flux<String> input) {
        return input.map(String::toUpperCase);
    }
}
