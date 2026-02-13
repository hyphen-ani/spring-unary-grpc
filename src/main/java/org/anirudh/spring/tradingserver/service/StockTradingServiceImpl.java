package org.anirudh.spring.tradingserver.service;

import com.anirudh.grpc.StockRequest;
import com.anirudh.grpc.StockResponse;
import com.anirudh.grpc.StockTradingServiceGrpc;
import io.grpc.stub.StreamObserver;
import org.springframework.grpc.server.service.GrpcService;

@GrpcService
public class StockTradingServiceImpl extends StockTradingServiceGrpc.StockTradingServiceImplBase {

    @Override
    public void getStockPrice(StockRequest request, StreamObserver<StockResponse> responseObserver) {
        // StockRequest (StockSymbol) -> DB -> Map Response -> Return


    }
}
