package br.com.zup.freemarket.uploadimage;


import br.com.zup.freemarket.registernewproduct.Product;
import br.com.zup.freemarket.registernewuser.Usuario;
import br.com.zup.freemarket.token.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.io.IOException;

import java.util.Set;

@RestController
public class UploadImageController {

    @Autowired
    private UploadImageService uploadImageService;

    @Autowired
    private TokenService tokenService;

    @PersistenceContext
    private EntityManager manager;

    @PostMapping("/product/{id}/image")
    @Transactional
    public ResponseEntity<?> uploadFile(@Valid NewImageRequest request, @PathVariable Long id, @RequestHeader("Authorization") String token) throws IOException {

        Usuario loggedUser = tokenService.getLoggedUser(token.substring(7, token.length()));

        Product product = manager.find(Product.class, id);
        if(product.getUser().equals(loggedUser)){
            Set<String> links = uploadImageService.uploadImageFake(request.getFiles());
            product.associateImage(links);
            manager.merge(product);
            return ResponseEntity.ok(product.toString());
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

    }
}
