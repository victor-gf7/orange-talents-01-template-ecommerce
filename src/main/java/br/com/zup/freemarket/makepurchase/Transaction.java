package br.com.zup.freemarket.makepurchase;

import br.com.zup.freemarket.registernewuser.Usuario;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private TransactionStatus status;

    @NotNull
    private String transactionIdGateway;

    @ManyToOne
    private Purchase purchase;

    private LocalDateTime createdIn = LocalDateTime.now();

    @Deprecated
    public Transaction() {
    }

    public Transaction(@NotNull TransactionStatus status, @NotBlank String transactionIdGateway, @NotNull @Valid Purchase purchase) {
        this.status = status;
        this.transactionIdGateway = transactionIdGateway;
        this.purchase = purchase;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Transaction other = (Transaction) obj;
        if (id == null) {
            return other.id == null;
        } else return transactionIdGateway.equals(other.transactionIdGateway);
    }

    public boolean successfullyCompleted(){
        return this.status.equals(TransactionStatus.success);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", status=" + status +
                ", transactionIdGateway='" + transactionIdGateway + '\'' +
                ", createdIn=" + createdIn +
                '}';
    }
}
