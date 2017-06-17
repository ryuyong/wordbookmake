package com.word.wordbookmake;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PlusAddPhase extends Activity implements View.OnClickListener {
	private EditText plusaddword_edittext01;
	private EditText plusaddword_edittext02;
	private EditText plusaddword_edittext03;
	private ImageView plusaddword_imageview01;
	private ImageView plusaddword_imageview02;
	
	public static Activity showMsgActivity;
	public String wordselect="";
	public String title="";
	public String content="";
	public String example="";
	public int currentposition=-1;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		showMsgActivity = this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.plusaddphase);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		Bundle extras=getIntent().getExtras();
		
		if (extras!=null) {
			wordselect=extras.getString("wordselect");
			title=extras.getString("title");
			content=extras.getString("content");
			example=extras.getString("example");
			currentposition = extras.getInt("currentposition");
		}
		
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
				WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
				WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
				WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

		plusaddword_edittext01 = (EditText) findViewById(R.id.plusaddword_edittext01);
		plusaddword_edittext02 = (EditText) findViewById(R.id.plusaddword_edittext02);
		plusaddword_edittext03 = (EditText) findViewById(R.id.plusaddword_edittext03);
		
		if(title != null && title.length() > 0) {
			plusaddword_edittext01.setText(title);
			plusaddword_edittext02.setText(content);
			plusaddword_edittext03.setText(example);
		}
		
		plusaddword_imageview01 = (ImageView) findViewById(R.id.plusaddword_imageview01);
		plusaddword_imageview02 = (ImageView) findViewById(R.id.plusaddword_imageview02);
		
		//showmsg_edittext01.setText("원어");
		//showmsg_edittext02.setText("한국어");
		
		plusaddword_imageview01.setOnClickListener(this);
		plusaddword_imageview02.setOnClickListener(this);
		// 폰 설정의 조명시간을 가져와서 해당 시간만큼만 화면을 켠다.
		int defTimeOut = Settings.System.getInt(getContentResolver(),
				Settings.System.SCREEN_OFF_TIMEOUT, 15000);
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				PushWakeLock.releaseCpuLock();
			}
		};

		Timer timer = new Timer();
		timer.schedule(task, defTimeOut);
	}

	@Override
	public void onClick(View view) {
		if (view.getId() == R.id.plusaddword_imageview01) {
			PushWakeLock.releaseCpuLock();
			String moto = plusaddword_edittext01.getText().toString();
			if(moto == null || moto.length() == 0) {
				showToast("원어를 입력해주세요.");
				return;
			}
			
			String korean = plusaddword_edittext02.getText().toString();
			if(korean == null || korean.length() == 0) {
				showToast("한국어를 입력해주세요.");
				return;
			}
			
			String example = plusaddword_edittext03.getText().toString();
			if(example == null || example.length() == 0) {
				showToast("예제를 입력해주세요.");
				return;
			}
			
			if(currentposition == -1) {
				doExcel(moto,korean,example);
			} else {
				doExcelUpdate(moto,korean,example);
			}
			
			
			Intent intent = new Intent();

			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
			intent.putExtra("wordselect", wordselect);
			//Var.getInstance().setGoDetail("YES");
			startActivity(intent.setClassName(getPackageName(),
					getPackageName() + ".PhraseList"));
			
			PlusAddPhase.this.finish();
		} else if (view.getId() == R.id.plusaddword_imageview02) {
			//Var.getInstance().setGoDetail("NO");
			PushWakeLock.releaseCpuLock();
			//showToast("NO");
			PlusAddPhase.this.finish();
		}
	}
	
	
	
	
	public void doExcel(String moto, String korean, String example) {
		File sdcard = Environment.getExternalStorageDirectory();
		File file = new File(sdcard, "/wordbookmake/"+wordselect+".xls");
		WritableSheet sheet = null;
		try {
			
	        InputStream  fis = new BufferedInputStream(new FileInputStream(file.getAbsolutePath()));
        	jxl.Workbook workbook = jxl.Workbook.getWorkbook (fis);
        	
		    jxl.write.WritableWorkbook copy = jxl.Workbook.createWorkbook ( file, workbook );
            //InputStream is = new FileInputStream(file);
            //workbook = Workbook.createWorkbook(file);

			sheet = copy.getSheet(0);
			int rows = sheet.getRows();

			WritableCell addcell = sheet.getWritableCell(rows, 0);

			
			if(addcell.getCellFormat() != null) {
				jxl.write.Label label = new jxl.write.Label(0,rows,moto,addcell.getCellFormat());
				jxl.write.Label label1 = new jxl.write.Label(1,rows,korean,addcell.getCellFormat());
				jxl.write.Label label2 = new jxl.write.Label(2,rows,example,addcell.getCellFormat());

				sheet.addCell(label);
				sheet.addCell(label1);
				sheet.addCell(label2);
			} else {
				jxl.write.Label label = new jxl.write.Label ( 0, rows, moto );
				jxl.write.Label label1 = new jxl.write.Label ( 1, rows, korean );
				jxl.write.Label label2 = new jxl.write.Label ( 2, rows, example );

				sheet.addCell(label);
				sheet.addCell(label1);
				sheet.addCell(label2);
			}
			
			
			copy.write ();
			copy.close ();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	public void doExcelUpdate(String moto, String korean, String example) {
		File sdcard = Environment.getExternalStorageDirectory();
		File file = new File(sdcard, "/wordbookmake/"+wordselect+".xls");
		
        jxl.write.WritableSheet sheet = null;
		try {
			
	        InputStream  fis = null;
        	fis = new BufferedInputStream(new FileInputStream(file.getAbsolutePath()));
        	jxl.Workbook workbook = jxl.Workbook.getWorkbook (fis);
        	
		    jxl.write.WritableWorkbook copy = jxl.Workbook.createWorkbook ( file, workbook );
            //InputStream is = new FileInputStream(file);
            //workbook = Workbook.createWorkbook(file);
            

			sheet = copy.getSheet(0);
			int rows = sheet.getRows();
			for(int i=0;i<rows;i++) {
				
				WritableCell addcell = sheet.getWritableCell(0, i);
				WritableCell addcell1 = sheet.getWritableCell(1, i);
				WritableCell addcell2 = sheet.getWritableCell(2, i);
				String content = addcell.getContents();
				String content1 = addcell1.getContents();
				String content2 = addcell2.getContents();
				if(i == currentposition) {
					if(addcell.getCellFormat() != null) {
						jxl.write.Label label = new jxl.write.Label(0,i,moto,addcell.getCellFormat());
						jxl.write.Label label1 = new jxl.write.Label(1,i,korean,addcell.getCellFormat());
						jxl.write.Label label2 = new jxl.write.Label(2,i,example,addcell.getCellFormat());
						sheet.addCell(label);
						sheet.addCell(label1);
						sheet.addCell(label2);
					} else {
						jxl.write.Label label = new jxl.write.Label ( 0, i, moto);
						jxl.write.Label label1 = new jxl.write.Label ( 1, i, korean );
						jxl.write.Label label2 = new jxl.write.Label ( 2, i, example );
						sheet.addCell(label);
						sheet.addCell(label1);
						sheet.addCell(label2);
					}
				} else {
					if(addcell.getCellFormat() != null) {
						jxl.write.Label label = new jxl.write.Label(0,i,content,addcell.getCellFormat());
						jxl.write.Label label1 = new jxl.write.Label(1,i,content1,addcell.getCellFormat());
						jxl.write.Label label2 = new jxl.write.Label(2,i,content2,addcell.getCellFormat());
						sheet.addCell(label);
						sheet.addCell(label1);
						sheet.addCell(label2);
					} else {
						jxl.write.Label label = new jxl.write.Label ( 0, i, content);
						jxl.write.Label label1 = new jxl.write.Label ( 1, i, content1 );
						jxl.write.Label label2 = new jxl.write.Label ( 2, i, content2 );
						sheet.addCell(label);
						sheet.addCell(label1);
						sheet.addCell(label2);
					}
				}
			}
			

			
			
			copy.write ();
			copy.close ();

		} catch (Exception e) {
			e.printStackTrace();
		}
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
	
}