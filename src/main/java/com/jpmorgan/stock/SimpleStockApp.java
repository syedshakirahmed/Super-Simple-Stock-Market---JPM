package com.jpmorgan.stock;

import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

import com.jpmorgan.stock.exception.SuperSimpleStocksException;
import com.jpmorgan.stock.model.Stock;
import com.jpmorgan.stock.model.StockType;
import com.jpmorgan.stock.model.Trade;
import com.jpmorgan.stock.model.TradeType;
import com.jpmorgan.stock.service.StockService;
import com.jpmorgan.stock.service.TradeService;
import com.jpmorgan.stock.service.impl.StockServiceImpl;
import com.jpmorgan.stock.service.impl.TradeServiceImpl;

/**
 * JP Morgan - Super Simple Stocks
 * @author Syed Shakir
 */
public class SimpleStockApp {

  private static StockService stockService = StockServiceImpl.getInstance();
  private static TradeService tradeService = TradeServiceImpl.getInstance();
  
  private static Scanner scanner;

  public static void main(String[] args) {
    initStocks();
    printMenu();

    scanner = new Scanner(System.in);
    String choice= null;
    while (true) {
      choice = scanner.nextLine();
      if ("q".equals(choice)) {
        scanner.close();
        System.exit(0);
      } else {
        try {
          int option = Integer.parseInt(choice);
          Stock stockFromUser;
          double priceFromUser;

          switch (option) {
            case 1:
              stockFromUser = getStockFromUser();
              priceFromUser = getStockPriceFromUser();
              calculateDividendYield(stockFromUser, priceFromUser);
              break;
            case 2:
              stockFromUser = getStockFromUser();
              priceFromUser = getStockPriceFromUser();
              calculatePERatio(stockFromUser, priceFromUser);
              break;
            case 3:
              stockFromUser = getStockFromUser();
              int quantityFromUser = getQuantityFromUser();
              TradeType tradeTypeFromUser = getTradeType();
              priceFromUser = getStockPriceFromUser();
              recordTrade(stockFromUser, quantityFromUser, tradeTypeFromUser, priceFromUser);
              break;
            case 4:
              stockFromUser = getStockFromUser();
              calculateVolumeWeightedStockPrice(stockFromUser);
              break;
            case 5:
              calculateGBCE();
              break;
            default:
              break;
          }
        } catch (NumberFormatException e) {
          printResult("Invalid Option");
        } catch (SuperSimpleStocksException e1) {
          printResult(e1.getMessage());
        }
        System.out.println("");
        printMenu();
      }
    }
  }

  private static Stock getStockFromUser() throws SuperSimpleStocksException {
    System.out.println("Please input stock symbol");
	String stockSymbol = getStockSymbol();
    Stock stock = stockService.getStock(stockSymbol);
    if (stock == null) {
      throw new SuperSimpleStocksException("Stock not found");
    }
    return stock;
  }

  private static double getStockPriceFromUser() throws SuperSimpleStocksException {
    System.out.println("Please input stock market price");
    String stockPrice = scanner.nextLine();
    try {
      double result = Double.parseDouble(stockPrice);
      if (result <= 0) {
        throw new SuperSimpleStocksException("Invalid price: Must be greated than 0");
      }
      return result;
    } catch (NumberFormatException e) {
      throw new SuperSimpleStocksException("Invalid price: Not a number");
    }
  }

  private static TradeType getTradeType() throws SuperSimpleStocksException {
    System.out.println("Please input trade type (BUY/SELL)");
    String type = scanner.nextLine();
    try {
      return TradeType.valueOf(type.toUpperCase());
    } catch (IllegalArgumentException e) {
      throw new SuperSimpleStocksException("Invalid trade type: Must be BUY or SELL");
    }
  }

  private static int getQuantityFromUser() throws SuperSimpleStocksException {
    System.out.println("Please input quantity");
    String quantity = scanner.nextLine();
    try {
      int result = Integer.parseInt(quantity);
      if (result <= 0) {
        throw new SuperSimpleStocksException("Invalid quantity: Must be greated than 0");
      }
      return result;
    } catch (NumberFormatException e) {
      throw new SuperSimpleStocksException("Invalid quantity: Not a number");
    }
  }

  private static void printMenu() {
    System.out.println("JPMorgan - Super simple stock market");
    System.out.println("1: Calculate dividend yield for stock");
    System.out.println("2: Calculate P/E ratio for stock");
    System.out.println("3: Record a trade for stock");
    System.out.println("4: Calculate Volume Weighted Stock Price for stock");
    System.out.println("5: Calculate GBCE All Share Index");
    System.out.println("q: Quit");
  }

    private static String getStockSymbol() {
    String stockSymbol = null;
    System.out.println("1: TEA");
    System.out.println("2: POP");
    System.out.println("3: ALE");
    System.out.println("4: GIN");
    System.out.println("5: JOE");
	try {
	int option = Integer.parseInt(scanner.nextLine());
	
	 switch (option) {
            case 1:
              stockSymbol = "TEA";
              break;
            case 2:
               stockSymbol = "POP";
              break;
            case 3:
               stockSymbol = "ALE";
              break;
            case 4:
              stockSymbol = "GIN";
              break;
			case 5:
              stockSymbol = "JOE";
              break;
           default:
		     break;
          }
	} catch (NumberFormatException e) {
          printResult("Invalid Option");
        }
    return stockSymbol;
  }
  
  private static void calculateDividendYield(Stock stock, double price) {
    double result = stockService.calculateDividendYield(stock, price);
    printResult("Dividend Yield: " + result);
  }

  private static void calculatePERatio(Stock stock, double price) {
     double result = stockService.calculatePERatio(stock, price);
     printResult("PE Ratio: " + result);
  }

  private static void calculateVolumeWeightedStockPrice(Stock stock) {
    List<Trade> trades = tradeService.getTrades(stock, 15);
    if (trades == null || trades.isEmpty()) {
      printResult("Volume Weighted Stock Price: No trades");
    } else {
      double result = stockService.calculateVolumeWeightedStockPrice(trades);
      printResult("Volume Weighted Stock Price: " + result);
    }
  }

  private static void recordTrade(Stock stock, int quantity, TradeType type, double price) {
    tradeService.recordTrade(new Trade(stock, Calendar.getInstance().getTime(),
        quantity, type, price));
    printResult("Trade recorded");
  }

  private static void calculateGBCE() {
    List<Trade> allTrades = tradeService.getAllTrades();
    if (allTrades == null || allTrades.isEmpty()) {
      printResult("Unable to calculate GBCE: No trades");
    } else {
      printResult("GBCE: " + stockService.calculateGBCE(allTrades));
    }
  }

  private static void initStocks() {
    stockService.addStock(new Stock("TEA", StockType.COMMON, 0, 0, 100));
    stockService.addStock(new Stock("POP", StockType.COMMON, 8, 0, 100));
    stockService.addStock(new Stock("ALE", StockType.COMMON, 23, 0, 60));
    stockService.addStock(new Stock("GIN", StockType.PREFERRED, 8, 2, 100));
    stockService.addStock(new Stock("JOE", StockType.PREFERRED, 13, 0, 250));
  }

  private static void printResult(String result) {
    System.out.println("-------------------------------------");
    System.out.println(result);
    System.out.println("-------------------------------------");
  }
}
