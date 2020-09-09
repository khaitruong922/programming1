package test;

import validator.EmailValidator;

public class EmailValidatorTest {
    public static void main(String[] args) {
        EmailValidator emailValidator = new EmailValidator();
        System.out.println(emailValidator.validate("huynh@gmail.com"));
        System.out.println(emailValidator.validate("mysite@you.me.net"));
        System.out.println(emailValidator.validate("mysite@.com.my"));
        System.out.println(emailValidator.validate("mysite123@gmail.b"));
    }
}
