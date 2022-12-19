package com.growdev.atividade.validation;

import com.growdev.atividade.dto.UserDTO;
import com.growdev.atividade.dto.UserInsertDTO;
import com.growdev.atividade.entities.User;
import com.growdev.atividade.exceptions.FieldMessage;
import com.growdev.atividade.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class UserInsertValidationBean implements ConstraintValidator<UserInsertValidation, UserDTO> {

    @Autowired
    UserRepository repository;


    @Override
    public void initialize(UserInsertValidation constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(UserDTO value, ConstraintValidatorContext context) {
        List<FieldMessage> list = new ArrayList<>();

        User user =  repository.findByEmail(value.getEmail());

        if(user != null){
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
