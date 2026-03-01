package com.pm.billing_service.grpc;

import com.pm.billing.BillingRequest;
import com.pm.billing.BillingResponse;
import com.pm.billing.BillingServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GrpcService
public class BillingGrpcService extends BillingServiceGrpc.BillingServiceImplBase {

    // 1. Define the missing 'log' symbol
    private static final Logger log = LoggerFactory.getLogger(BillingGrpcService.class);

    @Override
    public void createBillingAccount(BillingRequest billingRequest, StreamObserver<BillingResponse> responseObserver) {
        log.info("createBillingAccount called with request: {}", billingRequest.toString());

        // 2. Build the response using the exact field names from your .proto
        BillingResponse response = BillingResponse.newBuilder()
            .setAccountID("1234")   // Matches 'accountID' in proto
            .setStatus("ACTIVE")    // Matches 'status' in proto
            .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}