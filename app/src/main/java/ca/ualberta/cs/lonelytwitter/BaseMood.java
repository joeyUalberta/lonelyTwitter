package ca.ualberta.cs.lonelytwitter;

import java.util.Date;

/**
 * Created by joey on 2018-09-22.
 */

public abstract class BaseMood {
    protected Date date;
    public BaseMood(){
        this.date = new Date();
    }
    public BaseMood(Date date){
        this.date=date;
    }
    public void setDate(Date date){
        this.date=date;
    }
    public Date getDate(){
        return this.date;
    }
    public abstract String tellMood();
}
