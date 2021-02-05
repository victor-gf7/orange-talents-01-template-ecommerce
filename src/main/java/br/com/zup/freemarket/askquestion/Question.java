package br.com.zup.freemarket.askquestion;

import br.com.zup.freemarket.registernewproduct.Product;
import br.com.zup.freemarket.registernewuser.Usuario;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Question {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String title;

    @NotNull @Valid
    @ManyToOne
    private Product product;

    @NotNull @Valid
    @ManyToOne
    private Usuario user;

    @Deprecated
    public Question() {
    }

    public Question(@NotBlank String title, @NotNull @Valid Product product, @NotNull @Valid Usuario user) {
        this.title = title;
        this.product = product;
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public Usuario getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
