package com.company;

import menu.*;

public class Main {

    public static void main(String[] args) throws Exception {
        String input = InputField.next(new DateValidator());
        System.out.println(input);
    }
}
