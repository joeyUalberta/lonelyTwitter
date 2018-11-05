package ca.ualberta.cs.lonelytwitter;

import android.os.AsyncTask;
import android.util.Log;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.searchbox.client.JestResult;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;

/**
 * Created by romansky on 10/20/16.
 */
public class ElasticsearchTweetController {


    private static JestDroidClient client=null;
    //private static JestDroidClient client;


    public static class AddTweetsTask extends AsyncTask<Tweet,Void,Void>{

        protected Void doInBackground(Tweet... tweets) {
                setClient();
                Tweet tweet =tweets[0];
                Index index = new Index.Builder(tweet).index("wong1").type("tweet").build();
                try{
                    DocumentResult result = client.execute(index);
                    if(result.isSucceeded()){
                        tweet.setTweetId(result.getId());
                    }
                }catch(IOException e){
                    Log.d("Joey Error"," IOexception when executing client");
                }
                return null;
            }

    }
    public static class GetTweetsTask extends AsyncTask<String,Void,ArrayList<Tweet>>{

        protected ArrayList<Tweet> doInBackground(String ... params) {
            setClient();
            ArrayList<Tweet> tweets= new ArrayList<Tweet>();
            Search search = new Search.Builder(params[0]).addIndex("wong1")
                    .addType("tweet").build();
            try{
                JestResult result = client.execute(search);
                if(result.isSucceeded()){
                    List<NormalTweet> tweetList;
                    tweetList=result.getSourceAsObjectList(NormalTweet.class);
                    tweets.addAll(tweetList);
                }else{
                    Log.d("joey","unsucessful");
                }
            }catch(IOException e){
                Log.d("Joey Error",e.getMessage());
            }

            return tweets;
        }

    }
    /*
    public static class SearchTweetsTask extends AsyncTask<String,Void,ArrayList<Tweet>>{

        protected ArrayList<Tweet> doInBackground(String ... params) {

            setClient();
            ArrayList<Tweet> tweets= new ArrayList<Tweet>();
            Search search = new Search.Builder("").addIndex("wong1")
                    .addType("tweet").build();
            try{
                JestResult result = client.execute(search);
                if(result.isSucceeded()){
                    List<NormalTweet> tweetList;
                    tweetList=result.getSourceAsObjectList(NormalTweet.class);
                    for (Tweet tweet:tweetList){
                        if (tweet.getMessage().indexOf(params[0])!=-1){
                            tweets.add(tweet);
                            Log.d("joey","removed");
                        }else{
                            Log.d("joey","Not removed");
                        }
                    }
                }else{
                    Log.d("joey","search by keyword not working");
                }
            }catch(IOException e){
                Log.d("Joey Error",e.getMessage());
            }
            Log.d("joe",String.valueOf(tweets.size()));
            return tweets;
        }

    }*/


    public static class SearchTweetsTask extends AsyncTask<String,Void,ArrayList<Tweet>>{

        protected ArrayList<Tweet> doInBackground(String ... params) {

            setClient();
            ArrayList<Tweet> tweets= new ArrayList<Tweet>();
            String query = "{\n" +
                    "    \"query\": {\n" +
                    "        \"match\" :{ \"message\" : \""+params[0]+"\"}\n"+
                    "    }\n" +
                    "}";
            Log.d("joey",query);
            Search search = new Search.Builder(query).addIndex("wong1")
                    .addType("tweet").build();
            try{
                JestResult result = client.execute(search);
                if(result.isSucceeded()){
                    List<NormalTweet> tweetList;
                    tweetList=result.getSourceAsObjectList(NormalTweet.class);
                    for (Tweet tweet:tweetList){
                            tweets.add(tweet);
                    }
                }else{
                    Log.d("joey","search by keyword not working");
                }
            }catch(IOException e){
                Log.d("Joey Error",e.getMessage());
            }
            Log.d("joe",String.valueOf(tweets.size()));
            return tweets;
        }

    }

    public static void setClient(){
        if(client==null){
            DroidClientConfig config = new DroidClientConfig.Builder("http://cmput301.softwareprocess.es:8080/").build();
            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client=(JestDroidClient)factory.getObject();
        }
    }
    /*
    // TODO we need a function which adds tweets to elastic search
    public static class AddTweetsTask extends AsyncTask<NormalTweet, Void, Void> {

        @Override
        protected Void doInBackground(NormalTweet... tweets) {
            //verifySettings();

            for (NormalTweet tweet : tweets) {
                Index index = new Index.Builder(tweet).index("testing").type("tweet").build();

                try {
                    // where is the client?
                }
                catch (Exception e) {
                    Log.i("Error", "The application failed to build and send the tweets");
                }

            }
            return null;
        }
    }*/

    // TODO we need a function which gets tweets from elastic search
/*    public static class GetTweetsTask extends AsyncTask<String, Void, ArrayList<NormalTweet>> {
        @Override
        protected ArrayList<NormalTweet> doInBackground(String... search_parameters) {
            verifySettings();

            ArrayList<NormalTweet> tweets = new ArrayList<NormalTweet>();

                // TODO Build the query

            try {
               // TODO get the results of the query
            }
            catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }

            return tweets;
        }
    }*/



    /*
    public static void verifySettings() {
        if (client == null) {
            DroidClientConfig.Builder builder = new DroidClientConfig.Builder("http://cmput301.softwareprocess.es:8080");
            DroidClientConfig config = builder.build();

            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }
    }*/
}