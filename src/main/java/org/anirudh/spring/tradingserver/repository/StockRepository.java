package org.anirudh.spring.tradingserver.repository;

import org.anirudh.spring.tradingserver.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

    Stock findByStockSymbol(String stockSymbol);
}
