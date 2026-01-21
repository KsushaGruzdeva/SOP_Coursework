package org.example.auditservice.listeners;

import com.rabbitmq.client.Channel;
import org.example.events.AccommodationRatedEvent;
import org.example.events.AppointmentCreatedEvent;
import org.example.events.AppointmentDeletedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class AppointmentEventListener {
    private static final Logger log = LoggerFactory.getLogger(AppointmentEventListener.class);
    private static final String EXCHANGE_NAME = "appointments-exchange";
    private static final String QUEUE_NAME = "notification-queue";
    private final Set<Long> processedAppointmentCreations = ConcurrentHashMap.newKeySet();

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(
                            name = QUEUE_NAME,
                            durable = "true",
                            // если что-то пойдет не так, отправляем в 'dlx-exchange'
                            arguments = {
                                    @Argument(name = "x-dead-letter-exchange", value = "dlx-exchange"),
                                    @Argument(name = "x-dead-letter-routing-key", value = "dlq.notifications")
                            }),
                    exchange = @Exchange(name = EXCHANGE_NAME, type = "topic", durable = "true"),
                    key = "appointment.created"
            )
    )
    // Используем @Payload для явного указания параметра сообщения
    public void handleAppointmentCreatedEvent(@Payload AppointmentCreatedEvent event, Channel channel,
                                       @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
        try {
            if (!processedAppointmentCreations.add(event.appointmentId())) {
                log.warn("Duplicate event received for appointmentId: {}", event.appointmentId());
                channel.basicAck(deliveryTag, false);
                return;
            }
            log.info("Received AppointmentCreatedEvent: {}", event);
            if (event.accommodationName() != null && event.accommodationName().equalsIgnoreCase("маникюр")) {
                throw new RuntimeException("Simulating processing error for DLQ test");
            }
            // Логика отправки уведомления...
            log.info("Notification sent for new appointment '{}'!", event.accommodationName());
            // Отправляем подтверждение брокеру
            channel.basicAck(deliveryTag, false);

        } catch (Exception e) {
            log.error("Failed to process event: {}. Sending to DLQ.", event, e);
            // Отправляем nack и НЕ просим вернуть в очередь (requeue=false)
            channel.basicNack(deliveryTag, false, false);
        }
    }

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(
                            name = QUEUE_NAME,
                            durable = "true",
                            arguments = {
                                    @Argument(name = "x-dead-letter-exchange", value = "dlx-exchange"),
                                    @Argument(name = "x-dead-letter-routing-key", value = "dlq.notifications")
                            }),
                    exchange = @Exchange(name = EXCHANGE_NAME, type = "topic", durable = "true"),
                    key = "appointment.deleted"
            )
    )
    public void handleAppointmentDeletedEvent(@Payload AppointmentDeletedEvent event, Channel channel,
                                       @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
        try {
            log.info("Received AppointmentDeletedEvent: {}", event.appointmentId());
            // Логика отмены уведомлений...
            log.info("Notifications cancelled for deleted appointmentId {}!", event.appointmentId());
            channel.basicAck(deliveryTag, false);

        } catch (Exception e) {
            log.error("Failed to process event: {}. Sending to DLQ.", event, e);
            channel.basicNack(deliveryTag, false, false);
        }
    }

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(name = "notification-queue.dlq", durable = "true"),
                    exchange = @Exchange(name = "dlx-exchange", type = "topic", durable = "true"),
                    key = "dlq.notifications"
            )
    )
    public void handleDlqMessages(Object failedMessage) {
        log.error("!!! Received message in DLQ: {}", failedMessage);
        // Здесь может быть логика оповещения администраторов
    }

    @RabbitListener(
            bindings = @QueueBinding(
                    // Уникальное имя очереди для уведомлений!
                    value = @Queue(name = "q.audit.analytics", durable = "true"),
                    exchange = @Exchange(name = "analytics-fanout", type = "fanout")
            )
    )
    public void handleRating(AccommodationRatedEvent event) {
        log.info("NOTIFY: accommodation {} has new rating: {}", event.accommodationId(), event.score());
    }
}
