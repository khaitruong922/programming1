package com.company;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Lead implements IDatabaseEntity {
    public static Lead example = new Lead("lead_001", "Khai", new Date(), true, "090", "abc@a.com", "address");
    public static String idPrefix = "lead";
    private String id;
    private String name;
    private Date birthDate;//simple date format try catch
    private boolean isMale;// ask type 0:female 1:male
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
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        sb.append("id: ");
        sb.append(id);
        sb.append("\n");
        sb.append("name: ");
        sb.append(name);
        sb.append("\n");
        sb.append("Date of birth: ");
        sb.append(formatter.format(birthDate));
        sb.append("\n");
        sb.append("gender: ");
        if (isMale){
            sb.append("male");
        }else {
            sb.append("female");
        }
        sb.append("\n");
        sb.append("phone: ");
        sb.append(phone);
        sb.append("\n");
        sb.append("email :");
        sb.append(email);
        sb.append("\n");
        sb.append("address: ");
        sb.append(address);
        sb.append("\n");
        return sb.toString();
    }

    @Override
    public String toCSV() {
        StringBuilder sb = new StringBuilder();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        sb.append(id);
        sb.append(",");
        sb.append(name);
        sb.append(",");
        sb.append(formatter.format(birthDate));
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

