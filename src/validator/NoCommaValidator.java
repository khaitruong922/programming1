package validator;

public class NoCommaValidator implements IValidator {
    @Override
    public boolean validate(String s) {
        return !s.contains(",");
    }
}
