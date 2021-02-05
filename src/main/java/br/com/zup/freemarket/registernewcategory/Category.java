package br.com.zup.freemarket.registernewcategory;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class Category {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true, nullable = false)
    private String name;

    @ManyToOne
    private Category motherCategory;

    @Deprecated
    public Category() {
    }

    public Category(@NotBlank String name, Category motherCategory) {
        this.name = name;
        this.motherCategory = motherCategory;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", motherCategory=" + motherCategory +
                '}';
    }
}
