package com.word.wordbookmake;

import com.word.wordbookmake.NetworkManager;

import com.word.wordbookmake.HTML5WebView;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.Toast;

public class WordHelp extends Activity  {
	HTML5WebViewAll webView;
	ProgressBar progressBar;
	public boolean backend = false;
	
	private boolean checknetwork = true;
    private long backKeyPressedTime = 0;
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
		getWindow().requestFeature(Window.FEATURE_PROGRESS); // 프로그레스
        super.onCreate(savedInstanceState);
        
        webView = new HTML5WebViewAll(this);
        setContentView(webView.getLayout());
        progressBar = (ProgressBar) webView.getLayout().findViewById(R.id.pro1);
        
        
        if (savedInstanceState != null) {
        	webView.restoreState(savedInstanceState);
        } else {
        	webView.loadUrl("http://m.blog.naver.com/dolly11/220035223212");
        	//webView.loadUrl("http://endic.naver.com/small_search.nhn?query=student");
        }
    }
	
	private void changePage() {
		webView.loadUrl("http://m.blog.naver.com/dolly11/220035223212");
		//webView.loadUrl("http://endic.naver.com/small_search.nhn?query=student");
	}
	
	
    /**
     * 현재 접속하고 있는 네트워크 체크
     * @param threeg_state
     */
    public boolean checkNetwork()
	{
    	 boolean connected = false;
         if(NetworkManager.isWifiConnected(getApplicationContext())) 
             return true;
         if(NetworkManager.isMobileConnected(getApplicationContext()))
             return true;
         return connected;
	}
    
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			checknetwork = checkNetwork();
	        //checkNetwork("Y");
	        if(!checknetwork) {
				if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
					backKeyPressedTime = System.currentTimeMillis();
					Toast.makeText(WordHelp.this, "현재 네트워크 상태가 불안정합니다 \n뒤로 버튼을 한번 더  누르시면 메인화면으로 이동합니다..", Toast.LENGTH_SHORT).show();
					return false;
				}
				if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
					systemExit();
					return false;
				}
	        }
	        
	        webView.stopLoading();
	        
			if(webView.canGoBack()) {
				LogUtil.d("webView.canGoBack()","===================webView.canGoBack()1");
				webView.goBack();
				return false;
			} else {
				Intent i = new Intent(WordHelp.this,MainActivity.class);
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
				return false;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	
	public void systemExit() {
		Intent i = new Intent(WordHelp.this,MainActivity.class);
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(i);
	}
	
	@Override    
    public void onWindowFocusChanged(boolean hasFocus) {         
    	super.onWindowFocusChanged(hasFocus);
    }

	@Override
	protected void onStart() {
		LogUtil.d("onStart","=======================================");
		LogUtil.d("onStart","onStart");
		LogUtil.d("onStart","=======================================");
		super.onStart();
		
	}

	@Override
	protected void onPause() {
		LogUtil.d("onPause","=======================================");
		LogUtil.d("onPause","onPause");
		LogUtil.d("onPause","=======================================");
		super.onPause();
	}

	@Override
	protected void onRestart() {
		LogUtil.d("onRestart","=======================================");
		LogUtil.d("onRestart","onRestart");
		LogUtil.d("onRestart","=======================================");
		super.onRestart();
	}

	@Override
	protected void onResume() {
		changePage();
		LogUtil.d("onResume","=======================================");
		LogUtil.d("onResume","onResume");
		LogUtil.d("onResume","=======================================");
		// TODO Auto-generated method stub
		super.onResume();
		//super.onRestart();
		
	}
	
    @Override
    public void onSaveInstanceState(Bundle outState) {
    	super.onSaveInstanceState(outState);
    	webView.saveState(outState);
    }

	@Override
	protected void onStop() {
    	super.onStop();
    	webView.stopLoading();
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		//Toast.makeText(this, "onConfigurationChanged", Toast.LENGTH_SHORT).show();
		super.onConfigurationChanged(newConfig);
	};
}
