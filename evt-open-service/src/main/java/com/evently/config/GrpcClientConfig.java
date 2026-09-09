package com.evently.config;

import com.evently.grpc.EventServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcClientConfig {

    private ManagedChannel channel;

    @Value("${evt-core-service.host:localhost}")
    private String coreServiceHost;

    @Value("${evt-core-service.port:9090}")
    private int coreServicePort;

    @Bean
    public ManagedChannel eventServiceChannel(
            @Value("${grpc.client.event-service.host}") String host,
            @Value("${grpc.client.event-service.port}") int port
    ) {
        this.channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();
        return channel;
    }

    @Bean
    public EventServiceGrpc.EventServiceBlockingStub eventServiceBlockingStub(ManagedChannel eventServiceChannel) {
        return EventServiceGrpc.newBlockingStub(eventServiceChannel);
    }
}