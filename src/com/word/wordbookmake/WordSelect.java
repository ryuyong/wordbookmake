package com.word.wordbookmake;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class WordSelect extends Activity{
	ScrollView ScrlView;
	ImageView wordselect_imageview01;
	//Spinner wordselect_spinner01;
	ListView wordselect_listview01;
	TextView wordselect_textviewword;
	ImageButton wordselect_imagebutton01;
	ImageButton wordselect_imagebutton06;
	
	ImageView wordselect_imageview02;
	//Spinner wordselect_spinner02;
	ListView wordselect_listview02;
	TextView wordselect_textviewphase;
	ImageButton wordselect_imagebutton02;
	Common common;
	String[] keylists;
	String[] phrasekeylists;
	int selectposition = 10000;
	int selectposition2 = 10000;
	
	
	ImageButton wordselect_imagebutton07;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wordselect);
		
		ScrlView= (ScrollView)findViewById(R.id.ScrlView);
		
		common = new Common();
		wordselect_imagebutton01 = (ImageButton)findViewById(R.id.wordselect_imagebutton01);
		//wordselect_spinner01 = (Spinner)findViewById(R.id.wordselect_spinner01);
		wordselect_listview01 = (ListView)findViewById(R.id.wordselect_listview01);
		wordselect_textviewword = (TextView)findViewById(R.id.wordselect_textviewword);
		
		Resources res = getResources();
		//String[] wordlists = res.getStringArray(R.array.wordlists);
		String[] rewordlists = ExcelUtil.getWordTitles();
		keylists = ExcelUtil.getWordKeys();
		
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
//                this, R.array.wordlists, android.R.layout.simple_spinner_item);
		
		/*
		ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this,android.R.layout.simple_spinner_item,rewordlists);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        wordselect_spinner01.setAdapter(adapter);*/
        
        
        ArrayAdapter<String> adapterlist = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,rewordlists);
        wordselect_listview01.setAdapter(adapterlist);
        
        wordselect_listview01.setOnItemClickListener(
                new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						selectposition = position;
						Object o = wordselect_listview01.getItemAtPosition(position); 
						String obj_itemDetails = (String)o;
						wordselect_textviewword.setText(obj_itemDetails);
						LogUtil.d("ddddddddddddddd",obj_itemDetails+"ddddddddddddddd");
					}
                });	
        
        wordselect_listview01.setOnTouchListener(
                new OnTouchListener() {
					@Override
					public boolean onTouch(View v, MotionEvent event) {
				        if (event.getAction() == MotionEvent.ACTION_UP)
				        	ScrlView.requestDisallowInterceptTouchEvent(false);
				        else 
				        	ScrlView.requestDisallowInterceptTouchEvent(true);
				 
				        return false;
					}
                });	
        /*
        wordselect_spinner01.setOnItemSelectedListener(
                new OnItemSelectedListener() {
                    public void onItemSelected(
                            AdapterView<?> parent, View view, int position, long id) {
                    	selectposition = position;
                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });*/
        
        
        wordselect_imagebutton01.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if(selectposition == 10000) {
					showToast("단어를 선택해주세요");
					return;
				}
				
				Intent i = new Intent(WordSelect.this,WordList.class);
				//i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				i.putExtra("wordselect", keylists[selectposition]);
				startActivity(i);
			}
		});
        
        
        
        
		wordselect_imagebutton02 = (ImageButton)findViewById(R.id.wordselect_imagebutton02);
		wordselect_imagebutton02.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if(selectposition2 == 10000) {
					showToast("어휘를 선택해주세요");
					return;
				}
				
				Intent i = new Intent(WordSelect.this,PhraseList.class);
				//i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				i.putExtra("wordselect", phrasekeylists[selectposition2]);
				startActivity(i);
			}
		});
		wordselect_listview02 = (ListView)findViewById(R.id.wordselect_listview02);
		wordselect_textviewphase = (TextView)findViewById(R.id.wordselect_textviewphase);
		
		//wordselect_spinner02 = (Spinner)findViewById(R.id.wordselect_spinner02);
		String[] rephraselists = ExcelUtil.getPhraseTitles();
		phrasekeylists = ExcelUtil.getPhraseKeys();
		
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
//                this, R.array.wordlists, android.R.layout.simple_spinner_item);
		
		/*ArrayAdapter<CharSequence> adapter2 = new ArrayAdapter<CharSequence>(this,android.R.layout.simple_spinner_item,rephraselists);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        wordselect_spinner02.setAdapter(adapter2);
        
        wordselect_spinner02.setOnItemSelectedListener(
                new OnItemSelectedListener() {
                    public void onItemSelected(
                            AdapterView<?> parent, View view, int position, long id) {
                    	selectposition2 = position;
                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });*/
        ArrayAdapter<String> adapterlist2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,rephraselists);
        wordselect_listview02.setAdapter(adapterlist2);
        
        wordselect_listview02.setOnItemClickListener(
                new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						selectposition2 = position;
						Object o = wordselect_listview02.getItemAtPosition(position); 
						String obj_itemDetails = (String)o;
						wordselect_textviewphase.setText(obj_itemDetails);
						LogUtil.d("ddddddddddddddd",obj_itemDetails+"ddddddddddddddd");
					}
                });	
        
        wordselect_listview02.setOnTouchListener(
                new OnTouchListener() {
					@Override
					public boolean onTouch(View v, MotionEvent event) {
				        if (event.getAction() == MotionEvent.ACTION_UP)
				        	ScrlView.requestDisallowInterceptTouchEvent(false);
				        else 
				        	ScrlView.requestDisallowInterceptTouchEvent(true);
				 
				        return false;
					}
                });	
        
        
        
        wordselect_imagebutton06 = (ImageButton)findViewById(R.id.wordselect_imagebutton06);
        wordselect_imagebutton06.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if(selectposition == 10000) {
					showToast("단어를 선택해주세요");
					return;
				}
				
				Intent i = new Intent(WordSelect.this,WordQuestion.class);
				//i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				i.putExtra("wordselect", keylists[selectposition]);
				startActivity(i);
			}
		});
        
        wordselect_imagebutton07 = (ImageButton)findViewById(R.id.wordselect_imagebutton07);
        wordselect_imagebutton07.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if(selectposition2 == 10000) {
					showToast("어휘를 선택해주세요");
					return;
				}
				
				Intent i = new Intent(WordSelect.this,WordQuestion.class);
				//i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				i.putExtra("wordselect", phrasekeylists[selectposition2]);
				startActivity(i);
			}
		});
	}
	
    void showToast(CharSequence msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent i = new Intent(WordSelect.this,MainActivity.class);
			i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}
}
