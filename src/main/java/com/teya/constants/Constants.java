package com.teya.constants;

public class Constants {

	public static final String CREDIT = "credit";
	public static final String DEPOSIT = "deposit";
	public static final double DAILY_WITHDRAWAL_LIMIT = 600;
	public static final String WITHDRAWAL = "WITHDRAWAL";

	public static final String MSG_RESET_MESSAGE = "Ledger reset successfully";
	
	// error messages
	public static final String ERROR_MSG_EXCEEDED_DAILY_LIMIT = "Daily withdrawal limit of " + DAILY_WITHDRAWAL_LIMIT + " exceeded";
	public static final String ERROR_MSG_NEGATIVE_AMOUNT = "Amount cannot be negative";
	public static final String ERROR_MSG_EMPTY_TYPE = "Type must be provided";
	public static final String ERROR_MSG_INSUFFICIENT_BALANCE = "Insufficient balance for withdrawal";
	public static final String ERROR_MSG_AMOUNT_GREATER_THAN_ZERO = "Amount must be greater than 0";
	public static final String MSG_INTERNAL_SERVER_ERROR = "Internal server error";
	
	
}
