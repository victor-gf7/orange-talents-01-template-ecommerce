package br.com.zup.freemarket.makepurchase;


import br.com.zup.freemarket.config.email.SendEmailService;
import br.com.zup.freemarket.registernewproduct.Product;
import br.com.zup.freemarket.registernewuser.Usuario;
import br.com.zup.freemarket.token.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
public class PurchaseController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private SendEmailService sendEmailService;

    @PersistenceContext
    private EntityManager manager;

    @PostMapping("purchase/product/{id}")
    @Transactional
    public ResponseEntity<?> finalizePurchase(@RequestBody @Valid NewPurchaseRequest request,
                                              @PathVariable Long id,
                                              @RequestHeader("Authorization") String token,
                                              UriComponentsBuilder builder) throws BindException {
        Usuario loggedUser = tokenService.getLoggedUser(token.substring(7, token.length()));
        Product product = manager.find(Product.class, id);

        boolean slaughteredStock = product.slaughterStock(request.getQuantity());
        sendEmailService.send(product.getUser().getLogin());
        if(slaughteredStock){
            Purchase purchase = request.converterToModel(loggedUser, product);
            purchase.setStatus(StatusPurchase.STARTED);
            manager.persist(purchase);

            String url = purchase.getGateway().createURL(purchase, builder);

            return ResponseEntity.status(HttpStatus.MOVED_TEMPORARILY).body(url);
        }

        BindException problemWithStock = new BindException(request, "newPurchaseRequest");
        problemWithStock.rejectValue("quantity", null, "Não foi possivel realizar a compra devido a falta de estoque");

        throw problemWithStock;
    }
}
