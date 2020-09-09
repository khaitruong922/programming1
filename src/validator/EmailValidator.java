package validator;

public class EmailValidator implements IValidator{
    @Override
    public boolean validate(String s) {
        String emailRegex = "^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})$";
        return s.matches(emailRegex);
    }
}
