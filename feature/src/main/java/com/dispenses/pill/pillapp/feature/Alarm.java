package com.dispenses.pill.pillapp.feature;

/**
 * Created by Win10 on 07/02/2018.
 */

public class Alarm {

    private int    _id;
    private String _alarmname;
    private String  time;

    public Alarm(){

    }
    public Alarm(int _id, String _alarmname, String time) {
        this._id = _id;
        this._alarmname = _alarmname;
        this.time = time;
    }


    public Alarm(String _alarmname, String time) {
        this._alarmname = _alarmname;
        this.time = time;
    }

    public void setID(int _id) {
        this._id = _id;
    }

    public int getID() {
        return _id;
    }

    public void setAlarmName(String _alarmname) {
        this._alarmname = _alarmname;
    }

    public String getAlarmName() {
        return _alarmname;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }



}
