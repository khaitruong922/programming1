package test;

import validator.EmailValidator;

public class EmailValidatorTest {
    public static void main(String[] args) {
        EmailValidator emailValidator = new EmailValidator();
        System.out.println(emailValidator.validate("huynh@gmail.com"));
        System.out.println(emailValidator.validate(""));
        System.out.println(emailValidator.validate("@"));
        System.out.println(emailValidator.validate("abc@"));
        System.out.println(emailValidator.validate("abc@a"));
        System.out.println(emailValidator.validate("abc@a."));
        System.out.println(emailValidator.validate("abc@b.."));
    }
}
