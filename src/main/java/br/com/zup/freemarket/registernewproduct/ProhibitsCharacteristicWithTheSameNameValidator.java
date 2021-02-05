package br.com.zup.freemarket.registernewproduct;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Set;

public class ProhibitsCharacteristicWithTheSameNameValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return NewProductRequest.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if(errors.hasErrors())
            return;

        NewProductRequest request = (NewProductRequest) target;
        Set<String> equalNames = request.searchEqualCharacteristics();
        if(!equalNames.isEmpty()){
            errors.rejectValue("characteristics", null, "Exitem categorias com nomes iguais: " + equalNames);
        }


    }
}
