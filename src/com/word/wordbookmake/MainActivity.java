package com.word.wordbookmake;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import jxl.Sheet;
import jxl.Workbook;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends Activity {
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        //main_imagebutton01 = (ImageButton)findViewById(R.id.main_imagebutton01);
        //main_imagebutton01.setOnClickListener(this);
         
        // RelativeLayout 생성
        RelativeLayout rl = new RelativeLayout(this);
         
        // RelativeLayout width, height 설정
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams
                (ViewGroup.LayoutParams.WRAP_CONTENT, 
                 ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 100, 0, 100);
        //rl.setBackgroundColor(Color.WHITE);
        //rl.setBackgroundResource(R.layout.mainshapeback);
        rl.setBackgroundResource(R.drawable.mainimage);
        //rl.setBackgroundColor(Color.WHITE);
        rl.setLayoutParams(params);
        
        DisplayMetrics disp = getApplicationContext().getResources().getDisplayMetrics();
        // int deviceWidth = disp.widthPixels;
        int deviceHeight = disp.heightPixels;
        int onedeviceHeight = deviceHeight / 10;
        
        LogUtil.d("height","===================height:"+deviceHeight);
        // LogUtil.d("heightd","===================heightd:"+heightd); 
        
        /** 상위View */
        Button viewButton = new Button(this);
        viewButton.setId(1000);
        viewButton.setText("♥나만의단어장♥");
        viewButton.setBackgroundColor(Color.BLACK);
        viewButton.setHeight(onedeviceHeight+20);
        viewButton.setTextSize(35);
        viewButton.setTextColor(Color.WHITE);
        Animation aniview = AnimationUtils.loadAnimation(this, R.anim.simple_animation);
        aniview.setStartOffset(0);
		viewButton.startAnimation(aniview);
		
        // RelativeLayout의 차일드 View이기 때문에 RelativeLayout의 LayoutParams을
        // 적용해 준다.
        RelativeLayout.LayoutParams viewButtonParams = new RelativeLayout.LayoutParams
                (ViewGroup.LayoutParams.MATCH_PARENT, 
                 ViewGroup.LayoutParams.WRAP_CONTENT);
        viewButtonParams.setMargins(18, 20, 18, 10);
        viewButton.setLayoutParams(viewButtonParams);
        
        
        
        /** 상단 버튼 추가 */
        Button topButton = new Button(this);
        topButton.setId(1);
        topButton.setText("단어공부");
        topButton.setBackgroundResource(R.layout.buttonshapered);
        topButton.setHeight(onedeviceHeight);
        topButton.setTextSize(30);
        topButton.setTextColor(Color.WHITE);

        Animation ani = AnimationUtils.loadAnimation(this, R.anim.simple_animation);
		ani.setStartOffset(0);
		topButton.startAnimation(ani);
		
        // RelativeLayout의 차일드 View이기 때문에 RelativeLayout의 LayoutParams을
        // 적용해 준다.
        RelativeLayout.LayoutParams topButtonParams = new RelativeLayout.LayoutParams
                (ViewGroup.LayoutParams.MATCH_PARENT, 
                 ViewGroup.LayoutParams.WRAP_CONTENT);
        topButtonParams.addRule(RelativeLayout.BELOW, 1000);
        topButtonParams.setMargins(18, 50, 18, 10);
        topButton.setLayoutParams(topButtonParams);
        topButton.setOnClickListener(topbuttonListner);
         
        /** 중간 버튼 추가 */
        Button middleButton = new Button(this);
        middleButton.setId(2);
        middleButton.setText("숙어공부");
        middleButton.setBackgroundResource(R.layout.buttonshapeblue);
        middleButton.setHeight(onedeviceHeight);
        middleButton.setTextSize(30);
        middleButton.setTextColor(Color.WHITE);
        Animation ani2 = AnimationUtils.loadAnimation(this, R.anim.simple_animation);
        ani2.setStartOffset(500);
        middleButton.startAnimation(ani2);
        
        
        RelativeLayout.LayoutParams middleButtonParams = new RelativeLayout.LayoutParams
                (ViewGroup.LayoutParams.MATCH_PARENT, 
                 ViewGroup.LayoutParams.WRAP_CONTENT);
        middleButtonParams.addRule(RelativeLayout.BELOW, 1);
        middleButtonParams.setMargins(18, 10, 18, 10);
        middleButton.setLayoutParams(middleButtonParams);
        middleButton.setOnClickListener(middlebuttonListner);
        
        
        
        /** 영어사전 버튼 추가 */
        Button enButton = new Button(this);
        enButton.setId(3);
        enButton.setText("사전");
        enButton.setBackgroundResource(R.layout.buttonshapegreen);
        enButton.setHeight(onedeviceHeight);
        enButton.setTextSize(30);
        enButton.setTextColor(Color.WHITE);
        Animation anien = AnimationUtils.loadAnimation(this, R.anim.simple_animation);
        anien.setStartOffset(1000);
        enButton.startAnimation(anien);
        
        
        RelativeLayout.LayoutParams enButtonParams = new RelativeLayout.LayoutParams
                (ViewGroup.LayoutParams.MATCH_PARENT, 
                 ViewGroup.LayoutParams.WRAP_CONTENT);
        enButtonParams.addRule(RelativeLayout.BELOW, 2);
        enButtonParams.setMargins(18, 10, 18, 10);
        enButton.setLayoutParams(enButtonParams);
        enButton.setOnClickListener(enbuttonListner);
        
        
         
         
        /** 하단 버튼 추가 */
        Button bottomButton = new Button(this);
        bottomButton.setId(5);
        bottomButton.setText("만들기도움말");
        bottomButton.setBackgroundResource(R.layout.buttonshapegreen);
        bottomButton.setHeight(onedeviceHeight);
        bottomButton.setTextSize(30);
        bottomButton.setTextColor(Color.WHITE);
        Animation ani3 = AnimationUtils.loadAnimation(this, R.anim.simple_animation);
        ani3.setStartOffset(1500);
        bottomButton.startAnimation(ani3);
		
        RelativeLayout.LayoutParams bottomButtonParams = new RelativeLayout.LayoutParams
                (ViewGroup.LayoutParams.MATCH_PARENT, 
                 ViewGroup.LayoutParams.WRAP_CONTENT);
        bottomButtonParams.addRule(RelativeLayout.BELOW, 3);
        bottomButtonParams.setMargins(18, 10, 18, 10);
        
        //bottomButtonParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
         
        bottomButton.setLayoutParams(bottomButtonParams);
        bottomButton.setOnClickListener(buttombuttonListner);
         
         
        // RelativeLayout에 차일드 View 추가
        rl.addView(viewButton);
        rl.addView(topButton);
        rl.addView(middleButton);
        rl.addView(enButton);
        rl.addView(bottomButton);
         
        // Acitivty화면에 보일 View를 연결해 준다.
        setContentView(rl);
        
        
        
        File sdcard = Environment.getExternalStorageDirectory();
        //SDCARD에 폴더 생성
        File maindirect = new File(sdcard, "wordbookmake");
        File word = new File(sdcard, "wordbookmake/단어");
        File pharase = new File(sdcard, "wordbookmake/숙어");
        if(!maindirect.exists()) {
        	maindirect.mkdirs();
        	word.mkdirs();
        	pharase.mkdirs();
        }
    }
    
	
	
	
	private void copyExcelDataToDatabase(String filename) {
    	LogUtil.d("filename",filename);
        Workbook workbook = null;
        Sheet sheet = null;
        File sdcard = Environment.getExternalStorageDirectory();
        File file = new File(sdcard, "/wordbookmake/단어/"+filename);
        
        
        
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
                    int nColumnEndIndex = sheet.getRow(2).length - 1;
                    
                    for (int nRow = nRowStartIndex; nRow <= nRowEndIndex; nRow++) {
                        String title = sheet.getCell(nColumnStartIndex, nRow).getContents();
                        String body = sheet.getCell(nColumnStartIndex + 1, nRow).getContents();
                        LogUtil.d("title",title);
                        LogUtil.d("body",body);
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
    /*
	@Override
	public void onClick(View v) {
		
		if(v.getId() == R.id.main_imagebutton01) {
			Intent i = new Intent(MainActivity.this,WordSelect.class);
			startActivity(i);
		} 
	}*/
	public void showAlert(int reason) {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				MainActivity.this);
		if (reason == 1) {
			builder.setMessage("종료하시겠습니까?");
			builder.setCancelable(true);
			builder.setPositiveButton("확인",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) { // khsug
							System.exit(0);
						}
					});
			builder.setNegativeButton("취소",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
						}
					});

			AlertDialog alert = builder.create();
			alert.show();
		}
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			showAlert(1);
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	
    public View.OnClickListener topbuttonListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
            case 1:
    			Intent i = new Intent(MainActivity.this,WordSelectWord.class);
    			startActivity(i);
                break;
 
            default:
                break;
            }
        }
    };
    
    public View.OnClickListener middlebuttonListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
            case 2:
    			Intent i = new Intent(MainActivity.this,WordSelectPhase.class);
    			startActivity(i);
                break;
 
            default:
                break;
            }
        }
    };
    
    
    public View.OnClickListener buttombuttonListner = new View.OnClickListener() {
        
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
            case 5:
                //Toast.makeText(MainActivity.this, "하단 버튼 Click~!!",Toast.LENGTH_SHORT).show();
    			Intent i = new Intent(MainActivity.this,WordHelp.class);
    			startActivity(i);
                break;
 
            default:
                break;
            }
        }
    };
    
    
    public View.OnClickListener enbuttonListner = new View.OnClickListener() {
        
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
            case 3:
                //Toast.makeText(MainActivity.this, "하단 버튼 Click~!!",Toast.LENGTH_SHORT).show();
    			Intent i = new Intent(MainActivity.this,WordDic.class);
    			startActivity(i);
                break;
 
            default:
                break;
            }
        }
    };

}
