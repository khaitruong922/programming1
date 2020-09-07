package validator;

public class NameValidator implements IValidator {
    @Override
    public boolean validate(String s) {
        String nameRegex = "^[a-zA-Z\\s]+$";
        return s.matches(nameRegex);
    }
}
