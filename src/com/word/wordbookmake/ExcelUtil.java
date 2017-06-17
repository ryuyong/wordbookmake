package com.word.wordbookmake;

import java.io.File;

import android.os.Environment;


public class ExcelUtil {
	public static String[] getWordTitles() {
		//ArrayList<String> returnlist = new ArrayList<String>();
		File sdcard = Environment.getExternalStorageDirectory();
		File word = new File(sdcard, "wordbookmake/단어");
		File[] wordlist = word.listFiles();
		if(wordlist != null) {
			String[] returnlist = new String[wordlist.length];
			if(wordlist != null && wordlist.length > 0) {
	        	for(int i=0;i<wordlist.length;i++) {
	        		String name = wordlist[i].getName();
	        		int pointindex = name.lastIndexOf(".");
	        		name = name.substring(0,pointindex);
	        		returnlist[i] = name;
	        	}
	        	
	        }
			return returnlist;
		} else {
			return null;
		}
		
	}
	
	public static String[] getWordKeys() {
		//ArrayList<String> returnlist = new ArrayList<String>();
		File sdcard = Environment.getExternalStorageDirectory();
		File word = new File(sdcard, "wordbookmake/단어");
		File[] wordlist = word.listFiles();
		if(wordlist != null) {
			String[] returnlist = new String[wordlist.length];
			if(wordlist != null && wordlist.length > 0) {
	        	for(int i=0;i<wordlist.length;i++) {
	        		String name = wordlist[i].getName();
	        		int pointindex = name.lastIndexOf(".");
	        		name = name.substring(0,pointindex);
	        		returnlist[i] = "단어/"+name;
	        	}
	        	
	        }
			return returnlist;
		} else {
			return null;
		}
		
	}
	
	
	public static String[] getPhraseTitles() {
		//ArrayList<String> returnlist = new ArrayList<String>();
		File sdcard = Environment.getExternalStorageDirectory();
		File phrase = new File(sdcard, "wordbookmake/숙어");
		File[] phraselist = phrase.listFiles();
		if(phraselist != null) {
			String[] returnlist = new String[phraselist.length];
			if(phraselist != null && phraselist.length > 0) {
	        	for(int i=0;i<phraselist.length;i++) {
	        		String name = phraselist[i].getName();
	        		int pointindex = name.lastIndexOf(".");
	        		name = name.substring(0,pointindex);
	        		returnlist[i] = name;
	        	}
	        	
	        }
			return returnlist;
		} else {
			return null;
		}
		
	}
	
	
	public static String[] getPhraseKeys() {
		//ArrayList<String> returnlist = new ArrayList<String>();
		File sdcard = Environment.getExternalStorageDirectory();
		File phrase = new File(sdcard, "wordbookmake/숙어");
		File[] phraselist = phrase.listFiles();
		if(phraselist != null) {
			String[] returnlist = new String[phraselist.length];
			if(phraselist != null && phraselist.length > 0) {
	        	for(int i=0;i<phraselist.length;i++) {
	        		String name = phraselist[i].getName();
	        		int pointindex = name.lastIndexOf(".");
	        		name = name.substring(0,pointindex);
	        		returnlist[i] = "숙어/"+name;
	        	}
	        	
	        }
			return returnlist;
		} else {
			return null;
		}
		
	}
}
