package br.com.zup.freemarket.registernewuser;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
public class Usuario implements UserDetails {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email @NotBlank
    @Column(unique = true, nullable = false)
    private String login;

    @NotBlank @Size(min = 6)
    @Column(nullable = false)
    private String password;

    @NotNull @PastOrPresent
    @Column(nullable = false)
    private LocalDateTime createdIn = LocalDateTime.now();

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Profile> profiles = new ArrayList<>();

    @Deprecated
    public Usuario() {
    }

    public Usuario(@Email @NotBlank String login, @NotBlank @Size(min = 6) String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public LocalDateTime getCreatedIn() {
        return createdIn;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Usuario other = (Usuario) obj;
        if (login == null) {
            return other.login == null;
        } else return login.equals(other.login);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.profiles;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", createdIn=" + createdIn +
                ", profiles=" + profiles +
                '}';
    }
}
