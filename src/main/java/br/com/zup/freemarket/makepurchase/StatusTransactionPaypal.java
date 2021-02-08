package br.com.zup.freemarket.makepurchase;


public enum StatusTransactionPaypal {

    SUCCESS, ERROR;

    public TransactionStatus normalize() {
        if(this.equals(SUCCESS)){
            return TransactionStatus.success;
        }
        return TransactionStatus.error;
    }
}
