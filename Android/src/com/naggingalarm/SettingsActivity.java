package com.naggingalarm;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.naggingalarm.dialogs.AddAlarmDialog;
import com.naggingalarm.queuesaving.Alarm;
import com.naggingalarm.queuesaving.AlarmQueue;

public class SettingsActivity extends Activity {

	public static AlarmQueue queue;

	private static LinearLayout scrollQueue;
	private Button add, record;

	public static final int NEW_ALARM_REQUESTED = 9000;
	public static final int VOICE_RECOGNITION_REQUEST_CODE = 9001;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);

		scrollQueue = (LinearLayout) findViewById(R.id.scrollqueue);

		queue = new AlarmQueue(this.getApplicationContext());

		boolean success = queue.buildQueueFromFile();

		if (!success) {
			Log.e("ERROR", "Error building queue from file");
		}

		Alarm test;
		Calendar t = new GregorianCalendar(TimeZone.getDefault());
		t.set(2012, 11, 21);
		ArrayList<Boolean> b = new ArrayList<Boolean>();
		b.add(true);
		b.add(false);
		b.add(false);
		b.add(true);
		b.add(true);
		b.add(false);
		b.add(false);
		test = new Alarm(t, b);
		queue.addDataSetToQueue(test);
		fillScrollQueue();

		add = (Button) findViewById(R.id.addButton);
		record = (Button) findViewById(R.id.recordNew);
		
		add.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivityForResult(new Intent(getApplicationContext(),
						AddAlarmDialog.class), NEW_ALARM_REQUESTED);

			}
		});
		
		record.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				
			}
		});

	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == NEW_ALARM_REQUESTED) {
			queue.buildQueueFromFile();
			fillScrollQueue();
		} else if (requestCode == VOICE_RECOGNITION_REQUEST_CODE
				&& resultCode == RESULT_OK) {
			// Fill the list view with the strings the recognizer thought it
			// could have heard
			ArrayList<String> matches = data
					.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);


			

			super.onActivityResult(requestCode, resultCode, data);
		}
	}

	private void fillScrollQueue() {

		if (scrollQueue.getChildCount() > 0)
			scrollQueue.removeAllViews();

		for (final Alarm ds : queue.mirrorQueue)
			addViewToScrollQueue(ds);

	}

	// Fills the text fields in the list element blocks
	private void makeBlock(View view, Alarm ds) {
		TextView tv = (TextView) view.findViewById(R.id.name);
		tv.setText(ds.getTime());

		TextView desc = (TextView) view.findViewById(R.id.description);
		desc.setText(ds.getDaysOfWeek());

		TextView on = (TextView) view.findViewById(R.id.enabled);
		on.setText(ds.getEnabled() ? "On" : "Off");
	}

	private String checkPrevious(String previous, LinearLayout scrollQueue,
			String ds) {

		LinearLayout space = new LinearLayout(getApplicationContext());
		space.setPadding(0, 10, 0, 10);

		if ((!previous.equals(ds)) && (!previous.equals("")))
			scrollQueue.addView(space);

		return ds;
	}
	
	/**
	 * Fire an intent to start the speech recognition activity.
	 */
	private void startVoiceRecognitionActivity() {
		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
				RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
				"Speak the sentence here");
		startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
	}

	private void addViewToScrollQueue(final Alarm ds) {

		String previous = "";
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);

		layoutParams.setMargins(5, 5, 5, 5);

		final View data = View.inflate(getApplicationContext(),
				R.layout.queueblock_data, null);

		makeBlock(data, ds);
		previous = checkPrevious(previous, scrollQueue, ds.getTime());

		scrollQueue.addView(data, layoutParams);
		data.setContentDescription("" + ds.ALARM_ID);

		data.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				ds.setEnabled(!ds.enabled());
				scrollQueue.removeView(data);
				addViewToScrollQueue(ds);
				
			}

		});

		data.setOnLongClickListener(new OnLongClickListener() {

			public boolean onLongClick(View v) {
				Intent i = new Intent(getApplicationContext(),
						AddAlarmDialog.class);
				i.putExtra("DATA", ds);
				i.putExtra("EDITING", true);
				startActivityForResult(i, NEW_ALARM_REQUESTED);
				return false;
			}

		});

	}

}
