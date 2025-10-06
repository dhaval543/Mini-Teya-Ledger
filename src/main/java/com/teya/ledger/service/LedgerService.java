package com.teya.ledger.service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.teya.constants.Constants;
import com.teya.ledger.model.Transaction;

	

	@Service
	public class LedgerService {

	    private final Map<String, List<Transaction>> userTransactions = new HashMap<>();
	    private final Map<String, Double> userBalances = new HashMap<>();
	    private final Map<String, AtomicLong> userTransactionCounters = new HashMap<>();
	    // ZonedDateTime timestamp = ZonedDateTime.now(ZoneId.of("Europe/London"));
	   

	    public synchronized Transaction deposit(String accId, double amount) {
	        validateAmount(amount);
	        double balance = userBalances.getOrDefault(accId, 0.0) + amount;
	        userBalances.put(accId, balance);

	        AtomicLong counter = userTransactionCounters.computeIfAbsent(accId, k -> new AtomicLong(1));
	        Transaction transaction = new Transaction(counter.getAndIncrement(), Constants.DEPOSIT, amount, ZonedDateTime.now(), balance);
	        userTransactions.computeIfAbsent(accId, k -> new ArrayList<>()).add(transaction);
	        return transaction;
	    }

	    public synchronized Transaction withdraw(String accId, double amount) {
	        validateAmount(amount);
	        double balance = userBalances.getOrDefault(accId, 0.0);
	        if (amount > balance) throw new IllegalArgumentException(Constants.ERROR_MSG_INSUFFICIENT_BALANCE);

	        double withdrawnToday = userTransactions.getOrDefault(accId, new ArrayList<>()).stream()
	                .filter(t -> t.type().equalsIgnoreCase(Constants.WITHDRAWAL) &&
	                             t.timestamp().toLocalDate().equals(ZonedDateTime.now()))
	                .mapToDouble(Transaction::amount).sum();

	        if (withdrawnToday + amount > Constants.DAILY_WITHDRAWAL_LIMIT)
	            throw new IllegalArgumentException(Constants.ERROR_MSG_EXCEEDED_DAILY_LIMIT);

	        balance -= amount;
	        userBalances.put(accId, balance);

	        AtomicLong counter = userTransactionCounters.computeIfAbsent(accId, k -> new AtomicLong(1));
	        Transaction transaction = new Transaction(counter.getAndIncrement(), Constants.WITHDRAWAL, amount, ZonedDateTime.now(), balance);
	        userTransactions.computeIfAbsent(accId, k -> new ArrayList<>()).add(transaction);
	        return transaction;
	    }

	    public synchronized double getBalance(String accId) {
	        return userBalances.getOrDefault(accId, 0.0);
	    }

	   
	    public synchronized List<Transaction> getTransactionHistory(String accId, String type) {
	    	String normalizedType = (type == null) ? null : type.trim();
	        return userTransactions.getOrDefault(accId, new ArrayList<>()).stream()
	                .filter(t -> (type == null || t.type().equalsIgnoreCase(normalizedType)))
	                .collect(Collectors.toList());
	    }

	    public synchronized void resetAllData() {
	        userTransactions.clear();
	        userBalances.clear();
	        userTransactionCounters.clear();
	    }

	    private void validateAmount(double amount) {
	        if (amount <= 0) throw new IllegalArgumentException(Constants.ERROR_MSG_AMOUNT_GREATER_THAN_ZERO);
	    }
}



