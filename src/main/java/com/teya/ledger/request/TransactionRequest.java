package com.teya.ledger.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Transaction request object containing the amount to deposit/withdraw")
public class TransactionRequest {

    @Schema(description = "Transaction amount. Must be > 0. Withdrawals limited to 600 per day.",
            example = "100.00")
	 @NotNull(message = "Amount is required")
	    @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
	    private Double amount;

	    public Double getAmount() { return amount; }
	    public void setAmount(Double amount) { this.amount = amount; }

}
