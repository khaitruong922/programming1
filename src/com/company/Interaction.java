package com.company;

import java.util.Date;

public class Interaction implements IDatabaseEntity {
    @Override
    public String toCSV() {
        return null;
    }

    private enum Potential{
        NEGATIVE,
        NEUTRAL,
        POSITIVE
    }
    private String id;
    private Date interactionDate;// like lead
    private String leadId;
    private String mean;
    private Potential potential; //input 0 = NEGATIVE 1 = NEUTRAL 2 = POSITIVE

}
