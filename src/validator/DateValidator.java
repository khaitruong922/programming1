package validator;

import util.DateParser;

import java.text.ParseException;
import java.util.Date;

public class DateValidator implements IValidator {
    @Override
    public boolean validate(String s) {
        try {
            Date d = DateParser.parse(s);
            Date now = new Date();
            if (d.getTime() > now.getTime()) return false;
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}
