package com.dispenses.pill.pillapp.feature;

/**
 * Created by Win10 on 07/03/2018.
 */

public class bottlezGetSet {

    private int pillAmtz;
    private String namez;
    private String starttimez;
    private String endtimez;

    public bottlezGetSet()
    {

    }

    public bottlezGetSet(String namez, String starttimez, String endtimez, int pillAmtz)
    {
        this.namez =namez;
        this.starttimez=starttimez;
        this.endtimez=endtimez;
        this.pillAmtz=pillAmtz;
    }

    public int getPillAmtz() {
        return pillAmtz;
    }

    public void setPillAmtz(int pillAmtz) {
        this.pillAmtz = pillAmtz;
    }

    public String getNamez() {
        return namez;
    }

    public void setNamez(String namez) {
        this.namez = namez;
    }

    public String getStarttimez() {
        return starttimez;
    }

    public void setStarttimez(String starttimez) {
        this.starttimez = starttimez;
    }

    public String getEndtimez() {
        return endtimez;
    }

    public void setEndtimez(String endtimez) {
        this.endtimez = endtimez;
    }
}
