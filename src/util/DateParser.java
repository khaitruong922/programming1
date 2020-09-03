package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class DateParser {
    private DateParser() {
    }

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public static Date parse(String s) throws ParseException {
        return sdf.parse(s);
    }

    public static String format(Date d) {
        if (d == null) return "";
        return sdf.format(d);
    }
}
