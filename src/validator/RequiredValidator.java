package validator;

import validator.IValidator;

public class RequiredValidator implements IValidator {
    @Override
    public boolean validate(String s) {
        return !s.isEmpty();
    }
}
