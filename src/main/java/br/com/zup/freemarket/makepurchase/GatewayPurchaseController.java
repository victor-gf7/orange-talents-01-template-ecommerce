package br.com.zup.freemarket.makepurchase;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
public class GatewayPurchaseController {

    @PersistenceContext
    private EntityManager manager;


    @PostMapping("/payments-paypal/{id}")
    @Transactional
    public ResponseEntity<?> processingPaypal(@PathVariable Long id, @RequestBody @Valid PaypalRequest request) {

        Purchase purchase = manager.find(Purchase.class, id);
        purchase.addPayment(request);

        manager.merge(purchase);

        return ResponseEntity.ok(purchase.toString());
    }
}
