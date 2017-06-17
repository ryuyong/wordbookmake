package com.word.wordbookmake;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import jxl.Sheet;
import jxl.Workbook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

public class WordQuestionWord extends Activity implements RadioGroup.OnCheckedChangeListener,View.OnClickListener,
	SeekBar.OnSeekBarChangeListener{
	ArrayList<String> wordarraykeylist;
	ArrayList<String> wordarrayvaluelist;
	ArrayList<String> wordarrayfulllist;
	RadioGroup wordquestionword_radiogroup01;
	TextView wordquestionword_textview01;
	TextView wordquestionword_textview02;
	TextView wordquestionword_textview03;
	ImageButton wordquestionword_button01;
	ImageButton wordquestionword_button02;
	Common common;
	String wordselect="";
	String gubun="";
	int arrayvalue=0;
	SeekBar wordquestionword_seek01;
	
	int random4;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wordquestionword);
		Bundle extras=getIntent().getExtras();
		
		if (extras!=null) {
			wordselect=extras.getString("wordselect");
			arrayvalue=extras.getInt("arrayvalue");
			gubun = extras.getString("gubun");
		}
		//android.util.Log.e("","=======================================arrayvalue:"+arrayvalue);
		
        common = new Common();
        wordarraykeylist = new ArrayList<String>();
        wordarrayvaluelist = new ArrayList<String>();
        wordarrayfulllist = new ArrayList<String>();
		try {
			raw2file(this);
		} catch (Exception e) {
		}
		wordquestionword_textview01= (TextView)findViewById(R.id.wordquestionword_textview01);
		wordquestionword_textview02= (TextView)findViewById(R.id.wordquestionword_textview02);
		wordquestionword_radiogroup01 = (RadioGroup)findViewById(R.id.wordquestionword_radiogroup01);
		wordquestionword_radiogroup01.setOnCheckedChangeListener(this);
		
		wordquestionword_button01 = (ImageButton)findViewById(R.id.wordquestionword_button01);
		wordquestionword_button01.setOnClickListener(this);
		
		wordquestionword_button02 = (ImageButton)findViewById(R.id.wordquestionword_button02);
		wordquestionword_button02.setOnClickListener(this);
		
		wordquestionword_seek01 = (SeekBar)findViewById(R.id.wordquestionword_seek01);
		wordquestionword_seek01.setMax(wordarraykeylist.size()-1);
		wordquestionword_seek01.setProgress(arrayvalue);
		wordquestionword_seek01.setOnSeekBarChangeListener(this);
		
		wordquestionword_textview03 = (TextView)findViewById(R.id.wordquestionword_textview03);
		wordquestionword_textview03.setText(arrayvalue+1+" / "+wordarraykeylist.size());
		
		random4 = common.rand(4);
		ArrayList<String> templist = new ArrayList<String>();
		//android.util.Log.e("","=======================================random4:"+random4);
		
		for(int i=0;i<4;i++) {
			int temprandom = common.randArray(wordarraykeylist.size(),wordarraykeylist.get(arrayvalue),wordarraykeylist, templist);
			//android.util.Log.e("","=======================================temprandom:"+i+":"+temprandom);
			if(random4 == i) {
				continue;
			} else {
				templist.add(temprandom+"");
			}
		}
		int oid = common.getResourceId("wordquestionword_radiobutton0"+random4);
		
		RadioButton radiobuttono = (RadioButton)findViewById(oid);
		radiobuttono.setText(wordarrayvaluelist.get(arrayvalue));
		//android.util.Log.e("","wordarrayvaluelist.get(arrayvalue):"+wordarrayvaluelist.get(arrayvalue));
		wordquestionword_textview01.setText(wordarraykeylist.get(arrayvalue));
		
		int setcount = 0;
		for(int i=0;i<4;i++) {
			if(random4 == i) {
				continue;
			} else {
				String setvalue = templist.get(setcount);
				int eid = common.getResourceId("wordquestionword_radiobutton0"+i);
				RadioButton radiobuttone = (RadioButton)findViewById(eid);
				//android.util.Log.e("","=======================================Integer.parseInt(setvalue):"+i+":"+Integer.parseInt(setvalue));
				//android.util.Log.e("","wordarrayvaluelist.get(Integer.parseInt(setvalue)):"+i+":"+wordarrayvaluelist.get(Integer.parseInt(setvalue)));
				radiobuttone.setText(wordarrayvaluelist.get(Integer.parseInt(setvalue)));
				setcount++;
			}
			
		}
    }
    
  //Raw 리소스 파일의 저장 （1）
	private void raw2file(Context context) throws Exception {
		Workbook workbook = null;
        Sheet sheet = null;
        File sdcard = Environment.getExternalStorageDirectory();
        File file = new File(sdcard, "/wordbookmake/"+wordselect+".xls");
        
        try {
        	InputStream  fis = null;
        	fis = new BufferedInputStream(new FileInputStream(file.getAbsolutePath()));
        	
            //InputStream is = new FileInputStream(file);
            workbook = Workbook.getWorkbook(fis);
 
            if (workbook != null) {
                sheet = workbook.getSheet(0);
 
                if (sheet != null) {
 
                    int nMaxColumn = 2;
                    int nRowStartIndex = 0;
                    int nRowEndIndex = sheet.getColumn(nMaxColumn - 1).length - 1;
                    int nColumnStartIndex = 0;
                    //int nColumnEndIndex = sheet.getRow(2).length - 1;
                    
                    for (int nRow = nRowStartIndex; nRow <= nRowEndIndex; nRow++) {
                        String title = sheet.getCell(nColumnStartIndex, nRow).getContents();
                        String body = sheet.getCell(nColumnStartIndex + 1, nRow).getContents();
    					wordarraykeylist.add(title);
    					wordarrayvaluelist.add(body);
    					wordarrayfulllist.add(title+":"+body);
                    }
                } else {
                }
            } else {
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (workbook != null) {
                workbook.close();
            }
        }
	}
	
    public void onCheckedChanged(RadioGroup group, int checkedId) {
    	int oid = common.getResourceId("wordquestionword_radiobutton0"+random4);
    	if(oid == checkedId) {
    		//wordquestionword_textview02.setTextColor(Color.BLUE);
	    	wordquestionword_textview02.setText("O");
    	} else {
    		//wordquestionword_textview02.setTextColor(Color.RED);
	    	wordquestionword_textview02.setText("X");
    	}
    }

	@Override
	public void onClick(View view) {
		if(view.getId() == R.id.wordquestionword_button01) {
			Intent i = new Intent(WordQuestionWord.this,WordQuestionWord.class);
			i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			i.putExtra("wordselect", wordselect);
			if(arrayvalue == 0) {
				i.putExtra("arrayvalue", 0);
			} else {
				i.putExtra("arrayvalue", arrayvalue-1);
			}
			i.putExtra("gubun", gubun);
			startActivity(i);
		} else if(view.getId() == R.id.wordquestionword_button02) {
			Intent i = new Intent(WordQuestionWord.this,WordQuestionWord.class);
			i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			i.putExtra("wordselect", wordselect);
			if(arrayvalue == wordarraykeylist.size()-1) {
				i.putExtra("arrayvalue", wordarraykeylist.size()-1);
			} else {
				i.putExtra("arrayvalue", arrayvalue+1);
			}
			i.putExtra("gubun", gubun);
			startActivity(i);
		}
	}

	@Override
	public void onProgressChanged(SeekBar seekbar, int i, boolean flag) {
		// TODO Auto-generated method stub
		//android.util.Log.e("","=======================================SeekBar:"+i+":"+i);
		Intent intent = new Intent(WordQuestionWord.this,WordQuestionWord.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra("wordselect", wordselect);
		intent.putExtra("arrayvalue", i);
		intent.putExtra("gubun", gubun);
		startActivity(intent);
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekbar) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekbar) {
		// TODO Auto-generated method stub
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent i = new Intent(WordQuestionWord.this,WordSelectWord.class);
			i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}
}
