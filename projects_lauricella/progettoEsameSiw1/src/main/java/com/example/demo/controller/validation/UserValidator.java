package com.example.demo.controller.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.example.demo.model.User;
import com.example.demo.services.UserService;



@Component
public class UserValidator implements Validator {

    final Integer MAX_NAME_LENGTH = 100;
    final Integer MIN_NAME_LENGTH = 2;

    @Autowired
    UserService userService;

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;
        String firstName = user.getFirstname().trim();
        String lastName = user.getLastname().trim();

        if (firstName.trim().isEmpty())
            errors.rejectValue("firstname", "required");
        else if (firstName.length() < MIN_NAME_LENGTH || firstName.length() > MAX_NAME_LENGTH)
            errors.rejectValue("firstname", "size");

        if (lastName.trim().isEmpty())
            errors.rejectValue("lastname", "required");
        else if (lastName.length() < MIN_NAME_LENGTH || lastName.length() > MAX_NAME_LENGTH)
            errors.rejectValue("lastname", "size");
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

}
