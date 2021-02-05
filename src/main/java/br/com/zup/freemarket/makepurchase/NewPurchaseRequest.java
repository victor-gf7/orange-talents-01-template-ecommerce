package br.com.zup.freemarket.makepurchase;

import br.com.zup.freemarket.registernewproduct.Product;
import br.com.zup.freemarket.registernewuser.Usuario;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class NewPurchaseRequest {

    @NotNull @Positive
    private Integer quantity;

    @NotNull
    private ChosenGateway gateway;

    @JsonCreator
    public NewPurchaseRequest(@NotNull @Positive @JsonProperty("quantity") Integer quantity, @NotNull ChosenGateway gateway) {
        this.quantity = quantity;
        this.gateway = gateway;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public ChosenGateway getGateway() {
        return gateway;
    }

    public Purchase converterToModel(Usuario user, Product product) {

        return new Purchase(this.gateway, this.quantity, product, user);
    }
}
