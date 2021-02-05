package br.com.zup.freemarket.registernewproduct;


import br.com.zup.freemarket.registernewuser.Usuario;


public class UsuarioResponse {

    private String login;

    public UsuarioResponse(Usuario user) {
        this.login = user.getLogin();
    }

    public String getLogin() {
        return login;
    }
}
