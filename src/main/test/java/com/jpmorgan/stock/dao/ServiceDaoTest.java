package com.jpmorgan.stock.dao;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.jpmorgan.stock.dao.SimpleStockDAO;
import com.jpmorgan.stock.model.Stock;
import com.jpmorgan.stock.model.StockType;

/**
 * Tests for {@code SimpleStockDAO}
 * @author Syed Shakir
 */
public class ServiceDaoTest {

  private SimpleStockDAO stockDao;
  private Stock stock1;

  @Before
  public void setup() {
    stockDao = new SimpleStockDAO();
    stock1 = new Stock("TEST", StockType.COMMON, 1, 0, 1);
  }

  @Test
  public void testAddAndGetStock() {
    stockDao.addStock(stock1);
    assertEquals(stock1, stockDao.getStock(stock1.getSymbol()));
  }

}
