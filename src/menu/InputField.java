package menu;

import java.util.Scanner;

public class InputField {
    private static Scanner sc = new Scanner(System.in);

    private InputField() {

    }

    public static String next(IValidator validator) {
        String input = sc.next();
        if (validator.validate(input)) {
            return input;
        }
        return next(validator);
    }

    public static String next() {
        return sc.next();
    }
}
