package br.com.zup.freemarket.registernewcategory;

import br.com.zup.freemarket.config.validation.ExistsId;
import br.com.zup.freemarket.config.validation.UniqueValue;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class NewCategoryRequest {

    @NotBlank
    @UniqueValue(domainClass = Category.class, fieldName = "name")
    private String name;

    @ExistsId(domainClass = Category.class, fieldName = "id")
    private Long idMotherCategory;

    public NewCategoryRequest(@NotBlank String name, Long idMotherCategory) {
        this.name = name;
        this.idMotherCategory = idMotherCategory;
    }

    public String getName() {
        return name;
    }

    public Long getIdMotherCategory() {
        return idMotherCategory;
    }

    @Override
    public String toString() {
        return "NewCategoryRequest{" +
                "name='" + name + '\'' +
                ", idMotherCategory=" + idMotherCategory +
                '}';
    }

    public Category converterToModel(EntityManager manager) {
        if(idMotherCategory != null){
            @NotNull Category motherCategory = manager.find(Category.class, idMotherCategory);
            return new Category(this.name, motherCategory);
        }

        return new Category(this.name, null);
    }
}
