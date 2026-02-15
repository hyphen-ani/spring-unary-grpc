package org.anirudh.spring.tradingserver.service;

import com.anirudh.grpc.StockRequest;
import com.anirudh.grpc.StockResponse;
import com.anirudh.grpc.StockTradingServiceGrpc;
import io.grpc.stub.StreamObserver;
import org.anirudh.spring.tradingserver.entity.Stock;
import org.anirudh.spring.tradingserver.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.grpc.server.service.GrpcService;

import java.time.Instant;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@GrpcService
public class StockTradingServiceImpl extends StockTradingServiceGrpc.StockTradingServiceImplBase {

    private final StockRepository stockRepository;

    public StockTradingServiceImpl(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Override
    public void getStockPrice(StockRequest request, StreamObserver<StockResponse> responseObserver) {
        // StockRequest (StockSymbol) -> DB -> Map Response -> Return
        String stockSymbol = request.getStockSymbol();
        Stock stockEntityFromDatabase = stockRepository.findByStockSymbol(stockSymbol);

        StockResponse stockResponse = StockResponse.newBuilder()
                .setStockSymbol(stockEntityFromDatabase.getStockSymbol())
                .setPrice(stockEntityFromDatabase.getPrice())
                .setTimestamp(stockEntityFromDatabase.getLastUpdated().toString())
                .build();

        responseObserver.onNext(stockResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void subscribeStockPrice(StockRequest request, StreamObserver<StockResponse> responseObserver) {

        String symbol = request.getStockSymbol();

        try{
            for(int i = 0; i <=10; i++){
                StockResponse response = StockResponse.newBuilder()
                        .setStockSymbol(symbol)
                        .setPrice(new Random().nextDouble(200))
                        .setTimestamp(Instant.now().toString())
                        .build();

                responseObserver.onNext(response);
                TimeUnit.SECONDS.sleep(1);
            }
        } catch (Exception e) {
            responseObserver.onError(e);
        }

        responseObserver.onCompleted();

    }
}
