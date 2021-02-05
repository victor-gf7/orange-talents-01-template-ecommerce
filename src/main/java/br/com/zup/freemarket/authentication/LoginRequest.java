package br.com.zup.freemarket.authentication;

import br.com.zup.freemarket.config.validation.UniqueValue;
import br.com.zup.freemarket.registernewuser.Usuario;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class LoginRequest {

    @NotBlank
    private String email;

    @NotBlank @Size(min = 6)
    private String password;

    public LoginRequest(@NotBlank String email, @NotBlank @Size(min = 6) String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    @Override
    public String toString() {
        return "LoginRequest{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public UsernamePasswordAuthenticationToken converterToUsernamePasswordAuthenticationToken() {

        return new UsernamePasswordAuthenticationToken(this.email, this.password);
    }
}
