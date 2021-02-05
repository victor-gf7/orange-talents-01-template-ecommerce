package br.com.zup.freemarket.registernewproduct;

import br.com.zup.freemarket.askquestion.Question;


public class QuestionResponse {

    private String title;
    private UsuarioResponse user;

    public QuestionResponse(Question question) {
        this.title = question.getTitle();
        this.user = new UsuarioResponse(question.getUser());
    }

    public String getTitle() {
        return title;
    }

    public UsuarioResponse getUser() {
        return user;
    }
}
