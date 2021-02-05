package br.com.zup.freemarket.authentication;

import br.com.zup.freemarket.registernewuser.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    private UsuarioRepository repository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<Usuario> user = repository.findByLogin(login);

        if(user.isPresent()){
            return user.get();
        }

        throw new UsernameNotFoundException("Invalid Data");
    }
}
