package com.dispenses.pill.pillapp.feature;

public class historyGetSet {

    private int _id;
    private String pillBox;
    private String pillAmt;
    private String time;

    public historyGetSet(){


    }

    public historyGetSet(int _id, String pillBox, String pillAmt  ,String time ){

        this._id=_id;
        this.pillBox=pillBox;
        this.pillAmt=pillAmt;
        this.time=time;

    }

    public historyGetSet(String pillBox, String pillAmt  ,String time ){
        this.pillBox=pillBox;
        this.pillAmt=pillAmt;
        this.time=time;

    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getPillBox() {
        return pillBox;
    }

    public void setPillBox(String pillBox) {
        this.pillBox = pillBox;
    }

    public String getPillAmt() {
        return pillAmt;
    }

    public void setPillAmt(String pillAmt) {
        this.pillAmt = pillAmt;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }



}
