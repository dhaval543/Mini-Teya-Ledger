package com.teya.ledger.model;

import java.time.ZonedDateTime;

import com.teya.constants.Constants;

public record Transaction(
	    long id,
	    String type,
	    double amount,
	    ZonedDateTime timestamp,
	    double balanceAfter
	) {
	    public Transaction {
	        if (amount < 0) {
	            throw new IllegalArgumentException(Constants.ERROR_MSG_NEGATIVE_AMOUNT);
	        }
	        if (type == null || type.isBlank()) {
	            throw new IllegalArgumentException(Constants.ERROR_MSG_EMPTY_TYPE);
	        }
	    }

}
