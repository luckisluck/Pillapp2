package com.dispenses.pill.pillapp.feature;

/**
 * Created by Win10 on 07/03/2018.
 */

public class bottlexGetSet {

    private int pillAmt;
    private int _id;
    private String name;
    private String starttime;
    private String endtime;

    public bottlexGetSet()
    {

    }

    public bottlexGetSet(int _id,String name, String starttime, String endtime, int pillAmt)
        {
        this._id=_id;
        this.name =name;
        this.starttime=starttime;
        this.endtime=endtime;
        this.pillAmt=pillAmt;
        }

    public bottlexGetSet(String name, String starttime, String endtime, int pillAmt)
    {
        this.name =name;
        this.starttime=starttime;
        this.endtime=endtime;
        this.pillAmt=pillAmt;
    }

    public int getPillAmt() {
        return pillAmt;
    }

    public String getName() {
        return name;
    }

    public String getStarttime() {
        return starttime;
    }
    public String getEndtime() {
        return endtime;
    }


    public void setPillAmt(int pillAmt) {
        this.pillAmt = pillAmt;
    }



    public void setName(String name) {
        this.name = name;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }


    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }



    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }
}
