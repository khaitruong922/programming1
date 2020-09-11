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
            if (d.getTime() > now.getTime()) {
                System.out.println("Future date is not allowed.");
                return false;
            }
            return true;
        } catch (ParseException e) {
            System.out.println("Invalid date format.");
            return false;
        }
    }
}
