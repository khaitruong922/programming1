package database;

import util.DateParser;

import java.text.ParseException;
import java.util.Date;

public class Lead implements IDatabaseEntity {
    public static String fileName = "leads.csv";
    public static String idPrefix = "lead_";
    public static String[] fields = new String[]{"ID","Name","Birth date","Gender","Phone","Email","Address"};
    private String id;
    private String name;
    private Date birthDate;
    private boolean isMale;
    private String phone;
    private String email;
    private String address;

    public Lead(String id, String name, Date birthDate, boolean isMale, String phone, String email, String address) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.isMale = isMale;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public Date getBirthDate() {
        return birthDate;
    }
    public boolean isMale() {
        return isMale;
    }
    public String getPhone() {
        return phone;
    }
    public String getEmail() {
        return email;
    }
    public String getAddress() {
        return address;
    }

    public static Lead fromCSV(String row) {
        String[] fields = row.split(",");
        String id = fields[0];
        String name = fields[1];
        Date birthDate = null;
        try {
            birthDate = DateParser.parse(fields[2]);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        boolean isMale = Boolean.parseBoolean(fields[3]);
        String phone = fields[4];
        String email = fields[5];
        String address = fields[6];
        return new Lead(id, name, birthDate, isMale, phone, email, address);
    }

    @Override
    public String toCSV() {
        return String.join(",", id, name, DateParser.format(birthDate), Boolean.toString(isMale), phone, email, address) + "\n";
    }

    public String[] toStringArray() {
        return new String[]{id, name, DateParser.format(birthDate), isMale ? "Male" : "Female", phone, email, address};
    }

}

