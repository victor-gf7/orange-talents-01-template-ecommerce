package br.com.zup.freemarket.makepurchase;

import br.com.zup.freemarket.registernewproduct.Product;
import br.com.zup.freemarket.registernewuser.Usuario;
import io.jsonwebtoken.lang.Assert;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Purchase {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private StatusPurchase status  = StatusPurchase.NOT_STARTED;

    @Enumerated(EnumType.STRING)
    private ChosenGateway gateway;

    @NotNull
    @Column(nullable = false)
    private Integer quantity;

    @NotNull
    @ManyToOne @Valid
    private Product product;

    @NotNull
    @ManyToOne @Valid
    private Usuario buyer;

    @OneToMany(mappedBy = "purchase", cascade = CascadeType.MERGE)
    private Set<Transaction> transactions = new HashSet<>();

    @Deprecated
    public Purchase() {
    }

    public Purchase(ChosenGateway gateway, @NotNull Integer quantity, @NotNull @Valid Product product, @NotNull @Valid Usuario buyer) {
        this.gateway = gateway;
        this.quantity = quantity;
        this.product = product;
        this.buyer = buyer;
    }

    public ChosenGateway getGateway() {
        return gateway;
    }

    public void setStatus(StatusPurchase status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "id=" + id +
                ", status=" + status +
                ", gateway=" + gateway +
                ", quantity=" + quantity +
                ", product=" + product +
                ", buyer=" + buyer +
                ", transactions=" + transactions +
                '}';
    }

    public void addPayment(@Valid PaypalRequest request) {

        Transaction transaction = request.converterToTransaction(this);

        transactions.forEach(transactionProcessed -> {
            Assert.isTrue(!transactionProcessed.equals(transaction), "Esta transação " + transaction.toString() + " já foi processada");
        });

        Set<Transaction> transactionsSuccessfullyCompleted = this.transactions.stream().filter(Transaction::successfullyCompleted).collect(Collectors.toSet());

        Assert.isTrue(transactionsSuccessfullyCompleted.isEmpty(), "Essa compra já foi concluida com sucesso");

        this.transactions.add(transaction);
    }
}
