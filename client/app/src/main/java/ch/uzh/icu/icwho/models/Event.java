package ch.uzh.icu.icwho.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Flo on 10.08.2016.
 */

public class Event {
    // fields
    private String name;
    private int startDateY;
    private int startDateM;
    private int startDateD;
    private int endDateY;
    private int endDateM;
    private int endDateD;
    private int startTimeH;
    private int startTimeM;
    private int endTimeH;
    private int endTimeM;
    private String description;
    private String link;
    private String signUp;
    private String location;


    // constructors
    public Event() {}

    public Event(String name, int startDateY, int startDateM, int startDateD, int endDateY, int endDateM, int endDateD, int startTimeH, int startTimeM, int endTimeH, int endTimeM, String description, String link, String signUp, String location) {
        this.name = name;
        this.startDateY = startDateY;
        this.startDateM = startDateM;
        this.startDateD = startDateD;
        this.endDateY = endDateY;
        this.endDateM = endDateM;
        this.endDateD = endDateD;
        this.startTimeH = startTimeH;
        this.startTimeM = startTimeM;
        this.endTimeH = endTimeH;
        this.endTimeM = endTimeM;
        this.description = description;
        this.link = link;
        this.signUp = signUp;
        this.location = location;
    }

    public Event(JSONObject jo) throws JSONException {
        this.name = jo.getString("name");
        this.startDateY = Integer.parseInt(jo.getString("startDateY"));
        this.startDateM = Integer.parseInt(jo.getString("startDateM"));
        this.startDateD = Integer.parseInt(jo.getString("startDateD"));
        this.endDateY = Integer.parseInt(jo.getString("endDateY"));
        this.endDateM = Integer.parseInt(jo.getString("endDateM"));
        this.endDateD = Integer.parseInt(jo.getString("endDateD"));
        this.startTimeH = Integer.parseInt(jo.getString("startTimeH"));
        this.startTimeM = Integer.parseInt(jo.getString("startTimeM"));
        this.endTimeH = Integer.parseInt(jo.getString("endTimeH"));
        this.endTimeM = Integer.parseInt(jo.getString("endTimeM"));
        this.description = jo.getString("description");
        this.link = jo.getString("link");
        this.signUp = jo.getString("signUp");
        this.location = jo.getString("location");
    }


    // getter & setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStartDateY() {
        return startDateY;
    }

    public void setStartDateY(int startDateY) {
        this.startDateY = startDateY;
    }

    public int getStartDateM() {
        return startDateM;
    }

    public void setStartDateM(int startDateM) {
        this.startDateM = startDateM;
    }

    public int getStartDateD() {
        return startDateD;
    }

    public void setStartDateD(int startDateD) {
        this.startDateD = startDateD;
    }

    public int getEndDateY() {
        return endDateY;
    }

    public void setEndDateY(int endDateY) {
        this.endDateY = endDateY;
    }

    public int getEndDateM() {
        return endDateM;
    }

    public void setEndDateM(int endDateM) {
        this.endDateM = endDateM;
    }

    public int getEndDateD() {
        return endDateD;
    }

    public void setEndDateD(int endDateD) {
        this.endDateD = endDateD;
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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getSignUp() {
        return signUp;
    }

    public void setSignUp(String signUp) {
        this.signUp = signUp;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStartDate() {
        String d, m;

        if (startDateD < 10) {
            d = "0" + startDateD;
        } else {
            d = String.valueOf(startDateD);
        }
        if (startDateM < 10) {
            m = "0" + startDateM;
        } else {
            m = String.valueOf(startDateM);
        }

        return (d + "." + m + "." + startDateY);
    }

    public String getEndDate() {
        String d, m;

        if (endDateD < 10) {
            d = "0" + endDateD;
        } else {
            d = String.valueOf(endDateD);
        }
        if (endDateM < 10) {
            m = "0" + endDateM;
        } else {
            m = String.valueOf(endDateM);
        }

        return (d + "." + m + "." + endDateY);
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
