package ru.topacademy.socialnetwork.extensions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.topacademy.socialnetwork.Models.User;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, User> {
	
	@Autowired
	private PasswordEncoder encoder;

    @Override
    public boolean isValid(User user, ConstraintValidatorContext context) {
        if (user.getPassword() == null || user.getConfirmPassword() == null) {
            return false;
        }
        if (!user.getPassword().equals(user.getConfirmPassword())) {
        	System.out.println("Password: " + user.getPassword() + " " + "Confirm password: " + user.getConfirmPassword());
            System.out.println("Passwords do not match!"); // Add a log here to see if it's being triggered
        }
        return user.getPassword().equals(user.getConfirmPassword());
    }
}