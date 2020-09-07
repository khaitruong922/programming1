package test;

import validator.PhoneValidator;

public class PhoneValidatorTest {
    public static void main(String[] args) {

        PhoneValidator phoneValidator = new PhoneValidator();
        System.out.println(phoneValidator.validate("2")); // 1 digit
        System.out.println(phoneValidator.validate("2222222")); // 7 digits
        System.out.println(phoneValidator.validate("1212121212"));  // 10 digits
        System.out.println(phoneValidator.validate("121212121212"));  // 12 digits
        System.out.println(phoneValidator.validate("1212121212121"));  // 13 digits
        System.out.println(phoneValidator.validate("121212a212"));  // with char
        System.out.println(phoneValidator.validate("")); // empty


    }

}
