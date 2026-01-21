package com.demo;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class AnalyticsServiceImpl extends AnalyticsServiceGrpc.AnalyticsServiceImplBase {

    @Override
    public void calculateAccommodationRating(AccommodationRatingRequest request, StreamObserver<AccommodationRatingResponse> responseObserver) {
        int score = (int) (Math.random() * 100);
        String verdict = score > 50 ? "GOOD" : "BAD";

        AccommodationRatingResponse response = AccommodationRatingResponse.newBuilder()
                .setAccommodationId(request.getAccommodationId())
                .setRatingScore(score)
                .setVerdict(verdict)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
