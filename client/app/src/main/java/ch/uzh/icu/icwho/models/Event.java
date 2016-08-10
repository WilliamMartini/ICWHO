package ch.uzh.icu.icwho.models;

/**
 * Created by Flo on 10.08.2016.
 */

public class Event {
    // fields
    private String name;
    // TODO: start and end date
    private int dateY;
    private int dateM;
    private int dateD;
    private int startTimeH;
    private int startTimeM;
    private int endTimeH;
    private int endTimeM;
    private String description;
    private String link;
    private String signUp;


    // constructor
    public Event(String name, int dateY, int dateM, int dateD, int startTimeH, int startTimeM, int endTimeH, int endTimeM, String description) {
        this.name = name;
        this.dateY = dateY;
        this.dateM = dateM;
        this.dateD = dateD;
        this.startTimeH = startTimeH;
        this.startTimeM = startTimeM;
        this.endTimeH = endTimeH;
        this.endTimeM = endTimeM;
        this.description = description;
    }


    // getter & setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDateY() {
        return dateY;
    }

    public void setDateY(int dateY) {
        this.dateY = dateY;
    }

    public int getDateM() {
        return dateM;
    }

    public void setDateM(int dateM) {
        this.dateM = dateM;
    }

    public int getDateD() {
        return dateD;
    }

    public void setDateD(int dateD) {
        this.dateD = dateD;
    }

    public int getStartTimeH() {
        return startTimeH;
    }

    public void setStartTimeH(int startTimeH) {
        this.startTimeH = startTimeH;
    }

    public int getStartTimeM() {
        return startTimeM;
    }

    public void setStartTimeM(int startTimeM) {
        this.startTimeM = startTimeM;
    }

    public int getEndTimeH() {
        return endTimeH;
    }

    public void setEndTimeH(int endTimeH) {
        this.endTimeH = endTimeH;
    }

    public int getEndTimeM() {
        return endTimeM;
    }

    public void setEndTimeM(int endTimeM) {
        this.endTimeM = endTimeM;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        String d, m;

        if (dateD < 10) {
            d = "0" + dateD;
        } else {
            d = String.valueOf(dateD);
        }
        if (dateM < 10) {
            m = "0" + dateM;
        } else {
            m = String.valueOf(dateM);
        }

        return (d + "." + m + "." + dateY);
    }

    public String getStartTime() {
        String h, m;

        if (startTimeH < 10) {
            h = "0" + startTimeH;
        } else {
            h = String.valueOf(startTimeH);
        }
        if (startTimeM < 10) {
            m = "0" + startTimeM;
        } else {
            m = String.valueOf(startTimeM);
        }

        return (h + ":" + m);
    }

    public String getEndTime() {
        String h, m;

        if (endTimeH < 10) {
            h = "0" + endTimeH;
        } else {
            h = String.valueOf(endTimeH);
        }
        if (endTimeM < 10) {
            m = "0" + endTimeM;
        } else {
            m = String.valueOf(endTimeM);
        }

        return (h + ":" + m);
    }
}
