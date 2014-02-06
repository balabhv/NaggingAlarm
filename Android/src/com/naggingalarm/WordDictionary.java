package com.naggingalarm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Context;

public class WordDictionary {

	private static final int LENGTH_OF_PHRASES = 10;
	List<String> wordList;
	static WordDictionary instance;

	private static WordDictionary getDictionaryFromAssets(Context mContext) {
		WordDictionary dict = new WordDictionary();
		dict.wordList = new ArrayList<String>();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(mContext.getAssets().open(
					"allwords.txt"))); // throwing a FileNotFoundException?
			String word;
			while ((word = br.readLine()) != null)
				dict.wordList.add(word); // break txt file into different words,
											// add to wordList
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close(); // stop reading
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		String[] words = new String[dict.wordList.size()];
		dict.wordList.toArray(words); // make array of wordList
		return dict;
	}
	
	public static WordDictionary getInstance(Context c){
		if (instance != null){
			return instance;
		}
		instance = getDictionaryFromAssets(c);
		return instance;
	}
	
	public static String getRandomSentence(WordDictionary dict) {
		String s = "";
		for (int i = 0 ; i < LENGTH_OF_PHRASES ; i++) {
			s += getRandomWord(dict) + " ";
		}
		
		return s;
	}
	
	public static String getRandomWord(WordDictionary dict) {
		List<String> tmpList = dict.wordList;
		Collections.shuffle(tmpList);
		return tmpList.get(0);
		
	}
}
