package br.com.zup.freemarket.giveopinion;


import br.com.zup.freemarket.registernewproduct.Product;
import br.com.zup.freemarket.registernewuser.Usuario;
import br.com.zup.freemarket.token.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
public class OpinionController {

    @Autowired
    private TokenService tokenService;

    @PersistenceContext
    private EntityManager manager;

    @PostMapping("product/{id}/opinion")
    @Transactional
    public ResponseEntity<?> registerOpinion(@RequestBody @Valid NewOpinionRequest request, @PathVariable Long id, @RequestHeader("Authorization") String token){

        Usuario loggedUser = tokenService.getLoggedUser(token.substring(7, token.length()));
        Product product = manager.find(Product.class, id);

        if(product == null){
            return ResponseEntity.badRequest().build();
        }

        product.associateOpinion(request, loggedUser);
        manager.merge(product);

        return ResponseEntity.ok().build();
    }
}
