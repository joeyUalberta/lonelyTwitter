package ca.ualberta.cs.lonelytwitter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class EditTweetActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tweet);
        Intent intent =getIntent();
        String msg = intent.getStringExtra(LonelyTwitterActivity.SEND_MSG);
        TextView b = (TextView) findViewById(R.id.body2);
        b.setText(msg);
    }
}
