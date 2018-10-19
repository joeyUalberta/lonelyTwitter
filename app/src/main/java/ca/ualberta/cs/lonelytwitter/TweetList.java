package ca.ualberta.cs.lonelytwitter;

import java.util.ArrayList;

public class TweetList {
    private ArrayList<Tweet> tweets = new ArrayList<Tweet>();

    public void addTweet(Tweet t){
        tweets.add(t);
    }

    public Boolean hasTweet(Tweet t){
        return tweets.contains(t);
    }
    public void deleteTweet(Tweet t){
        tweets.remove(t);
    }
    public Tweet getTweet(int index){
        return tweets.get(index);
    }
    public int getCount(){
        return tweets.size();
    }
}
