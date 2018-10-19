package ca.ualberta.cs.lonelytwitter;

import android.test.ActivityInstrumentationTestCase2;

import junit.framework.Assert;

public class TweetListTest extends ActivityInstrumentationTestCase2 {
    public TweetListTest(){
        super(LonelyTwitterActivity.class);
    }
    public void testAddTweet(){
        TweetList tweets = new TweetList();
        Tweet new_tweet =new NormalTweet("Normal tweet");
        tweets.addTweet(new_tweet);
        assertTrue(tweets.hasTweet(new_tweet));
    }
    public void testDeleteTweet(){
        TweetList tweets = new TweetList();
        Tweet new_tweet =new NormalTweet("Normal tweet");
        tweets.addTweet(new_tweet);
        tweets.deleteTweet(new_tweet);
        Assert.assertFalse(tweets.hasTweet(new_tweet));
    }
    public void testHasTweet(){
        TweetList tweets = new TweetList();
        Tweet new_tweet =new NormalTweet("Normal tweet");
        Tweet fake_tweet = new NormalTweet("fake");
        tweets.addTweet(new_tweet);
        assertTrue(tweets.hasTweet(new_tweet));
        assertFalse(tweets.hasTweet(fake_tweet));
        tweets.deleteTweet(new_tweet);
        assertFalse(tweets.hasTweet(new_tweet));
        assertFalse(tweets.hasTweet(fake_tweet));
    }
    public void testGetTweet(){
        TweetList tweets = new TweetList();
        Tweet new_tweet =new NormalTweet("Normal tweet");
        Tweet fake_tweet = new NormalTweet("fake");
        tweets.addTweet(new_tweet);
        Tweet returnTweet = tweets.getTweet(0);
        tweets.addTweet(fake_tweet);
        Tweet returnTweet1 = tweets.getTweet(1);
        assertEquals(returnTweet,new_tweet);
        assertEquals(fake_tweet,returnTweet1);
        assertNotSame(returnTweet,returnTweet1);
    }
    public void testGetCount(){
        TweetList tweets = new TweetList();
        assertEquals(0,tweets.getCount());
        Tweet new_tweet =new NormalTweet("Normal tweet");
        Tweet fake_tweet = new NormalTweet("fake");
        tweets.addTweet(new_tweet);
        assertEquals(1,tweets.getCount());
        Tweet returnTweet = tweets.getTweet(0);
        tweets.addTweet(fake_tweet);
        assertEquals(2,tweets.getCount());
        tweets.deleteTweet(new_tweet);
        assertEquals(1,tweets.getCount());
        tweets.deleteTweet(fake_tweet);
        assertEquals(0,tweets.getCount());
    }
}
