package br.com.zup.freemarket.giveopinion;

import br.com.zup.freemarket.registernewproduct.Product;
import br.com.zup.freemarket.registernewuser.Usuario;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;

@Entity
public class Opinion {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Min(1) @Max(5)
    @Column(nullable = false)
    private Integer rating;

    @NotBlank
    @Column(nullable = false)
    private String title;

    @NotBlank @Size(max = 500)
    @Column(nullable = false)
    private String description;

    @NotNull @Valid
    @ManyToOne
    private Usuario user;

    @NotNull
    @Valid
    @ManyToOne
    private Product product;

    @Deprecated
    public Opinion() {
    }

    public Opinion(@NotNull @Min(1) @Max(5) Integer rating,
                   @NotBlank String title, @NotBlank @Size(max = 500) String description,
                   @NotNull @Valid Usuario user, @NotNull @Valid Product product) {
        this.rating = rating;
        this.title = title;
        this.description = description;
        this.user = user;
        this.product = product;
    }

    public Long getId() {
        return id;
    }

    public Integer getRating() {
        return rating;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Usuario getUser() {
        return user;
    }

    public Product getProduct() {
        return product;
    }

    @Override
    public String toString() {
        return "Opinion{" +
                "id=" + id +
                ", rating=" + rating +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
