package com.naggingalarm.queuesaving;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Alarm implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4281133664603707830L;
	// instance variables
	public int ALARM_ID;
	private Calendar date;
	private ArrayList<Integer> daysToRepeat;
	private boolean useVoiceWake;
	private boolean enabled;
	
	// constants
	public static final int MONDAY = 1;
	public static final int TUESDAY = 2;
	public static final int WEDNESDAY = 3;
	public static final int THURSDAY = 4;
	public static final int FRIDAY = 5;
	public static final int SATURDAY = 6;
	public static final int SUNDAY = 0;
	
	public static final String id_key = "ALARM_ID";
	public static final String date_key = "TIMESTAMP";
	public static final String repeat_key = "REPEAT";
	public static final String voice_key = "VOICE_WAKE";
	public static final String on_key = "ENABLED";
	
	public Alarm(Calendar mDate, ArrayList<Boolean> days, boolean wakeOnVoice, boolean enabled) {
		
		date = mDate;
		
		daysToRepeat = new ArrayList<Integer>();
		
		for (int i = 0 ; i < 7 ; ++i) {
			if (days.get(i))
				daysToRepeat.add(i);
		}
		
		useVoiceWake = wakeOnVoice;
		this.enabled = enabled;
			
	}
	
	public Alarm(Calendar mDate, ArrayList<Boolean> days, boolean wakeOnVoice) {
		this(mDate, days, wakeOnVoice, true);
	}
	
	public Alarm(Calendar mDate, ArrayList<Boolean> days) {
		this(mDate, days, false);
	}

	public String getTime() {
		String hour = ":";
		if (date.get(Calendar.HOUR_OF_DAY) < 10) {
			hour = "0" + date.get(Calendar.HOUR_OF_DAY) + hour;
		} else {
			hour = date.get(Calendar.HOUR_OF_DAY) + hour;
		}
		String min = ":";
		if (date.get(Calendar.MINUTE) < 10) {
			min = "0" + date.get(Calendar.MINUTE) + min;
		} else {
			min = date.get(Calendar.MINUTE) + min;
		}
		String second = ".";
		if (date.get(Calendar.SECOND) < 10) {
			second = "0" + date.get(Calendar.SECOND) + second;
		} else {
			second = date.get(Calendar.SECOND) + second;
		}
		String milli;
		if (date.get(Calendar.MILLISECOND) < 10) {
			milli = "0" + date.get(Calendar.MILLISECOND);
		} else {
			milli = date.get(Calendar.MILLISECOND) + "";
		}
		
		return hour + min + second + milli;
	}

	public boolean getEnabled() {
		// TODO Auto-generated method stub
		return enabled;
	}

	public String getDaysOfWeek() {
		String s = "";
		
		for (int i = 0 ; i < 7 ; i++) {
			switch(i) {
			case 0:
				s += "SUNDAY, ";
				break;
			case 1:
				s += "MONDAY, ";
				break;
			case 2:
				s += "TUESDAY, ";
				break;
			case 3:
				s += "WEDNESDAY, ";
				break;
			case 4:
				s += "THURSDAY, ";
				break;
			case 5:
				s += "FRIDAY, ";
				break;
			case 6:
				s += "SATURDAY";
			}
		}
		
		return s;
	}
		
	
	

}
