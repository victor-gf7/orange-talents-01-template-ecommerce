package br.com.zup.freemarket.registernewcategory;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
public class NewCategoryController {

    @PersistenceContext
    private EntityManager manager;

    @PostMapping("/category")
    @Transactional
    public ResponseEntity<?> toRegisterCategory(@RequestBody @Valid NewCategoryRequest request){

        Category category = request.converterToModel(manager);

        manager.persist(category);

        return ResponseEntity.ok().build();
    }
}
