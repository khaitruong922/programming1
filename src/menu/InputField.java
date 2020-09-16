package menu;

import validator.IValidator;

import java.util.Scanner;

public class InputField {
    private String label;
    private boolean required;

    public InputField(String label) {
        this.label = label;
        this.required = true;
    }

    public InputField(String label, boolean required) {
        this.label = label;
        this.required = required;
    }

    public String next(IValidator validator, String errorMessage) {
        System.out.print(label);
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        if (!required && input.isEmpty()) return input;
        if (required && input.isEmpty()) {
            System.out.println("Field is missing.");
            return next(validator, errorMessage);
        }
        // Check if the input is valid
        if (validator.validate(input)) return input;
        // Print error message and ask the user to type again if it is not valid
        if (!errorMessage.isEmpty()) System.out.println(errorMessage);
        return next(validator, errorMessage);
    }

    public String next(IValidator validator) {
        return next(validator, "Invalid input.");
    }

    public String next() {
        return next(s -> true);
    }
}
