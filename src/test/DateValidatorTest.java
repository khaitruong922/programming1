package test;

import validator.DateValidator;

public class DateValidatorTest {
    private static final DateValidator dateValidator = new DateValidator();

    public static void main(String[] args) {
        test("2001-09-22");
        test("2001-02-30");
        test("2001-02-29");
        test("2000-02-29");
        test("2001-01-32");
        test("2020-09-10");
        test("2020-09-11");
    }

    private static void test(String s) {
        boolean result = dateValidator.validate(s);
        System.out.println(s + ": " + result);
    }
}
