package validator;

public class EmailValidator implements IValidator{
    @Override
    public boolean validate(String s) {
        String emailRegex = "^\\S{1,64}(@)\\S{1,255}+$";
        return s.matches(emailRegex);
    }
}
