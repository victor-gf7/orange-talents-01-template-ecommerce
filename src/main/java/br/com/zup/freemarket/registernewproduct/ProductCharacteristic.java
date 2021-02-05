package br.com.zup.freemarket.registernewproduct;


import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class ProductCharacteristic {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotNull @Valid
    @ManyToOne
    private Product product;


    @Deprecated
    public ProductCharacteristic() {
    }

    public ProductCharacteristic(@NotBlank String name, @NotBlank String description,
                                 @NotNull @Valid Product product) {
        this.name = name;
        this.description = description;
        this.product = product;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Product getProduct() {
        return product;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ProductCharacteristic other = (ProductCharacteristic) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (product == null) {
            return other.product == null;
        } else return product.equals(other.product);
    }

    @Override
    public String toString() {
        return "ProductCharacteristic{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
