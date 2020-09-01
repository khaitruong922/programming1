package menu;

import com.company.DateParser;

import java.text.ParseException;

public class DateValidator implements IValidator {
    @Override
    public boolean validate(String s) {
        try {
            DateParser.parse(s);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}
