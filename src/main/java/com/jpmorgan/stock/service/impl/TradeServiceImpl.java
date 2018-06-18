package com.jpmorgan.stock.service.impl;

import java.util.List;

import com.jpmorgan.stock.dao.SimpleTradeDAO;
import com.jpmorgan.stock.model.Stock;
import com.jpmorgan.stock.model.Trade;
import com.jpmorgan.stock.service.TradeService;

/**
 * Implementation of {@code TradeService}
 * @author Syed Shakir
 */
public class TradeServiceImpl implements TradeService {

  private static TradeServiceImpl instance = null;

  public static TradeService getInstance() {
    if (instance == null) {
      instance = new TradeServiceImpl();
    }
    return instance;
  }

  private SimpleTradeDAO tradeDao = new SimpleTradeDAO();

  /**
   * @inheritDoc
   */
  public void recordTrade(Trade trade) {
    if (trade != null && trade.getStock() != null) {
      tradeDao.addTrade(trade);
    }
  }

  /**
   * @inheritDoc
   */
  public List<Trade> getTrades(Stock stock, int minutes) {
    return tradeDao.getTrades(stock, minutes);
  }

  /**
   * @inheritDoc
   */
  public List<Trade> getAllTrades() {
    return tradeDao.getAllTrades();
  }

}
