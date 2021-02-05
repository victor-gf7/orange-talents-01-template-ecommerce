package br.com.zup.freemarket.registernewuser;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
public class NewUserController {

    @PersistenceContext
    private EntityManager manager;

    @PostMapping("/user")
    @Transactional
    public ResponseEntity<?> toRegisterUser(@RequestBody @Valid NewUserRequest request){
        Usuario user = request.converterToModel();
        user.setPassword(new BCryptPasswordEncoder().encode(request.getPassword()));
        manager.persist(user);

        return ResponseEntity.ok().build();
    }
}
