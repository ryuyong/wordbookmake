package com.word.wordbookmake;

import android.util.Log;

public class LogUtil {
	private static boolean isDevMode = true;
	//디버그용 
	public static void d(String tag, String msg) {
		if (isDevMode) {
			Log.d(tag, msg);
		}
	}
	//운영용
	public static void i(String tag, String msg) {
		if (isDevMode) {
			Log.i(tag, msg);
		}
	}
}
