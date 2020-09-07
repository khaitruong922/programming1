package validator;

public class NameValidator implements IValidator{
    private final boolean required;

    public NameValidator(){ this.required = false; }

    public NameValidator(boolean required) { this.required = required; }

    @Override
    public boolean validate(String s) {
        for (char c: s.toCharArray()
             ) {
            if (Character.isDigit(c)){
                return false;
            }
        }
        return true;
    }
}
