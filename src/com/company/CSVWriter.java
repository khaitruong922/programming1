package com.company;

public class CSVWriter {
    public static String getCSVRow(String[] fields) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < fields.length-1; i++) {
            sb.append(fields[i]);
            sb.append(",");
        }
        sb.append(fields[fields.length-1]);
        sb.append("\n");
        return sb.toString();
    }
}
