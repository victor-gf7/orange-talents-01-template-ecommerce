package br.com.zup.freemarket.askquestion;

import br.com.zup.freemarket.registernewproduct.Product;
import br.com.zup.freemarket.registernewuser.Usuario;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

public class NewQuestionRequest {

    @NotBlank
    private String title;

    @JsonCreator
    public NewQuestionRequest(@NotBlank @JsonProperty("title") String title) {
        this.title = title;
    }


    @Override
    public String toString() {
        return "NewQuestionRequest{" +
                "title='" + title + '\'' +
                '}';
    }

    public Question converterToModel(Usuario user, Product product) {
        return new Question(this.title, product, user);
    }
}
