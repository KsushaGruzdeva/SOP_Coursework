package com.example.beauty_salon.services.impl;

import com.example.beauty_salon.config.RabbitMQConfig;
import com.example.beauty_salon.services.AccommodationService;
import com.example.beauty_salon.services.AppointmentService;
import com.example.beauty_salon.services.MasterService;
import com.example.beauty_salon.storage.InMemoryStorage;
import demo.com.beauty_salon_api.dto.*;
import demo.com.beauty_salon_api.exception.ResourceNotFoundException;
import org.example.events.AppointmentCreatedEvent;
import org.example.events.AppointmentDeletedEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class AppointmentServiceImpl implements AppointmentService {
    private final InMemoryStorage storage;
    private final AccommodationService accommodationService;
    private final MasterService masterService;
    private final RabbitTemplate rabbitTemplate; // Внедряем RabbitTemplate

    public AppointmentServiceImpl(InMemoryStorage storage, AccommodationService accommodationService, MasterService masterService, RabbitTemplate rabbitTemplate) {
        this.storage = storage;
        this.accommodationService = accommodationService;
        this.masterService = masterService;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public PagedResponse<AppointmentResponse> findAll(Long masterId, int page, int size) {
        // Получаем стрим всех книг
        Stream<AppointmentResponse> appointmentsStream = storage.appointments.values().stream()
                .sorted((b1, b2) -> b1.getId().compareTo(b2.getId())); // Сортируем для консистентности

        // Фильтруем, если указан masterId
        if (masterId != null) {
            appointmentsStream = appointmentsStream.filter(appointment -> appointment.getMaster() != null && appointment.getMaster().getId().equals(masterId));
        }

        List<AppointmentResponse> allAppointments = appointmentsStream.toList();

        // Выполняем пагинацию
        int totalElements = allAppointments.size();
        int totalPages = (int) Math.ceil((double) totalElements / size);
        int fromIndex = page * size;
        int toIndex = Math.min(fromIndex + size, totalElements);

        List<AppointmentResponse> pageContent = (fromIndex > toIndex) ? List.of() : allAppointments.subList(fromIndex, toIndex);

        return new PagedResponse<>(pageContent, page, size, totalElements, totalPages, page >= totalPages - 1);
    }

    @Override
    public AppointmentResponse findById(Long id) {
        return Optional.ofNullable(storage.appointments.get(id))
                .orElseThrow(() -> new ResourceNotFoundException("Appointment", id));
    }

    @Override
    public AppointmentResponse create(AppointmentRequest request) {
        // Находим мастера, если не найден - будет исключение
        MasterResponse master = masterService.findById(request.masterId());
        AccommodationResponse accommodation = accommodationService.findById(request.accommodationId());

        long id = storage.appointmentSequence.incrementAndGet();
        var appointment = new AppointmentResponse(
                id,
                accommodation,
                master,
                request.startTime(),
                LocalDateTime.now(),
                "",
                LocalDateTime.now()

        );
        storage.appointments.put(id, appointment);

        // Тут публикуем событие

        AppointmentCreatedEvent event = new AppointmentCreatedEvent(
                id,
                request.startTime().toString(),
                accommodation.getName(),
                master.getFullName()
        );
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY_APPOINTMENT_CREATED, event);

        return appointment;
    }

    @Override
    public AppointmentResponse update(Long id, AppointmentRequest request) {
        AppointmentResponse existingAppointment = findById(id); // Проверяем, что книга существует

        var updatedAppointment = new AppointmentResponse(
                id,
                existingAppointment.getAccommodation(),
                existingAppointment.getMaster(),
                request.startTime(),
                existingAppointment.getEndTime(),
                existingAppointment.getStatus(),
                existingAppointment.getCreatedAt() // Дата создания не меняется
        );
        storage.appointments.put(id, updatedAppointment);
        return updatedAppointment;
    }

    @Override
    public void delete(Long id) {
        findById(id);
        storage.appointments.remove(id);
        AppointmentDeletedEvent event = new AppointmentDeletedEvent(id);
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY_APPOINTMENT_DELETED, event);
    }
}
