package menu;

import validator.IValidator;

import java.util.Scanner;

public class InputField {
    private static final Scanner sc = new Scanner(System.in);
    private String label;
    private String errorMessage;

    public InputField(String label) {
        this.label = label;
    }

    public InputField(String label, String errorMessage) {
        this(label);
        this.errorMessage = errorMessage;
    }

    public String next(IValidator validator) {
        System.out.print(label);
        String input = sc.nextLine();
        if (validator.validate(input)) {
            return input;
        }
        System.out.println(errorMessage);
        return next(validator);
    }

    public String next() {
        return next(s -> true);
    }
}
