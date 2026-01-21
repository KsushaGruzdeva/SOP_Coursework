package com.example.beauty_salon.graphql;

import com.example.beauty_salon.services.AccommodationService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import demo.com.beauty_salon_api.dto.AccommodationRequest;
import demo.com.beauty_salon_api.dto.AccommodationResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@DgsComponent
public class AccommodationDataFetcher {

    private final AccommodationService accommodationService;

    public AccommodationDataFetcher(AccommodationService accommodationService) {
        this.accommodationService = accommodationService;
    }

    @DgsQuery
    public List<AccommodationResponse> accommodations() {
        return accommodationService.findAll();
    }

    @DgsQuery
    public AccommodationResponse accommodationById(@InputArgument Long id) {
        return accommodationService.findById(id);
    }

    @DgsMutation
    public AccommodationResponse createAccommodation(@InputArgument("input") Map<String, String> input) {
        AccommodationRequest request = new AccommodationRequest(
                input.get("name"),
                Long.parseLong(input.get("price").toString()),
                LocalDateTime.parse(input.get("duration").toString()));
        return accommodationService.create(request);
    }

    @DgsMutation
    public AccommodationResponse updateAccommodation(@InputArgument Long id, @InputArgument("input") Map<String, String> input) {
        AccommodationRequest request = new AccommodationRequest(
                input.get("name"),
                Long.parseLong(input.get("price").toString()),
                LocalDateTime.parse(input.get("duration").toString()));
        return accommodationService.update(id, request);
    }

    @DgsMutation
    public Long deleteAccommodation(@InputArgument Long id) {
        accommodationService.delete(id);
        return id;
    }
}
