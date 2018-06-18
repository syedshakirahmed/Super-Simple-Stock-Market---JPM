package com.jpmorgan.stock.exception;

/**
 * Custom exception for any super simple stock errors.
 * @author Syed Shakir
 */
public class SuperSimpleStocksException extends Exception {

  private static final long serialVersionUID = 1L;

  public SuperSimpleStocksException(String message) {
    super(message);
  }

}
