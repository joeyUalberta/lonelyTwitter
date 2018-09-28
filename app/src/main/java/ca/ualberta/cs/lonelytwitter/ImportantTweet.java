package ca.ualberta.cs.lonelytwitter;


import java.util.Date;

public class ImportantTweet extends Tweet {

    //Empty argument constructor with default values
    public ImportantTweet() {
        //Call the parent constructor to: avoid duplication!
        super();
    }

    public ImportantTweet(String message) {
        super(message);
    }

    @Override
    public Boolean isImportant() {
        return true;
    }
}
