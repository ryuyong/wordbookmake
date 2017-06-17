package com.word.wordbookmake;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class WordSelectWord extends Activity{
	ImageView wordselectword_imageview01;
	
	ListView wordselectword_listview01;
	TextView wordselectword_textview01;
	TextView wordselectword_textview02;
	
	ImageButton wordselectword_imagebutton01;
	ImageButton wordselectword_imagebutton02;
	
	Common common;
	String[] keylists;
	String[] phrasekeylists;
	int selectposition = 10000;
	int selectposition2 = 10000;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wordselectword);
		common = new Common();
		wordselectword_listview01 = (ListView)findViewById(R.id.wordselectword_listview01);
		wordselectword_textview01 = (TextView)findViewById(R.id.wordselectword_textview01);
		wordselectword_textview02 = (TextView)findViewById(R.id.wordselectword_textview02);
		
		wordselectword_imagebutton01 = (ImageButton)findViewById(R.id.wordselectword_imagebutton01);
		wordselectword_imagebutton02 = (ImageButton)findViewById(R.id.wordselectword_imagebutton02);
		
		Resources res = getResources();
		String[] rewordlists = ExcelUtil.getWordTitles();
		keylists = ExcelUtil.getWordKeys();

        
        ArrayAdapter<String> adapterlist = new ArrayAdapter<String>(this, R.layout.simple_list_item_1red,rewordlists);
        wordselectword_listview01.setAdapter(adapterlist);
        
        wordselectword_listview01.setOnItemClickListener(
                new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						selectposition = position;
						Object o = wordselectword_listview01.getItemAtPosition(position); 
						String obj_itemDetails = (String)o;
						wordselectword_textview02.setText(obj_itemDetails);
						wordselectword_textview02.setTextColor(Color.YELLOW);
						LogUtil.d("ddddddddddddddd",obj_itemDetails+"ddddddddddddddd");
					}
                });	
        wordselectword_imagebutton01.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if(selectposition == 10000) {
					showToast("단어를 선택해주세요");
					return;
				}
				Intent i = new Intent(WordSelectWord.this,WordList.class);
				//i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				i.putExtra("wordselect", keylists[selectposition]);
				startActivity(i);
			}
		});
        
        wordselectword_imagebutton02.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if(selectposition == 10000) {
					showToast("단어를 선택해주세요");
					return;
				}
				Intent i = new Intent(WordSelectWord.this,WordQuestionWord.class);
				//i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				i.putExtra("wordselect", keylists[selectposition]);
				i.putExtra("gubun", "WORD");
				startActivity(i);
			}
		});
	}
	
	
	private void showToast(CharSequence text) {
		  TextView tv = new TextView(this.getApplicationContext()); 
		  tv.setText("\n" + text + "\n");
		  tv.setTextSize(20);
		  tv.setTextColor(Color.BLACK); 
		  LinearLayout ll = new LinearLayout(this.getApplicationContext()); 
		  LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
		            (ViewGroup.LayoutParams.WRAP_CONTENT, 
		             ViewGroup.LayoutParams.WRAP_CONTENT);
		  params.setMargins(50, 50, 50, 50);
		  ll.setLayoutParams(params); 
		  
		  //NOTICE 그림
		  ll.setBackgroundResource(R.drawable.toast); 
		  ll.setGravity(Gravity.CENTER); 
		  ll.addView(tv); 
		  Toast t = Toast.makeText(this.getApplicationContext(), "", Toast.LENGTH_SHORT); 
		  t.setGravity(Gravity.CENTER, 0, 0); 
		  t.setView(ll); 
		  t.show(); 
	}
	/*
	void showToast(CharSequence msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}*/
    
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent i = new Intent(WordSelectWord.this,MainActivity.class);
			i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}
}
