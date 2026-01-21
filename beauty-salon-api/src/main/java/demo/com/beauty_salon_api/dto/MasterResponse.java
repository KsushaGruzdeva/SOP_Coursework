package demo.com.beauty_salon_api.dto;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.util.List;
import java.util.Objects;

@Relation(collectionRelation = "masters", itemRelation = "master")
public class MasterResponse extends RepresentationModel<MasterResponse> {
    private final Long id;
    private final String fullName;
    private final String phone;
    private final String email;
    private final Integer experienceYears;
    private final List<AccommodationResponse> accommodations;

    public MasterResponse(Long id, String fullName, String phone, String email, Integer experienceYears, List<AccommodationResponse> accommodations) {
        this.id = id;
        this.fullName = fullName;
        this.phone = phone;
        this.email = email;
        this.experienceYears = experienceYears;
        this.accommodations = accommodations;
    }

    public Long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public Integer getExperienceYears() {
        return experienceYears;
    }

    public List<AccommodationResponse> getAccommodations() {
        return accommodations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        MasterResponse that = (MasterResponse) o;
        return Objects.equals(id, that.id) && Objects.equals(fullName, that.fullName)
                && Objects.equals(phone, that.phone) && Objects.equals(email, that.email) && Objects.equals(experienceYears, that.experienceYears)
                && Objects.equals(accommodations, that.accommodations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, fullName, phone, email, experienceYears);
    }
}