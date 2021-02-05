package br.com.zup.freemarket.registernewproduct;


import br.com.zup.freemarket.config.validation.ExistsId;
import br.com.zup.freemarket.registernewcategory.Category;
import br.com.zup.freemarket.registernewuser.Usuario;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NewProductRequest {

    @NotBlank
    private String name;

    @NotNull @Positive
    private BigDecimal value;

    @NotNull @Positive
    private Integer availableQuantity;

    @Valid
    @Size(min = 3)
    private List<CharacteristicRequest> characteristics = new ArrayList<>();

    @NotBlank @Size(max = 1000)
    private String description;

    @NotNull
    @ExistsId(domainClass = Category.class, fieldName = "id")
    private Long idCategory;


    public NewProductRequest(@NotBlank String name, @NotNull BigDecimal value,
                             @NotNull @Min(0) Integer availableQuantity,
                             @Valid @Size(min = 3) List<CharacteristicRequest> characteristics,
                             @NotBlank @Size(max = 1000) String description,
                             @NotNull Long idCategory) {
        this.name = name;
        this.value = value;
        this.availableQuantity = availableQuantity;
        this.characteristics.addAll(characteristics);
        this.description = description;
        this.idCategory = idCategory;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getValue() {
        return value;
    }

    public Integer getAvailableQuantity() {
        return availableQuantity;
    }

    public String getDescription() {
        return description;
    }

    public Long getIdCategory() {
        return idCategory;
    }

    public List<CharacteristicRequest> getCharacteristics() {
        return characteristics;
    }

    @Override
    public String toString() {
        return "NewProductRequest{" +
                "name='" + name + '\'' +
                ", value=" + value +
                ", availableQuantity=" + availableQuantity +
                ", features=" + characteristics +
                ", description='" + description + '\'' +
                ", idCategory=" + idCategory +
                '}';
    }

    public Set<String> searchEqualCharacteristics() {
        HashSet<String> equalNames = new HashSet<>();//Hashset não suporta elementos iguais
        HashSet<String> results = new HashSet<>();
        for (CharacteristicRequest characteristic : characteristics){
            String name = characteristic.getName();
            if(!equalNames.add(name)){
                results.add(name);
            }
        }
        return results;
    }

    public Product converterToModel(EntityManager manager, Usuario user) {
        @NotNull Category category = manager.find(Category.class, idCategory);

        return new Product(this.name, this.value, this.availableQuantity, this.description, user, category, characteristics);
    }
}
