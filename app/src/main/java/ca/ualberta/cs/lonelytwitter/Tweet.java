package ca.ualberta.cs.lonelytwitter;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by joey on 2018-09-22.
 */

public class Tweet {
    private String description;
    private Date date;
    private ArrayList<BaseMood> moodList;
    public  Tweet(String a,Date b) {
        this.description = a;
        this.date = b;
    }
    public String getDescription(){
        return this.description;
    }
    public Date getDate(){
        return this.date;
    }
    public ArrayList<BaseMood> getMoodList(){
        return this.moodList;
    }
    public void addMood(BaseMood mood){
        this.moodList.add(mood);
    }
}
