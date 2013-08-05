package com.naggingalarm;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class PrefsActivity extends PreferenceActivity {

	public void onCreate(Bundle b){
		super.onCreate(b);
		addPreferencesFromResource(R.xml.prefs);
	}
	
}
