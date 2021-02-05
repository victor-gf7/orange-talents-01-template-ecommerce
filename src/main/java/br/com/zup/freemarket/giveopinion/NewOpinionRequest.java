package br.com.zup.freemarket.giveopinion;

import br.com.zup.freemarket.registernewproduct.Product;
import br.com.zup.freemarket.registernewuser.Usuario;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;

public class NewOpinionRequest {

    @NotNull
    @Min(1) @Max(5)
    private Integer rating;

    @NotBlank
    private String title;

    @NotBlank
    @Size(max = 500)
    private String description;


    public NewOpinionRequest(@NotNull @Size(min = 1, max = 5) Integer rating, @NotBlank String title, @NotBlank @Size(max = 500) String description) {
        this.rating = rating;
        this.title = title;
        this.description = description;
    }

    @Override
    public String toString() {
        return "NewOpinionRequest{" +
                "rating=" + rating +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public Opinion converterToModel(Usuario user, Product product) {

        return new Opinion(this.rating, this.title, this.description, user, product);
    }
}
