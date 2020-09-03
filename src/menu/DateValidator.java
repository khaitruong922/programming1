package menu;

import com.company.DateParser;

import java.text.ParseException;

public class DateValidator implements IValidator {
    private final boolean required;

    public DateValidator() {
        this.required = true;
    }

    public DateValidator(boolean required) {
        this.required = required;
    }

    @Override
    public boolean validate(String s) {
        if (!required && s.isEmpty()) return true;
        try {
            DateParser.parse(s);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}
