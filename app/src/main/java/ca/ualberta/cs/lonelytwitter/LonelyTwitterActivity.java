package ca.ualberta.cs.lonelytwitter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
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
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.view.View;
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
	private ArrayList<Tweet> tweets = new ArrayList<Tweet>();
	private ArrayAdapter<Tweet> adapter;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		bodyText = (EditText) findViewById(R.id.body);
		Button saveButton = (Button) findViewById(R.id.save);
		oldTweetsList = (ListView) findViewById(R.id.oldTweetsList);
		Button clearButton = (Button) findViewById(R.id.clear);
		saveButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				String text = bodyText.getText().toString();
				ImportantTweet newTweet=new ImportantTweet(text);
				tweets.add(newTweet);
				adapter.notifyDataSetChanged();
				saveInFile();

			}
		});
		clearButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				tweets.clear();
				adapter.notifyDataSetChanged();
				saveInFile();	
			}
		});
	}

	/**
	 * Connect the arrayadapter to the listview, and load the file
	 */
	@Override
	protected void onStart() {
		super.onStart();
		loadFromFile();
		adapter = new ArrayAdapter<Tweet>(this,
				R.layout.list_item, tweets);
		oldTweetsList.setAdapter(adapter);
	}

	/**
	 * Load the tweets from the file
	 */
	private void loadFromFile() {
		Log.d("joey","start loading");
		try {
			FileInputStream fis = openFileInput(FILENAME);
			InputStreamReader isr= new InputStreamReader(fis);
			BufferedReader reader = new BufferedReader(isr);
			Gson gson=new Gson();
			Type typeListTweets=new TypeToken<ArrayList<ImportantTweet>>(){}.getType();
			Log.d("joey","creatd Type");
			tweets = gson.fromJson(reader,typeListTweets);
			Log.d("joey","Type fail");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			Log.d("joey","File not found");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.d("joey","File not found");
			e.printStackTrace();
		}
		Log.d("joey","done loading");
	}

	/**
	 * store the new tweets to the file
	 */
	private void saveInFile() {
		try {
			FileOutputStream fos= openFileOutput(FILENAME,0);
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			Gson gson =new Gson();
			gson.toJson(tweets,osw);
			osw.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}