package test;

import validator.EmailValidator;

public class EmailValidatorTest {
    public static void main(String[] args) {
        EmailValidator emailValidator = new EmailValidator();
        System.out.println(emailValidator.validate("huynh@gmail.com"));
    }
}
