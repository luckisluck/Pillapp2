package com.dispenses.pill.pillapp.feature;

/**
 * Created by Win10 on 07/03/2018.
 */

public class bottleyGetSet {

    private int pillAmty;
    private String namey;
    private String starttimey;
    private String endtimey;


    private int _id;

    public bottleyGetSet()
    {

    }

    public bottleyGetSet(int _id,String namey, String starttimey, String endtimey, int pillAmty)
    {
        this._id=_id;
        this.namey =namey;
        this.starttimey=starttimey;
        this.endtimey=endtimey;
        this.pillAmty=pillAmty;
    }


    public bottleyGetSet(String namey, String starttimey, String endtimey, int pillAmty)
    {
        this.namey =namey;
        this.starttimey=starttimey;
        this.endtimey=endtimey;
        this.pillAmty=pillAmty;
    }

    public int getPillAmty() {
        return pillAmty;
    }

    public void setPillAmty(int pillAmty) {
        this.pillAmty = pillAmty;
    }

    public String getNamey() {
        return namey;
    }

    public void setNamey(String namey) {
        this.namey = namey;
    }

    public String getStarttimey() {
        return starttimey;
    }

    public void setStarttimey(String starttimey) {
        this.starttimey = starttimey;
    }

    public String getEndtimey() {
        return endtimey;
    }

    public void setEndtimey(String endtimey) {
        this.endtimey = endtimey;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }


}
