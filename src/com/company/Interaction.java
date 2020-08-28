package com.company;

import javax.swing.text.DateFormatter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Interaction implements IDatabaseEntity {
    public static Interaction example = new Interaction("inter_001",new Date(),"001","facebook",Potential.negative);
    public static String fileName = "interactions.csv";
    public static String idPrefix = "inter_";
    private enum Potential{
        negative,
        neutral,
        positive;
    }
    private String id;
    private Date interactionDate;// like lead
    private String leadId;
    private String mean;
    private Potential potential; //input 0 = NEGATIVE 1 = NEUTRAL 2 = POSITIVE

    public Interaction(String id, Date interactionDate, String leadId, String mean, Potential potential) {
        this.id = id;
        this.interactionDate = interactionDate;
        this.leadId = leadId;
        this.mean = mean;
        this.potential = potential;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getInteractionDate() {
        return interactionDate;
    }

    public void setInteractionDate(Date interactionDate) {
        this.interactionDate = interactionDate;
    }

    public String getLeadId() {
        return leadId;
    }

    public void setLeadId(String leadId) {
        this.leadId = leadId;
    }

    public String getMean() {
        return mean;
    }

    public void setMean(String mean) {
        this.mean = mean;
    }

    public Potential getPotential() {
        return potential;
    }

    public void setPotential(Potential potential) {
        this.potential = potential;
    }

    @Override
    public String toString() {
    StringBuilder sb = new StringBuilder();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    sb.append("Interaction ID: ");
    sb.append(id);
    sb.append("\n");
    sb.append("Interaction date: ");
    sb.append(formatter.format(interactionDate));
    sb.append("\n");
    sb.append("Lead ID: ");
    sb.append(leadId);
    sb.append("\n");
    sb.append("Mean: ");
    sb.append(mean);
    sb.append("\n");
    sb.append("Potential: ");
    sb.append(potential);
    sb.append("\n");

        return sb.toString();
    }

    public static Interaction fromCSV(String row){
        String[] data = row.split(",");
        String id = data[0];
        Date interactionDate = null;
        try {
            interactionDate = DateParser.stringToDate(data[1]);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String leadId = data[2];
        String mean = data[3];
        Potential potential = Potential.valueOf(data[4]);
        return new Interaction(id,interactionDate,leadId,mean,potential);
    }
    @Override
    public String toCSV() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append(id);
        sb.append(",");
        sb.append(DateParser.dateToString(interactionDate));
        sb.append(",");
        sb.append(leadId);
        sb.append(",");
        sb.append(mean);
        sb.append(",");
        sb.append(potential);
        return sb.toString();
    }
}
