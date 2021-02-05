package br.com.zup.freemarket.makepurchase;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class GatewayPurchaseController {

    @PostMapping("/payments-paypal/{id}")
    public ResponseEntity<?> processingPaypal(@PathVariable Long id, @RequestBody @Valid PaypalRequest request) {

        return ResponseEntity.ok(request.toString());
    }
}
