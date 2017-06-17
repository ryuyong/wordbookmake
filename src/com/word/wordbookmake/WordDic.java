package com.word.wordbookmake;

import com.word.wordbookmake.NetworkManager;

import com.word.wordbookmake.HTML5WebView;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class WordDic extends Activity  {
	HTML5WebView webView;
	ProgressBar progressBar;
	
	String diselect;
	Button customscreen_button01;
	Button customscreen_button02;
	Button customscreen_button03;
	Button customscreen_button04;
	
	public boolean backend = false;
	
	private boolean checknetwork = true;
    private long backKeyPressedTime = 0;
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
		getWindow().requestFeature(Window.FEATURE_PROGRESS); // 프로그레스
        super.onCreate(savedInstanceState);
        
		Bundle extras=getIntent().getExtras();
		
		if (extras!=null) {
			diselect=extras.getString("diselect");
		}
		
		
		LogUtil.d("WordDic","11=====================================================");
    	LogUtil.d("WordDic",diselect+"11=====================================================");
    	LogUtil.d("WordDic","11=====================================================");
    	
        webView = new HTML5WebView(this);
        setContentView(webView.getLayout());
        progressBar = (ProgressBar) webView.getLayout().findViewById(R.id.pro);
        
        customscreen_button01 = (Button)findViewById(R.id.customscreen_button01);
        customscreen_button02 = (Button)findViewById(R.id.customscreen_button02);
        customscreen_button03 = (Button)findViewById(R.id.customscreen_button03);
        customscreen_button04 = (Button)findViewById(R.id.customscreen_button04);
        customscreen_button01.setOnClickListener(enbuttonListner);
        customscreen_button02.setOnClickListener(jabuttonListner);
        customscreen_button03.setOnClickListener(chbuttonListner);
        customscreen_button04.setOnClickListener(mainbuttonListner);
		
        if (savedInstanceState != null) {
        	webView.restoreState(savedInstanceState);
        } else {
        	if(diselect != null && diselect.equals("viewja")) {
        		webView.loadUrl("http://m.jpdic.naver.com/");
        		LogUtil.d("WordDic","http://m.jpdic.naver.com/=====================================================");
        	} else if(diselect != null && diselect.equals("viewch")) {
        		webView.loadUrl("http://m.cndic.naver.com/");
        		LogUtil.d("WordDic","http://m.cndic.naver.com/=====================================================");
        	} else {
        		webView.loadUrl("http://m.endic.naver.com/");
        		LogUtil.d("WordDic","http://m.endic.naver.com/=====================================================");
        	}
        	
        }
    }
	
	
	public View.OnClickListener enbuttonListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
    		LogUtil.d("WordDic","=====================================================");
        	LogUtil.d("WordDic","enbuttonListner=====================================================");
        	LogUtil.d("WordDic","=====================================================");
        	
			Intent i = new Intent(WordDic.this,WordDic.class);
			//i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			i.putExtra("diselect", "viewen");
			startActivity(i);
			
			diselect = "viewen";
        }
    };
    
    public View.OnClickListener jabuttonListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
    		LogUtil.d("WordDic","=====================================================");
        	LogUtil.d("WordDic","jabuttonListner=====================================================");
        	LogUtil.d("WordDic","=====================================================");
        	
			Intent i1 = new Intent(WordDic.this,WordDic.class);
			//i1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			i1.putExtra("diselect", "viewja");
			startActivity(i1);
			
			diselect = "viewja";
        }
    };
    
    
    public View.OnClickListener chbuttonListner = new View.OnClickListener() {
        
        @Override
        public void onClick(View v) {
    		LogUtil.d("WordDic","=====================================================");
        	LogUtil.d("WordDic","chbuttonListner=====================================================");
        	LogUtil.d("WordDic","=====================================================");
        	
			Intent i2 = new Intent(WordDic.this,WordDic.class);
			//i2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			i2.putExtra("diselect", "viewch");
			startActivity(i2);
			
			diselect = "viewch";
        }
    };
    
    public View.OnClickListener mainbuttonListner = new View.OnClickListener() {
        
        @Override
        public void onClick(View v) {
    		LogUtil.d("WordDic","=====================================================");
        	LogUtil.d("WordDic","mainbuttonListner=====================================================");
        	LogUtil.d("WordDic","=====================================================");
        	systemExit();
        }
    };
    
	private void changePage() {
    	if(diselect != null && diselect.equals("viewja")) {
    		webView.loadUrl("http://m.jpdic.naver.com/");
    		LogUtil.d("WordDic","http://m.jpdic.naver.com/=====================================================");
    	} else if(diselect != null && diselect.equals("viewch")) {
    		webView.loadUrl("http://m.cndic.naver.com/");
    		LogUtil.d("WordDic","http://m.cndic.naver.com/=====================================================");
    	} else {
    		webView.loadUrl("http://m.endic.naver.com/");
    		LogUtil.d("WordDic","http://m.endic.naver.com/=====================================================");
    	}
    	/*
    	if(diselect != null && diselect.equals("viewja")) {
	    	LinearLayout bContentView = (LinearLayout) findViewById(R.id.button_content);
	    	
	    	LinearLayout.LayoutParams sarams = new LinearLayout.LayoutParams
	                (ViewGroup.LayoutParams.MATCH_PARENT, 
	                		ViewGroup.LayoutParams.WRAP_CONTENT);
	    	sarams.height = 0;
	
	    	bContentView.setLayoutParams(sarams);
    	} else {
    		LinearLayout bContentView = (LinearLayout) findViewById(R.id.button_content);
	    	
	    	LinearLayout.LayoutParams sarams = new LinearLayout.LayoutParams
	                (ViewGroup.LayoutParams.MATCH_PARENT, 
	                		ViewGroup.LayoutParams.WRAP_CONTENT);
	    	sarams.height = 70;
	
	    	bContentView.setLayoutParams(sarams);
    	}*/
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
    
    
	public void systemExit() {
		Intent i = new Intent(WordDic.this,MainActivity.class);
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(i);
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			
			checknetwork = checkNetwork();
	        //checkNetwork("Y");
	        if(!checknetwork) {
				if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
					backKeyPressedTime = System.currentTimeMillis();
					Toast.makeText(WordDic.this, "현재 네트워크 상태가 불안정합니다 \n뒤로 버튼을 한번 더  누르시면 메인화면으로 이동합니다..", Toast.LENGTH_SHORT).show();
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
				Intent i = new Intent(WordDic.this,MainActivity.class);
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
				return false;
			}
		}
		return super.onKeyDown(keyCode, event);
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
