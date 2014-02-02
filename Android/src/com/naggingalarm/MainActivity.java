package com.naggingalarm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextClock;

public class MainActivity extends Activity {

	public static final String PREFS_TAG = "SHARED_PREFS";

	public static WordDictionary dict;
	
	TextClock clock;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		dict = WordDictionary.getInstance(getApplicationContext());
		
		clock = (TextClock) findViewById(R.id.textClock1);
		clock.addTextChangedListener(new TextWatcher(){

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				
			}
			
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.wordsviewopen: {
			startActivity(new Intent(getApplicationContext(),
					WordActivity.class));
		}
			return true;
		case R.id.action_settings: {
			startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
		}
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
