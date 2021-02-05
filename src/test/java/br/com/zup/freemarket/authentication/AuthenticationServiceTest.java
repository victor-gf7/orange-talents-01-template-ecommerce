package br.com.zup.freemarket.authentication;

import br.com.zup.freemarket.registernewuser.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ActiveProfiles("test")
class AuthenticationServiceTest {

    @Autowired
    private AuthenticationService authenticationService;

    @PersistenceContext
    private EntityManager manager;

    @Test
    void mustNotLoadUserByNotFoundUsername() {

        assertThrows(UsernameNotFoundException.class, ()-> authenticationService.loadUserByUsername("joao@email.com"));
    }

    @Test
    @Transactional
    void mustNotLoadUserByUsername() {
        Usuario user = new Usuario("joao@email.com", "123456");

        manager.persist(user);

        assertEquals(user, authenticationService.loadUserByUsername("joao@email.com"));
    }
}