package demo.com.beauty_salon_api.dto;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDateTime;

@Relation(collectionRelation = "appointments", itemRelation = "appointment")
public class AppointmentResponse extends RepresentationModel<AppointmentResponse> {
    private final Long id;
    private final AccommodationResponse accommodation;
    private final MasterResponse master;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final String status;
    private final LocalDateTime createdAt;

    public AppointmentResponse (Long id, AccommodationResponse accommodation, MasterResponse master, LocalDateTime startTime, LocalDateTime endTime, String status, LocalDateTime createdAt) {
        this.id = id;
        this.accommodation = accommodation;
        this.master = master;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public AccommodationResponse getAccommodation() {
        return accommodation;
    }

    public MasterResponse getMaster() {
        return master;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}