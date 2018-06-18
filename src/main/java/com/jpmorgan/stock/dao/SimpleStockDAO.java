package com.jpmorgan.stock.dao;

import java.util.HashMap;
import java.util.Map;

import com.jpmorgan.stock.model.Stock;

/**
 * In memory implementation of {@code SimpleStockDao}
 * @author Syed Shakir
 */
public class SimpleStockDAO {

  private Map<String, Stock> stockMap = new HashMap<String, Stock>();

  /**
   * Add new {@code Stock} item to the db.
   * @param stock
   */
  public void addStock(Stock stock) {
    stockMap.put(stock.getSymbol(), stock);
  }

  /**
   * Get {@code Stock} by stock symbol.
   * @param symbol
   * @return
   */
  public Stock getStock(String symbol) {
    return stockMap.get(symbol);
  }

}
