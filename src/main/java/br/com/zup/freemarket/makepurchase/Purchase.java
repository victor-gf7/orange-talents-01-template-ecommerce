package br.com.zup.freemarket.makepurchase;

import br.com.zup.freemarket.registernewproduct.Product;
import br.com.zup.freemarket.registernewuser.Usuario;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

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
                '}';
    }
}
