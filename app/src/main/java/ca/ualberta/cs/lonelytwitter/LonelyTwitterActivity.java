package ca.ualberta.cs.lonelytwitter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class LonelyTwitterActivity extends Activity {

	private static final String FILENAME = "file.sav";
	private EditText bodyText;
	private ListView oldTweetsList;
	private ArrayList<Tweet> tweetList = new ArrayList<Tweet>();
	private ArrayAdapter<Tweet> adapter;

	public static final String SEND_MSG = "ca.ualberta.cs.lonelytwitter.MSG";


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		bodyText = (EditText) findViewById(R.id.body);
		Button saveButton = (Button) findViewById(R.id.save);
		Button searchButton = (Button) findViewById(R.id.searchBtn);
		oldTweetsList = (ListView) findViewById(R.id.oldTweetsList);

		saveButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				setResult(RESULT_OK);
				String text = bodyText.getText().toString();
				Tweet newTweet = new NormalTweet(text);
				tweetList.add(newTweet);
				adapter.notifyDataSetChanged();
				new ElasticsearchTweetController.AddTweetsTask().execute(newTweet);
				 // TODO replace this with elastic search
				sendMessage();
			}
		});

		searchButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
			    ArrayList<Tweet> tempTweetList;
				setResult(RESULT_OK);
				tweetList.clear();
				String text = bodyText.getText().toString();
				try {
					tempTweetList = new ElasticsearchTweetController.SearchTweetsTask().execute(text).get();
                    tweetList.clear();
                    tweetList.addAll(tempTweetList);
				}catch(Exception e){
					Log.d("joey","error in displaying tweetList");
				}
				for(Tweet tweet:tweetList){
					Log.d("tweetList result ",tweet.getMessage());
					Log.d("tweetListString ",tweet.toString());
				}
				adapter.notifyDataSetChanged();
			}
		});


	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		try {
			tweetList = new ElasticsearchTweetController.GetTweetsTask().execute("").get();
		}catch(Exception e){
			Log.d("Joey ","error occur when loading tweet");
		}
		Log.d("joey",String.valueOf(tweetList.size()));
		adapter = new ArrayAdapter<Tweet>(this,
				R.layout.list_item, tweetList);
		oldTweetsList.setAdapter(adapter);
	}

	private void sendMessage(){
		Intent intent = new Intent(this,EditTweetActivity.class);
		EditText et=(EditText) findViewById(R.id.body);
		String message =et.getText().toString();
		intent.putExtra(SEND_MSG,message);
		startActivity(intent);
	}
}