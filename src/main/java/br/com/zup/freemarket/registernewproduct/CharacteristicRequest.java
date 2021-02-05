package br.com.zup.freemarket.registernewproduct;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CharacteristicRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String description;


    public CharacteristicRequest(@NotBlank String name, @NotBlank String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Feature{" +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public ProductCharacteristic converterToModel(@NotNull @Valid Product product) {

        return new ProductCharacteristic(name, description, product);
    }
}
