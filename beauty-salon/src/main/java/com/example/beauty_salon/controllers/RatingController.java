package com.example.beauty_salon.controllers;

import com.demo.AnalyticsServiceGrpc;
import com.demo.AccommodationRatingRequest;
import com.example.beauty_salon.config.RabbitMQConfig;
import io.grpc.StatusRuntimeException;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.example.events.AccommodationRatedEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RatingController {

    @GrpcClient("analytics-service")
    private AnalyticsServiceGrpc.AnalyticsServiceBlockingStub analyticsStub;

    private final RabbitTemplate rabbitTemplate;

    public RatingController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostMapping("/api/accommodation/{id}/rate")
    public String rateUser(@PathVariable Long id) {
        try {
            // Вызов gRPC с обработкой исключений
            var request = AccommodationRatingRequest.newBuilder()
                    .setAccommodationId(id)
                    .build();

            var gRpcResponse = analyticsStub.calculateAccommodationRating(request);

            // Отправка события в Fanout (только если gRPC успешен)
            var event = new AccommodationRatedEvent(
                    gRpcResponse.getAccommodationId(),
                    gRpcResponse.getRatingScore(),
                    gRpcResponse.getVerdict()
            );

            rabbitTemplate.convertAndSend(RabbitMQConfig.FANOUT_EXCHANGE, "", event);

            return "Rating calculated: " + gRpcResponse.getRatingScore();

        } catch (StatusRuntimeException e) {
            // gRPC сервис недоступен - возвращаем -1 и НЕ отправляем в RabbitMQ
            System.out.println("⚠️ gRPC сервис недоступен! Возвращаю рейтинг -1");
            System.out.println("   Ошибка: " + e.getStatus().getDescription());

            return "Rating calculated: -1 (gRPC service unavailable)";
        }
    }
}
