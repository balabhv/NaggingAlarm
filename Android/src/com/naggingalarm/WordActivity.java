package com.naggingalarm;

import java.util.ArrayList;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class WordActivity extends Activity {

	TextView text;
	WordDictionary dict;
	Button speak;
	String words;

	public static final int VOICE_RECOGNITION_REQUEST_CODE = 9000;

	public void onCreate(Bundle b) {
		super.onCreate(b);
		setContentView(R.layout.wordview);

		text = (TextView) findViewById(R.id.textView1);
		dict = WordDictionary.getInstance(getApplicationContext());
		speak = (Button) findViewById(R.id.button1);

		speak.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startVoiceRecognitionActivity();
			}
		});

		words = WordDictionary.getRandomSentence(dict);

		text.setText(words);

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

	/**
	 * Handle the results from the recognition activity.
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == VOICE_RECOGNITION_REQUEST_CODE
				&& resultCode == RESULT_OK) {
			// Fill the list view with the strings the recognizer thought it
			// could have heard
			ArrayList<String> matches = data
					.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);


			if (matches.contains(words.toLowerCase(Locale.ENGLISH))){
				finish();
			}

			super.onActivityResult(requestCode, resultCode, data);
		}
	}

}
