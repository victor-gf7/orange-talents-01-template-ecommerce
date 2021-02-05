package br.com.zup.freemarket.makepurchase;

import org.springframework.web.util.UriComponentsBuilder;

public enum ChosenGateway {
    PAYPAL{
        @Override
        String createURL(Purchase purchase, UriComponentsBuilder builder){

            String urlReturnAfterPaymentPaypal = builder
                    .path("/payments-paypal/{id}")
                    .buildAndExpand(purchase.getId()).toString();

            return "paypal.com/"+purchase.getId()+"?redirectUrl="+urlReturnAfterPaymentPaypal;

        }
    },
    PAGSEGURO{
        @Override
        String createURL(Purchase purchase, UriComponentsBuilder builder){

            String urlReturnAfterPaymentPagseguro = builder
                    .path("/payments-pagseguro/{id}")
                    .buildAndExpand(purchase.getId()).toString();

            return "pagseguro.com?returnId="+purchase.getId()+"&redirectUrl="+urlReturnAfterPaymentPagseguro;

        }

    };

    abstract String createURL(Purchase purchase, UriComponentsBuilder builder);
}
