package com.naggingalarm.dialogs;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TimePicker;

import com.naggingalarm.R;
import com.naggingalarm.queuesaving.Alarm;
import com.naggingalarm.queuesaving.AlarmQueue;

public class AddAlarmDialog extends Activity {
	
	boolean mon,tue,wed,thur,fri,sat,sun;
	boolean voice, enabled;
	
	private TimePicker picker;
	private CheckBox m,t,w,th,f,s,su,v,e;
	private Button ok;
	private Alarm toBeAdded;
	private ArrayList<Boolean> days;
	
	private boolean editing = false;
	
	private int hour, minute;
	private AlarmQueue aq;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_alarm_dialog);
		
		aq = new AlarmQueue(getApplicationContext());
		aq.buildQueueFromFile();
		
		Bundle b = getIntent().getExtras();
		
		editing = b.getBoolean("EDITING", false);
		toBeAdded = (Alarm) b.getSerializable("DATA");
		
		picker = (TimePicker) findViewById(R.id.timePicker1);
		m = (CheckBox) findViewById(R.id.cbMonday);
		t = (CheckBox) findViewById(R.id.cbTuesday);
		w = (CheckBox) findViewById(R.id.cbWednesday);
		th = (CheckBox) findViewById(R.id.cbThursday);
		f = (CheckBox) findViewById(R.id.cbFriday);
		s = (CheckBox) findViewById(R.id.cbSaturday);
		su = (CheckBox) findViewById(R.id.cbSunday);
		v = (CheckBox) findViewById(R.id.useVoice);
		e = (CheckBox) findViewById(R.id.cbEnable);
		
		ok = (Button) findViewById(R.id.addButtonOK);
		
		if (editing) {
			setTitle("Edit Alarm");
			aq.removeItemWithKey(toBeAdded.ALARM_ID);
			picker.setCurrentHour(toBeAdded.getCurrentHour());
			picker.setCurrentMinute(toBeAdded.getCurrentMinute());
			m.setChecked(toBeAdded.monday());
			t.setChecked(toBeAdded.tuesday());
			w.setChecked(toBeAdded.wednesday());
			th.setChecked(toBeAdded.thursday());
			f.setChecked(toBeAdded.friday());
			s.setChecked(toBeAdded.saturday());
			su.setChecked(toBeAdded.sunday());
			v.setChecked(toBeAdded.voice());
			e.setChecked(toBeAdded.enabled());
			ok.setText("Done");
		}
		
		ok.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				hour = picker.getCurrentHour().intValue();
				minute = picker.getCurrentMinute().intValue();
				Calendar mCal = new GregorianCalendar();
				mCal.set(Calendar.HOUR_OF_DAY, hour);
				mCal.set(Calendar.MINUTE, minute);
				setBools();
				
				toBeAdded = new Alarm(mCal, days, voice, enabled);
				aq.addDataSetToQueue(toBeAdded);
				setResult(RESULT_OK);
				finish();
				
			}
		});
		
	}
	
	private void setBools() {
		days = new ArrayList<Boolean>();
		mon = m.isChecked();
		tue = t.isChecked();
		wed = w.isChecked();
		thur = th.isChecked();
		fri = f.isChecked();
		sat = s.isChecked();
		sun = su.isChecked();
		days.add(sun);
		days.add(mon);
		days.add(tue);
		days.add(wed);
		days.add(thur);
		days.add(fri);
		days.add(sat);
		
		voice = v.isChecked();
		enabled = e.isChecked();
	}

}
