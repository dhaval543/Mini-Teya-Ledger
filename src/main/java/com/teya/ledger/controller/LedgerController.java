package com.teya.ledger.controller;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.teya.constants.Constants;
import com.teya.ledger.model.Transaction;
import com.teya.ledger.request.TransactionRequest;
import com.teya.ledger.service.LedgerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/ledger")
@Tag(name = "Ledger API", description = "Deposit, withdraw, check balance, and view transaction history")
public class LedgerController {

    @Autowired
    private LedgerService ledgerService;

    @Operation(
            summary = "Deposit money into account",
            description = "Deposit a positive amount into the given user's account.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Deposit successful"),
                    @ApiResponse(responseCode = "400", description = "Invalid input (negative or zero deposit)", content = @Content)
            }
    )
    @PostMapping("/{accId}/deposit")
    public Transaction deposit(@PathVariable String accId, @Valid @RequestBody TransactionRequest request) {
        return ledgerService.deposit(accId, request.getAmount());
    }

    @Operation(
            summary = "Withdraw money from account",
            description = "Withdraw a positive amount from the given user's account. " +
                    "Enforces validation: cannot withdraw negative values, cannot exceed balance, and max 600 per day.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Withdrawal successful"),
                    @ApiResponse(responseCode = "400", description = "Validation failed (e.g., exceeds balance, daily limit)", content = @Content)
            }
    )
    @PostMapping("/{accId}/withdraw")
    public Transaction withdraw(@PathVariable String accId, @Valid @RequestBody TransactionRequest request) {
        return ledgerService.withdraw(accId, request.getAmount());
    }

    @Operation(summary = "Get current balance", description = "Returns the current account balance.")
    @GetMapping("/{accId}/balance")
    public double getBalance(@PathVariable String accId) {
        return ledgerService.getBalance(accId);
    }

    @Operation(summary = "Get transaction history", description = "Returns all transactions for the given user.")
    @GetMapping("/{accId}/TransactionHistory")
    public List<Transaction> getTransactions(
            @PathVariable String accId,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime to
    ) {
        return ledgerService.getTransactionHistory(accId, type);
    }

    @Operation(summary = "Admin reset", description = "Reset all balances and transaction history (for testing/demo).")
    @PostMapping("/admin/reset")
    public String resetLedger() {
        ledgerService.resetAllData();
        return Constants.MSG_RESET_MESSAGE;
    }
}

