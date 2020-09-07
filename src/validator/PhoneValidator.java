package validator;

public class PhoneValidator implements IValidator {
    @Override
    public boolean validate(String s) {
        String phoneRegex = "^(\\d{7,12})$";
        return s.matches(phoneRegex);
    }
}
