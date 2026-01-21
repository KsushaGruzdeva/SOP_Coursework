package com.example.beauty_salon.storage;

import demo.com.beauty_salon_api.dto.AccommodationResponse;
import demo.com.beauty_salon_api.dto.AppointmentResponse;
import demo.com.beauty_salon_api.dto.MasterResponse;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class InMemoryStorage {
    public final Map<Long, AppointmentResponse> appointments = new ConcurrentHashMap<>();
    public final Map<Long, MasterResponse> masters = new ConcurrentHashMap<>();
    public final Map<Long, AccommodationResponse> accommodations = new ConcurrentHashMap<>();

    public final AtomicLong appointmentSequence = new AtomicLong(0);
    public final AtomicLong masterSequence = new AtomicLong(0);
    public final AtomicLong accommodationSequence = new AtomicLong(0);

    @PostConstruct
    public void init() {
        // Создаем несколько услуг
        AccommodationResponse accommodation1 = new AccommodationResponse(accommodationSequence.incrementAndGet(), "Маникюр", Long.parseLong("2500"), LocalDateTime.of(2025, Month.DECEMBER, 25, 18, 30, 0));
        AccommodationResponse accommodation2 = new AccommodationResponse(accommodationSequence.incrementAndGet(), "Массаж", Long.parseLong("100000000"), LocalDateTime.of(2025, Month.DECEMBER, 25, 18, 30, 0));
        accommodations.put(accommodation1.getId(), accommodation1);
        accommodations.put(accommodation2.getId(), accommodation2);

        // Создаем несколько мастеров
        long masterId1 = masterSequence.incrementAndGet();

        MasterResponse master1 = new MasterResponse(masterId1, "Груздева Ксения", "978-5-389-06259-8", "master1@mail.com", 3, List.of(accommodation1));
        masters.put(masterId1, master1);

        long masterId2 = masterSequence.incrementAndGet();
        masters.put(masterId2, new MasterResponse(masterId2, "Васильева Елизавета", "978-5-389-06259-9", "master2@mail.com", 3, List.of(accommodation1, accommodation2)));

        //Создаем несколько записей
        long appointmentId1 = appointmentSequence.incrementAndGet();
        appointments.put(appointmentId1, new AppointmentResponse(appointmentId1, accommodation1, master1, LocalDateTime.of(2025, Month.NOVEMBER, 25, 18, 30, 0), LocalDateTime.of(2025, Month.NOVEMBER, 25, 20, 0, 0), "Create" ,LocalDateTime.now()));
    }
}
