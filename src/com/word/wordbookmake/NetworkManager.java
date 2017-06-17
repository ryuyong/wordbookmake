package com.word.wordbookmake; 

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.DetailedState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

public class NetworkManager {
    
    /**
     * Check network connectivity (wi-fi)
     * @param context
     * @return connected state
     */
    public static boolean isWifiConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if(activeNetworkInfo != null && activeNetworkInfo.isAvailable() && activeNetworkInfo.isConnected())
        {
        	//와이파이 감도로직 추가
        	WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        	WifiInfo wInfo = wm.getConnectionInfo();
        	if(wInfo != null){
        	      // 연결상태 확인
        	      DetailedState ni_ds = WifiInfo.getDetailedStateOf(wInfo.getSupplicantState());
        	      if( (wInfo.getIpAddress() >0 && wInfo.getSSID() != null && wInfo.getSupplicantState().toString().equals("COMPLETED")) 
        	        && (ni_ds == DetailedState.CONNECTED || ni_ds == DetailedState.OBTAINING_IPADDR)
        	      ){
        	       // RSSI 는 -100에 가까울수록 안좋고 0에 가까울수록 좋음
        	       Log.d("wifi rssi",wInfo.getRssi()+"");
        	       if(wInfo.getRssi() > -75)
        	        return true;
        	      }
        	}
        }
        return false;
    }
    
    /**
     * Check network connectivity (mobile)
     * @param context
     * @return connected state
     */
    public static boolean isMobileConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if(networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected()) {
        	return true;
        }
        //if(networkInfo != null) {
        //	return true;
        //}
        return false;
    }
    
    /**
     * Check network connectivity (mobile, wi-fi)
     * @return connected state
     */
    public static boolean isOnline(Context context) { // network 연결 상태 확인
        boolean connected = false;
        
        if(isWifiConnected(context)) 
            return true;
        if(isMobileConnected(context))
            return true;
        
        return connected;
    }
}