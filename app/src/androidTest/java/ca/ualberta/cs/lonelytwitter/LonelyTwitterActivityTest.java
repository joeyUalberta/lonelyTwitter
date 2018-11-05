package ca.ualberta.cs.lonelytwitter;

import android.app.Activity;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.robotium.solo.Solo;

import junit.framework.TestCase;

/**
 * Created by wz on 14/09/15.
 */
public class LonelyTwitterActivityTest extends ActivityInstrumentationTestCase2<LonelyTwitterActivity> {

    private Solo solo;
    public LonelyTwitterActivityTest() {
        super(ca.ualberta.cs.lonelytwitter.LonelyTwitterActivity.class);
    }

    @Override
    public void setUp(){
        solo=new Solo(getInstrumentation(),getActivity());
    }

    @Override
    public void tearDown(){
        solo.finishOpenedActivities();
    }

    public void testTweet(){
        solo.assertCurrentActivity("wrong activity",LonelyTwitterActivity.class);
    }
    public void testEqual(){
        assertEquals("Not equal",5,5);
    }
    public void testAddTweet(){
        solo.enterText((EditText)solo.getView(R.id.body),"new thing");
        solo.clickOnButton("Save");
        solo.clearEditText((EditText)solo.getView(R.id.body));
        assertTrue(solo.waitForText("new thing"));
        TextView temp = (TextView)solo.getView(R.id.body2);
        String s = temp.getText().toString();
        assertEquals(s,"new thing");
    }
}