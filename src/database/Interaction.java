package database;

import util.DateParser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Interaction implements IDatabaseEntity {
    public static Interaction example = new Interaction("inter_001", new Date(), "001", "facebook", "negative");
    public static String fileName = "interactions.csv";
    public static String idPrefix = "inter_";
    public static String[] fields = new String[]{"ID", "Interaction date", "Lead ID", "Mean", "Potential"};
    private String id;
    private Date interactionDate;// like lead
    private String leadId;
    private String mean;
    private String potential; //input 0 = NEGATIVE 1 = NEUTRAL 2 = POSITIVE

    public Interaction(String id, Date interactionDate, String leadId, String mean, String potential) {
        this.id = id;
        this.interactionDate = interactionDate;
        this.leadId = leadId;
        this.mean = mean;
        this.potential = potential;
    }

    public String getId() {
        return id;
    }

    public Date getInteractionDate() {
        return interactionDate;
    }

    public String getLeadId() {
        return leadId;
    }

    public String getMean() {
        return mean;
    }

    public String getPotential() {
        return potential;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setInteractionDate(Date interactionDate) {
        this.interactionDate = interactionDate;
    }

    public void setLeadId(String leadId) {
        this.leadId = leadId;
    }

    public void setMean(String mean) {
        this.mean = mean;
    }

    public void setPotential(String potential) {
        this.potential = potential;
    }

    public static Interaction fromCSV(String row) {
        String[] fields = row.split(",");
        String id = fields[0];
        Date interactionDate = null;
        try {
            interactionDate = DateParser.parse(fields[1]);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String leadId = fields[2];
        String mean = fields[3];
        String potential = fields[4];
        return new Interaction(id, interactionDate, leadId, mean, potential);
    }

    @Override
    public String toCSV() {
        return String.join(",", id, DateParser.format(interactionDate), leadId, mean, potential) + "\n";
    }

    public String[] toStringArray() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM dd, yyyy");
        return new String[]{id, simpleDateFormat.format(interactionDate), leadId, mean, potential};
    }
}
