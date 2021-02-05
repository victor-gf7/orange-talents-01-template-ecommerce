package br.com.zup.freemarket.registernewproduct;

import br.com.zup.freemarket.authentication.UsuarioRepository;
import br.com.zup.freemarket.registernewuser.Usuario;
import br.com.zup.freemarket.token.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
public class ProductController {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private TokenService tokenService;

    @InitBinder
    public void init(WebDataBinder binder){
        binder.addValidators(new ProhibitsCharacteristicWithTheSameNameValidator());
    }

    @PostMapping("/product")
    @Transactional
    public ResponseEntity<?> registerProduct(@RequestBody @Valid NewProductRequest request, @RequestHeader("Authorization") String token ){

        Usuario loggedUser = tokenService.getLoggedUser(token.substring(7, token.length()));

        Product product = request.converterToModel(manager, loggedUser);

        manager.persist(product);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<DetailProductResponse> productDetails(@PathVariable Long id){
        Product product = manager.find(Product.class, id);

        if(product == null){
            return ResponseEntity.badRequest().build();
        }
        DetailProductResponse response = new DetailProductResponse(product);

        return ResponseEntity.ok(response);
    }
}
