package com.word.wordbookmake;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.provider.Browser;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class HTML5WebViewAll extends WebView {
	private Context 							mContext;
	private MyWebChromeClient					mWebChromeClient;
	private View								mCustomView;
	private FrameLayout							mCustomViewContainer;
	private WebChromeClient.CustomViewCallback 	mCustomViewCallback;
	
	private FrameLayout							mContentView;
	private FrameLayout							mBrowserFrameLayout;
	private FrameLayout							mLayout;
	
	ProgressBar progressBar;
	
    static final String LOGTAG = "HTML5WebView";
	    
	private void init(Context context) {
		mContext = context;		
		Activity a = (Activity) mContext;
		
		mLayout = new FrameLayout(context);
		
		mBrowserFrameLayout = (FrameLayout) LayoutInflater.from(a).inflate(R.layout.custom_screen1, null);
		

        
        
		mContentView = (FrameLayout) mBrowserFrameLayout.findViewById(R.id.main_content1);
		mCustomViewContainer = (FrameLayout) mBrowserFrameLayout.findViewById(R.id.fullscreen_custom_content1);
        
		mLayout.addView(mBrowserFrameLayout, COVER_SCREEN_PARAMS);
		mWebChromeClient = new MyWebChromeClient();
	    setWebChromeClient(mWebChromeClient);
	    
	    setWebViewClient(new MyWebViewClient());
	       
	    // Configure the webview
	    WebSettings s = getSettings();
	    s.setBuiltInZoomControls(true);
	    s.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);

	    s.setUseWideViewPort(true);
	    s.setLoadWithOverviewMode(true);
	    s.setSavePassword(true);
	    s.setSaveFormData(true);
	    s.setJavaScriptEnabled(true);
	    
	    // enable navigator.geolocation 
	    //s.setGeolocationEnabled(true);
	    //s.setGeolocationDatabasePath("/data/data/org.itri.html5webview/databases/");
	    
	    // enable Web Storage: localStorage, sessionStorage
	    s.setDomStorageEnabled(true);
	    //setVerticalScrollBarEnabled(true);
	    //setHorizontalScrollBarEnabled(false);
	    setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
	    setScrollbarFadingEnabled(true);
	    
	    
	    clearHistory();
	    clearFormData();
	    clearCache(true);
	    s.setCacheMode(WebSettings.LOAD_NO_CACHE);
	    
		//progressBar = new ProgressBar(mContext);
        //FrameLayout.LayoutParams params=new FrameLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,40,Gravity.TOP);
        //params.height = 40;
        //progressBar.setLayoutParams(params);
        //progressBar.setMax(100);
        //progressBar.setVisibility(View.INVISIBLE);      
        //mContentView.addView(progressBar);
		//progressBar = (ProgressBar) mContentView.findViewById(R.id.pro);
		//mContentView.addView(progressBar);
	    mContentView.addView(this);
	    //mContentView.addView(progressBar);
	}

	public HTML5WebViewAll(Context context) {
		super(context);
		init(context);
	}

	public HTML5WebViewAll(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public HTML5WebViewAll(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}
	
	public FrameLayout getLayout() {
		return mLayout;
	}
	
    public boolean inCustomView() {
		return (mCustomView != null);
	}
    
    public boolean inmCustomViewContainer() {
		return (mCustomViewContainer != null);
	}
    
    public void hideCustomView() {
		mWebChromeClient.onHideCustomView();
	}
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	if (keyCode == KeyEvent.KEYCODE_BACK) {
    		/*if (mCustomViewContainer != null) {
    	        mWebChromeClient.onHideCustomView();
    		}*/
    		/*if ((mCustomView == null) && canGoBack()){
    			goBack();
    			return true;
    		}*/
    	}
    	return super.onKeyDown(keyCode, event);
    }

    private class MyWebChromeClient extends WebChromeClient {
		private Bitmap 		mDefaultVideoPoster;
		private View 		mVideoProgressView;
        
		
		public boolean onJsAlert(WebView view, String url,
                String message, final android.webkit.JsResult result) {
       	 new AlertDialog.Builder((MainActivity)mContext)
                    //.setTitle("JobStrike")
                    .setMessage(message)
                    .setPositiveButton(android.R.string.ok,
                            new AlertDialog.OnClickListener() {
                                public void onClick(
                                        DialogInterface dialog,
                                        int which) {
                                    result.confirm();
                                }
                            }).setCancelable(false).create().show();

            return true;
        };

        public boolean onJsConfirm(WebView view, String url,
                String message, final android.webkit.JsResult result) {
            new AlertDialog.Builder((MainActivity)mContext)
                    //.setTitle("JobStrike")
                    .setMessage(message)
                    .setPositiveButton(android.R.string.yes,
                            new AlertDialog.OnClickListener() {
                                public void onClick(
                                        DialogInterface dialog,
                                        int which) {
                                    result.confirm();
                                }
                            })
                    .setNegativeButton(android.R.string.no,
                            new AlertDialog.OnClickListener() {
                                public void onClick(
                                        DialogInterface dialog,
                                        int which) {
                                    result.cancel();
                                }
                            }).setCancelable(false).create().show();
            return true;
        }
        
        
    	@Override
		public void onShowCustomView(View view, WebChromeClient.CustomViewCallback callback)
		{
			//Log.i(LOGTAG, "here in on ShowCustomView");
	        HTML5WebViewAll.this.setVisibility(View.GONE);
	        
	        // if a view already exists then immediately terminate the new one
	        if (mCustomView != null) {
	            callback.onCustomViewHidden();
	            return;
	        }
	        
	        mCustomViewContainer.addView(view);
	        mCustomView = view;
	        mCustomViewCallback = callback;
	        mCustomViewContainer.setVisibility(View.VISIBLE);
		}
		
		@Override
		public void onHideCustomView() {
			
			if (mCustomView == null)
				return;	       
			
			// Hide the custom view.
			mCustomView.setVisibility(View.GONE);
			
			// Remove the custom view from its container.
			mCustomViewContainer.removeView(mCustomView);
			mCustomView = null;
			mCustomViewContainer.setVisibility(View.GONE);
			mCustomViewCallback.onCustomViewHidden();
			
			HTML5WebViewAll.this.setVisibility(View.VISIBLE);
			
	        //Log.i(LOGTAG, "set it to webVew");
		}
		
		@Override
		public Bitmap getDefaultVideoPoster() {
			//Log.i(LOGTAG, "here in on getDefaultVideoPoster");	
			if (mDefaultVideoPoster == null) {
				mDefaultVideoPoster = BitmapFactory.decodeResource(
						getResources(), R.drawable.default_video_poster);
		    }
			return mDefaultVideoPoster;
		}
		
		@Override
		public View getVideoLoadingProgressView() {
			//Log.i(LOGTAG, "here in on getVideoLoadingPregressView");
			
	        if (mVideoProgressView == null) {
	            LayoutInflater inflater = LayoutInflater.from(mContext);
	            mVideoProgressView = inflater.inflate(R.layout.video_loading_progress, null);
	        }
	        return mVideoProgressView; 
		}
    	
    	 @Override
         public void onReceivedTitle(WebView view, String title) {
            ((Activity) mContext).setTitle(title);
         }

         @Override
         public void onProgressChanged(WebView view, int newProgress) {
        	 ((WordHelp) mContext).progressBar.setProgress(newProgress);
        	 //((Activity) mContext).getWindow().setFeatureInt(Window.FEATURE_PROGRESS, newProgress*100);
         }
         
         @Override
         public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
             callback.invoke(origin, true, false);
         }
    }
	
	private class MyWebViewClient extends WebViewClient {
		@Override
	    public boolean shouldOverrideUrlLoading(WebView view, String url) {
	    	LogUtil.d("HybridApp","===================shouldOverrideUrlLoading:"+url);
	    	return super.shouldOverrideUrlLoading(view, url);
	    }
	    
		public void onPageStarted(WebView view, String url,
				android.graphics.Bitmap favicon) {
			//super.onPageStarted(view, url, favicon);
			((WordHelp)mContext).progressBar.setVisibility(View.VISIBLE);
		};


		public void onPageFinished(WebView view, String url) {
			//super.onPageFinished(view, url);
			((WordHelp)mContext).progressBar.setVisibility(View.INVISIBLE);
		};
	    
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        	loadUrl("");
        	Toast.makeText((WordHelp)mContext, "현재 네트워크 상태가 불안정합니다.\n네트워크 상태를 확인하세요.\n뒤로 버튼을 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
        	((WordHelp)mContext).backend = true;
        }
	}
	
	static final FrameLayout.LayoutParams COVER_SCREEN_PARAMS =
        new FrameLayout.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

}
