package com.evently.config;

import com.evently.grpc.EventServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcClientConfig {

    @Value("${evt-core-service.host:localhost}")
    private String coreServiceHost;

    @Value("${evt-core-service.port:9090}")
    private int coreServicePort;

    @Bean
    public ManagedChannel eventServiceChannel() {
        return ManagedChannelBuilder.forAddress(coreServiceHost, coreServicePort)
                .usePlaintext()
                .build();
    }

    @Bean
    public EventServiceGrpc.EventServiceBlockingStub eventServiceStub(ManagedChannel eventServiceChannel) {
        return EventServiceGrpc.newBlockingStub(eventServiceChannel);
    }
}