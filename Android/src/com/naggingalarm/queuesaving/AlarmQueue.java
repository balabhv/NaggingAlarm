package com.naggingalarm.queuesaving;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;

import com.naggingalarm.MainActivity;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;

public class AlarmQueue implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6151786818935772873L;
	
	public Queue<Alarm> alarmQueue;
	public Queue<Alarm> mirrorQueue;
	private static Context mContext;
	
	public AlarmQueue(Context context) {
		mContext = context;
		this.alarmQueue = new LinkedList<Alarm>();
		this.mirrorQueue = new LinkedList<Alarm>();
	}
	
	public void addDataSetToQueue(Alarm ds) {
		alarmQueue.add(ds);
		mirrorQueue.add(ds);
		storeAndReRetrieveQueue(true);
	}

	// Saves Q_COUNT and alarmQueue into memory for later use
	protected void storeAndReRetrieveQueue(boolean rebuildMirrorQueue) {

		Queue<Alarm> backupQueue = new LinkedList<Alarm>();
		backupQueue.addAll(alarmQueue);
		
		// save Q_COUNT in SharedPrefs
		final SharedPreferences mPrefs = mContext.getSharedPreferences(
				MainActivity.PREFS_TAG, Context.MODE_PRIVATE);
		final SharedPreferences.Editor mPrefsEditor = mPrefs.edit();
		int Q_COUNT = backupQueue.size();
		int Q_COUNT_BACKUP = Q_COUNT;
		mPrefsEditor.putInt("Q_COUNT", Q_COUNT);
		mPrefsEditor.commit();
		
		// obtain storage directory and file for the alarmqueue
		File folder = new File(Environment.getExternalStorageDirectory()
				+ "/ExcaliburApps");
		
		if (!folder.exists()) {
			folder.mkdir();
		}

		File alarmQueueFile = new File(folder.getAbsolutePath() + "/"
				 + "NaggingAlarm_Alarms.ser");

		// writes the queue to a serializable file
		ObjectOutput out = null;
		try {
			out = new ObjectOutputStream(new FileOutputStream(alarmQueueFile));
			
			// serializes DataSets
			while (Q_COUNT > 0) {
				Alarm ds = backupQueue.remove();
				out.writeObject(ds);
				Q_COUNT--;
			}

			out.flush();
			
		} catch (IOException e) {
			e.printStackTrace();
		}

		// re-retrieve the queue
		alarmQueue = new LinkedList<Alarm>();
		Q_COUNT = Q_COUNT_BACKUP;
		
		try {
			// Deserialize the file as a whole
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(
					alarmQueueFile));
			
			// Deserialize the objects one by one
			for (int i = 0; i < Q_COUNT; i++) {
				Alarm dataSet = (Alarm) in.readObject();
				alarmQueue.add(dataSet);
			}
			in.close();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (rebuildMirrorQueue) {
			mirrorQueue.clear();
			mirrorQueue.addAll(alarmQueue);
		}
		
		
		try {
			if (out != null)
				out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// removes the dataset with the associated key: true if removed, false if not found
	public boolean removeItemWithKey(long keyVal) {
		LinkedList<Alarm> backup = new LinkedList<Alarm>();
		backup.addAll(alarmQueue);
		for (Alarm ds : backup) {
			if (ds.ALARM_ID == keyVal) {
				alarmQueue.remove(ds);
				mirrorQueue.remove(ds);
				storeAndReRetrieveQueue(true);
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Rebuilds the alarmQueue from the serializable file it
	 * is saved on.
	 * 
	 * @return 
	 * 			@true if the alarmQueue is rebuilt successfully
	 * 			@false if the rebuilding fails 
	 */
	public boolean buildQueueFromFile() {
		
		// reset the queues but save a backup
		Queue<Alarm> backupQueue = new LinkedList<Alarm>();
		backupQueue.addAll(alarmQueue);
		alarmQueue       = new LinkedList<Alarm>();
		mirrorQueue = new LinkedList<Alarm>();
		
		// get Q_COUNT from the SharedPrefs
		final SharedPreferences mPrefs = mContext.getSharedPreferences(
				MainActivity.PREFS_TAG, Context.MODE_PRIVATE);
		int Q_COUNT = mPrefs.getInt("Q_COUNT", -1);
		if (Q_COUNT == -1) {
			alarmQueue.addAll(backupQueue);
			mirrorQueue.addAll(backupQueue);
			return false;
		}

		// obtain storage directory and file for the alarmqueue
		File folder = new File(Environment.getExternalStorageDirectory()
				+ "/ExcaliburApps");
		if (!folder.exists()) {
			alarmQueue.addAll(backupQueue);
			mirrorQueue.addAll(backupQueue);
			return false;
		}
		
		File alarmQueueFile = new File(folder.getAbsolutePath() + "/"
				+ "NaggingAlarm_Alarms.ser");
		
		try {
			// Deserialize the file as a whole
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(
					alarmQueueFile));
			
			// Deserialize the objects one by one
			for (int i = 0; i < Q_COUNT; i++) {
				Alarm dataSet = (Alarm) in.readObject();
				alarmQueue.add(dataSet);
			}
			in.close();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			alarmQueue.addAll(backupQueue);
			mirrorQueue.addAll(backupQueue);
			return false;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			alarmQueue.addAll(backupQueue);
			mirrorQueue.addAll(backupQueue);
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			alarmQueue.addAll(backupQueue);
			mirrorQueue.addAll(backupQueue);
			return false;
		}
		
		mirrorQueue.addAll(alarmQueue);
		return true;
	}

}
