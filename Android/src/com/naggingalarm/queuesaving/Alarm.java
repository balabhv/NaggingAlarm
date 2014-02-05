package com.naggingalarm.queuesaving;

import java.io.Serializable;
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
	
	public static int ID_COUNT = 0;
	
	public Alarm(Calendar mDate, ArrayList<Boolean> days, boolean wakeOnVoice, boolean enabled) {
		
		date = mDate;
		
		ALARM_ID = ID_COUNT++;
		
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
	
	public int getCurrentHour() {
		return date.get(Calendar.HOUR_OF_DAY);
	}
	
	public int getCurrentMinute() {
		return date.get(Calendar.MINUTE);
	}
	
	public boolean monday() {
		return daysToRepeat.contains(MONDAY);
	}
	public boolean tuesday() {
		return daysToRepeat.contains(TUESDAY);
	}
	public boolean wednesday() {
		return daysToRepeat.contains(WEDNESDAY);
	}
	public boolean thursday() {
		return daysToRepeat.contains(THURSDAY);
	}
	public boolean friday() {
		return daysToRepeat.contains(FRIDAY);
	}
	public boolean saturday() {
		return daysToRepeat.contains(SATURDAY);
	}
	public boolean sunday() {
		return daysToRepeat.contains(SUNDAY);
	}
	public boolean voice() {
		return useVoiceWake;
	}
	public boolean enabled() {
		return enabled;
	}
	

	public String getTime() {
		String hour = ":";
		if (date.get(Calendar.HOUR) < 10) {
			hour = "0" + date.get(Calendar.HOUR) + hour;
		} else {
			hour = date.get(Calendar.HOUR) + hour;
		}
		String min = "";
		if (date.get(Calendar.MINUTE) < 10) {
			min = "0" + date.get(Calendar.MINUTE);
		} else {
			min = date.get(Calendar.MINUTE) + "";
		}
		String suffix = " ";
		if (date.get(Calendar.AM_PM) == Calendar.AM) {
			suffix += "AM";
		} else {
			suffix += "PM";
		}
		
		return hour + min + suffix;
	}

	public boolean getEnabled() {
		return enabled;
	}

	public String getDaysOfWeek() {
		String s = "";
		
		for (int i = 0 ; i < daysToRepeat.size() ; i++) {
			switch(daysToRepeat.get(i)) {
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
				break;
			}
		}
		
		if (s.charAt(s.length()-2) == ',') {
			s = s.substring(0, s.length()-2);
		}
		
		return s;
	}

	public void setEnabled(boolean b) {
		enabled = b;
		
	}
		
	
	

}
