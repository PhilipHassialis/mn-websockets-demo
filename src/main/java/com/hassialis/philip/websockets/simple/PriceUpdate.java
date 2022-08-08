package com.hassialis.philip.websockets.simple;

import java.math.BigDecimal;

public class PriceUpdate {

	private final String symbol;
	private final BigDecimal price;

	public PriceUpdate(String symbol, BigDecimal price) {
		this.price = price;
		this.symbol = symbol;
	}

	public String getSymbol() {
		return symbol;
	}

	public BigDecimal getPrice() {
		return price;
	}
}
