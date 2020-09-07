package validator;

public class PhoneValidator implements IValidator {
    private final boolean required;

    public PhoneValidator() { this.required = false; }

    public PhoneValidator(boolean required) {
        this.required = required;
    }

    @Override
    public boolean validate(String s) {
        int validating = 0;
        if (!required && s.isEmpty()) return true;
        if (s.length() > 6 && s.length() < 13) {
        try {
            Long.parseLong(s);
            return true;
        } catch (NumberFormatException e) { return false; }
        }
        return false;
    }
}
