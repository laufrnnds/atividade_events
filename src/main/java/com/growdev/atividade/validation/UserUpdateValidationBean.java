package com.growdev.atividade.validation;

import com.growdev.atividade.dto.UserDTO;
import com.growdev.atividade.entities.User;
import com.growdev.atividade.exceptions.FieldMessage;
import com.growdev.atividade.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserUpdateValidationBean implements ConstraintValidator<UserUpdateValidation, UserDTO> {
    @Autowired
    UserRepository repository;

    @Autowired
    private HttpServletRequest request;

    @Override
    public void initialize(UserUpdateValidation constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }


    @Override
    public boolean isValid(UserDTO value, ConstraintValidatorContext context) {
        var uriAtributos = (Map<String,String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        String userId = uriAtributos.get("id");
        List<FieldMessage> list = new ArrayList<>();

        User usuario =  repository.findByEmail(value.getEmail());

        if(usuario != null && usuario.getId() !=  Long.parseLong(userId)){
            list.add(new FieldMessage("email", "Esse email já existe"));
        }
        for(FieldMessage e : list){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
                    .addConstraintViolation();
        }
        return list.isEmpty();
    }
}
