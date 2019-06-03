package io.github.eutkin.scalecube.example;

import io.scalecube.services.annotations.Service;
import io.scalecube.services.annotations.ServiceMethod;
import reactor.core.publisher.Flux;

import static io.github.eutkin.scalecube.example.GreetingService.NAMESPACE;

@Service(NAMESPACE)
public interface GreetingService {

    String NAMESPACE = "greeting";

    @ServiceMethod("upper-case")
    Flux<String> toUpperCase(Flux<String> input);



}
