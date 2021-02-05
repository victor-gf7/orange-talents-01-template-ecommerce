package br.com.zup.freemarket.uploadimage;

import br.com.zup.freemarket.registernewproduct.Product;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String url;

    @NotNull
    @Valid
    @ManyToOne
    private Product product;

    @Deprecated
    public ProductImage() {
    }

    public ProductImage(@NotBlank String url, @NotNull @Valid Product product) {
        this.url = url;
        this.product = product;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "ProductImage{" +
                "id=" + id +
                ", url='" + url + '\'' +
                '}';
    }
}
