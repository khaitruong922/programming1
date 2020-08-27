package com.company;

import javax.swing.text.DateFormatter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Interaction implements IDatabaseEntity {
    public static Interaction example = new Interaction("inter_001",new Date(),"001","facebook",Potential.NEGATIVE);
    public static String idPrefix = "inter";
    private enum Potential{
        NEGATIVE,
        NEUTRAL,
        POSITIVE;

        public String toLowerCase(){
            return name().toLowerCase();
        }
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
    sb.append("id: ");
    sb.append(id);
    sb.append("\n");
    sb.append("interaction date: ");
    sb.append(formatter.format(interactionDate));
    sb.append("\n");
    sb.append("leadId: ");
    sb.append(leadId);
    sb.append("\n");
    sb.append("mean: ");
    sb.append(mean);
    sb.append("\n");
    sb.append("potential: ");
    sb.append(potential.toLowerCase());
    sb.append("\n");

        return sb.toString();
    }
    @Override
    public String toCSV() {
        StringBuilder sb = new StringBuilder();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        sb.append(id);
        sb.append(",");
        sb.append(formatter.format(interactionDate));
        sb.append(",");
        sb.append(leadId);
        sb.append(",");
        sb.append(mean);
        sb.append(",");
        sb.append(potential.toLowerCase());
        sb.append("\n");
        return sb.toString();
    }
}
