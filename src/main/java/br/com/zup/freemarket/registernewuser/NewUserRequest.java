package br.com.zup.freemarket.registernewuser;

import br.com.zup.freemarket.config.validation.UniqueValue;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class NewUserRequest {

    @Email @NotBlank
    @UniqueValue(domainClass = Usuario.class, fieldName = "login")
    private String login;

    @NotBlank @Size(min = 6)
    private String password;

    public NewUserRequest(@Email @NotBlank String login, @NotBlank @Min(value = 6) String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "NewUserRequest{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public Usuario converterToModel() {
        return new Usuario(this.login, this.password);
    }
}
