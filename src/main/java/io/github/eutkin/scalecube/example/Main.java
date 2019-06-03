package io.github.eutkin.scalecube.example;

import io.scalecube.services.Microservices;
import io.scalecube.services.discovery.ScalecubeServiceDiscovery;
import io.scalecube.services.transport.rsocket.RSocketServiceTransport;
import io.scalecube.services.transport.rsocket.RSocketTransportResources;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Microservices seed = Microservices
                .builder()
                .discovery(ScalecubeServiceDiscovery::new)
                .transport(opts -> opts
                        .resources(RSocketTransportResources::new)
                        .client(RSocketServiceTransport.INSTANCE::clientTransport)
                        .server(RSocketServiceTransport.INSTANCE::serverTransport)
                )
                .services(new GreetingServiceImpl())
                .startAwait();

        GreetingService greetingService = seed.call().api(GreetingService.class);

        Flux<String> input = Flux.<String>create(sink -> {
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                sink.next(line);
            }
            sink.complete();
        }).subscribeOn(Schedulers.newSingle("io"));

        greetingService.toUpperCase(input).doOnNext(System.out::println).blockLast();
    }
}
