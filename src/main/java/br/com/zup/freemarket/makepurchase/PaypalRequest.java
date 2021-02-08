package br.com.zup.freemarket.makepurchase;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class PaypalRequest {

    @NotBlank
    private String transactionId;

    @NotNull
    private StatusTransactionPaypal statusTransactionPaypal;

    public PaypalRequest(@NotBlank String transactionId, @NotNull StatusTransactionPaypal statusTransactionPaypal) {
        this.transactionId = transactionId;
        this.statusTransactionPaypal = statusTransactionPaypal;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public StatusTransactionPaypal getStatusTransactionPaypal() {
        return statusTransactionPaypal;
    }

    @Override
    public String toString() {
        return "PaypalRequest{" +
                "transactionId='" + transactionId + '\'' +
                ", statusTransactionPaypal=" + statusTransactionPaypal +
                '}';
    }

    public Transaction converterToTransaction(Purchase purchase) {
        return new Transaction(statusTransactionPaypal.normalize(), transactionId, purchase);
    }
}
