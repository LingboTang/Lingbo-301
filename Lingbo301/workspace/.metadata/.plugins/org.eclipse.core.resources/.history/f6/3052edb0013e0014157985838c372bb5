package ca.ualberta.cs.lonelytwitter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class LonelyTwitterActivity extends Activity {

	private static final String FILENAME = "file.sav";
	
	private EditText bodyText;
	
	private ArrayList<Tweet> tweets;
	
	private ArrayAdapter<Tweet> tweetsViewAdapter;
	
	private ListView oldTweetsList;

	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.main);
		bodyText = (EditText) findViewById(R.id.body);
		oldTweetsList = (ListView) findViewById(R.id.oldTweetsList);
	}

	@Override
	protected void onStart() {
		super.onStart();
		
		tweets = loadTweets();
		tweetsViewAdapter = new ArrayAdapter<Tweet>(this, R.layout.list_item, tweets);
		oldTweetsList.setAdapter(tweetsViewAdapter);
	}
	
	public void save(View v) {
		
		String text = bodyText.getText().toString();
		Tweet tweet = new Tweet(new Date(), text);
		
		tweets.add(tweet);
		tweetsViewAdapter.notifyDataSetChanged();
		
		bodyText.setText("");
		saveTweets(tweets);
	}
	
	public void clear(View v) {
		
		tweets.clear();
		tweetsViewAdapter.notifyDataSetChanged();
		saveTweets(tweets);
	}
	
	public ArrayList<Tweet> loadTweets() {
		ArrayList<Tweet> lts = new ArrayList<Tweet>();

		try {
			FileInputStream fis = new FileInputStream(FILENAME);
			ObjectInputStream ois = new ObjectInputStream(fis);

			lts = (ArrayList<Tweet>) ois.readObject();

		} catch (Exception e) {
			Log.i("LonelyTwitter", "Error casting");
			e.printStackTrace();
		} 

		return lts;
	}
	
	public void saveTweets(List<Tweet> lts) {
		try {
			FileOutputStream fos = new FileOutputStream(FILENAME);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(lts);
			fos.close();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}