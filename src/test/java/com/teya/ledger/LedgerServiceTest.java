package com.teya.ledger;


import com.teya.constants.Constants;
import com.teya.ledger.model.Transaction;
import com.teya.ledger.service.LedgerService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class LedgerServiceTest {

    private LedgerService ledgerService;
    private final String accId = "111";

    @BeforeEach
    void setUp() {
        ledgerService = new LedgerService();
    }

    @Test
    void testDeposit() {
        Transaction t = ledgerService.deposit(accId, 200);
        assertEquals(200, ledgerService.getBalance(accId));
        assertEquals(Constants.DEPOSIT, t.type());
    }

    @Test
    void testWithdraw() {
        ledgerService.deposit(accId, 500);
        Transaction t = ledgerService.withdraw(accId, 200);
        assertEquals(300, ledgerService.getBalance(accId));
        assertEquals(Constants.WITHDRAWAL, t.type());
    }

    @Test
    void testOverdraft() {
        ledgerService.deposit(accId, 100);
        Exception e = assertThrows(IllegalArgumentException.class, () -> ledgerService.withdraw(accId, 200));
        assertEquals(Constants.ERROR_MSG_INSUFFICIENT_BALANCE, e.getMessage());
    }

    @Test
    void testDailyLimit() {
        ledgerService.deposit(accId, 1000);
        ledgerService.withdraw(accId, 600.01);
        Exception e = assertThrows(IllegalArgumentException.class, () -> ledgerService.withdraw(accId, 300));
        assertEquals(Constants.ERROR_MSG_EXCEEDED_DAILY_LIMIT, e.getMessage());
    }

    @Test
    void testTransactionHistory() {
        ledgerService.deposit(accId, 100);
        ledgerService.withdraw(accId, 50);
        List<Transaction> all = ledgerService.getTransactionHistory(accId, null);
        assertEquals(2, all.size());
    }

    @Test
    void testReset() {
        ledgerService.deposit(accId, 100);
        ledgerService.resetAllData();
        assertEquals(0, ledgerService.getBalance(accId));
        assertTrue(ledgerService.getTransactionHistory(accId, null).isEmpty());
    }
}

