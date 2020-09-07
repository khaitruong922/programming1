package test;

import validator.NameValidator;

public class NameValidatorTest {
    public static void main(String[] args) {
        NameValidator nameValidator = new NameValidator();
        System.out.println(nameValidator.validate("Hello"));
        System.out.println(nameValidator.validate(""));
        System.out.println(nameValidator.validate("1Hello"));
        System.out.println(nameValidator.validate("Hello World"));
        System.out.println(nameValidator.validate("Hello World 1"));




    }
}
