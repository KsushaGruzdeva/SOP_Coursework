package com.example.beauty_salon.graphql;

import com.example.beauty_salon.services.MasterService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import demo.com.beauty_salon_api.dto.MasterResponse;
import demo.com.beauty_salon_api.dto.MasterRequest;

import java.util.List;
import java.util.Map;

@DgsComponent
public class MasterDataFetcher {

    private final MasterService masterService;

    public MasterDataFetcher(MasterService masterService) {
        this.masterService = masterService;
    }

    @DgsQuery
    public List<MasterResponse> masters() {
        return masterService.findAll();
    }

    @DgsQuery
    public MasterResponse masterById(@InputArgument Long id) {
        return masterService.findById(id);
    }

    @DgsMutation
    public MasterResponse createMaster(@InputArgument("input") Map<String, String> input) {
        MasterRequest request = new MasterRequest(
                input.get("fullName"),
                input.get("phone"),
                input.get("email"),
                Integer.parseInt(input.get("experienceYears")),
                List.of(Long.parseLong(input.get("accommodations"))));
        return masterService.create(request);
    }
    @DgsMutation
    public MasterResponse updateMaster(@InputArgument Long id, @InputArgument("input") Map<String, String> input) {
        MasterRequest request = new MasterRequest(
                input.get("fullName"),
                input.get("phone"),
                input.get("email"),
                Integer.parseInt(input.get("experienceYears")),
                List.of(Long.parseLong(input.get("accommodationIds"))));
        return masterService.update(id, request);
    }

    @DgsMutation
    public Long deleteMaster(@InputArgument Long id) {
        masterService.delete(id);
        return id;
    }
}
