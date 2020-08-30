package com.company;

import java.text.ParseException;
import java.util.Date;

public class Lead implements IDatabaseEntity {
    public static Lead example = new Lead("lead_001", "Khai", new Date(), true, "090", "abc@a.com", "address");
    public static String fileName = "leads.csv";
    public static String idPrefix = "lead_";
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

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public boolean isMale() {
        return isMale;
    }

    public void setMale(boolean male) {
        isMale = male;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Lead ID: ");
        sb.append(id);
        sb.append("\n");
        sb.append("Name: ");
        sb.append(name);
        sb.append("\n");
        sb.append("Date of birth: ");
        sb.append(birthDate.getTime());
        sb.append("\n");
        sb.append("Gender: ");
        sb.append(isMale ? "Male" : "Female");
        sb.append("\n");
        sb.append("Phone: ");
        sb.append(phone);
        sb.append("\n");
        sb.append("Email :");
        sb.append(email);
        sb.append("\n");
        sb.append("Address: ");
        sb.append(address);
        sb.append("\n");
        return sb.toString();
    }
    public static String[] fillArray(String[] arr){
        String[] newArr = new String[7];
        for (int i = 0; i <7 ; i++) {
            newArr[i] = "";
        }
        if (arr.length < 7){
            for (int i = 0; i <arr.length ; i++) {
                newArr[i] = arr[i];
            }
        } else {
            for (int i = 0; i < 7 ; i++) {
                newArr[i] = arr[i];
            }
        }
        return newArr;
    }

    public static Lead fromCSV(String row) {
        String[] data = row.split(",");
        String[] newData = fillArray(data);
        String id = newData[0];
        String name = newData[1];
        Date birthDate = null;
        try {
            birthDate = DateParser.stringToDate(newData[2]);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        boolean isMale = Boolean.parseBoolean(newData[3]);
        String phone = newData[4];
        String email = newData[5];
        String address = newData[6];
        return new Lead(id, name, birthDate, isMale, phone, email, address);

    }

    @Override
    public String toCSV() {
        StringBuilder sb = new StringBuilder();
        sb.append(id);
        sb.append(",");
        sb.append(name);
        sb.append(",");
        sb.append(DateParser.dateToString(birthDate));
        sb.append(",");
        sb.append(isMale);
        sb.append(",");
        sb.append(phone);
        sb.append(",");
        sb.append(email);
        sb.append(",");
        sb.append(address);
        sb.append("\n");
        return sb.toString();
    }
}

