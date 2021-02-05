package br.com.zup.freemarket.askquestion;


import br.com.zup.freemarket.config.validation.ExistsId;
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
public class QuestionController {

    @Autowired
    private TokenService tokenService;

    @PersistenceContext
    private EntityManager manager;

    @PostMapping("product/{id}/question")
    @Transactional
    public ResponseEntity<?> registerQuestion(@RequestBody @Valid NewQuestionRequest request, @PathVariable Long id, @RequestHeader("Authorization") String token){

        Usuario loggedUser = tokenService.getLoggedUser(token.substring(7, token.length()));
        Product product = manager.find(Product.class, id);

        if(product == null){
            return ResponseEntity.badRequest().build();
        }
        product.associateQuestion(request, loggedUser);
        manager.merge(product);

        return ResponseEntity.ok().build();
    }
}
