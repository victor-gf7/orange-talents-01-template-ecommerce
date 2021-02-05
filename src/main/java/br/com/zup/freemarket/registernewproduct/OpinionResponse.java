package br.com.zup.freemarket.registernewproduct;

import br.com.zup.freemarket.giveopinion.Opinion;
import br.com.zup.freemarket.registernewuser.Usuario;

public class OpinionResponse {

    private Integer rating;
    private String title;
    private String description;
    private UsuarioResponse user;


    public OpinionResponse(Opinion opinion) {
        this.rating = opinion.getRating();
        this.title = opinion.getTitle();
        this.description = opinion.getDescription();
        this.user = new UsuarioResponse(opinion.getUser());
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

    public UsuarioResponse getUser() {
        return user;
    }
}
