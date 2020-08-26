package com.company;

import java.util.Date;

public class Interaction {
    private enum Potential{
        NEGATIVE,
        NEUTRAL,
        POSITIVE
    }
    private String id;
    private Date interactionDate;// like lead
    private String leadId;
    private String means;
    private Potential potential; //input 0 = NEGATIVE 1 = NEUTRAL 2 = POSITIVE

}
