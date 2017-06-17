package com.word.wordbookmake;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import jxl.Sheet;
import jxl.Workbook;
import jxl.write.WritableCell;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

public class PhraseList extends ListActivity {
	private String[] mTitles;
	private String[] mDialogue;
	private boolean[] mExpanded;
	
	ArrayList<String> titlearraylist;
	ArrayList<String> conarraylist;
	
	ArrayList<String> phrasearraylist;
	ArrayList<String> phrasearrayfulllist;
	ArrayList<String> phrasearrayexlist;
	Common common;
	String wordselect="";
	String viewoption = "";
	
	Button phaselist_button01;
	Button phaselist_button02;
	Button phaselist_button03;
	Button phaselist_button04;
	Button phaselist_button05;
	Button phaselist_button06;
	
	private static final int VIEWALL = Menu.FIRST+1;
	private static final int VIEWJAPAN = Menu.FIRST+2;
	private static final int VIEWKOREAN = Menu.FIRST+3;
	
	private int currentposition = -1;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.phaselist);
		Bundle extras=getIntent().getExtras();
		
		if (extras!=null) {
			wordselect=extras.getString("wordselect");
			viewoption=extras.getString("viewoption");
		}
		
		phaselist_button01 = (Button)findViewById(R.id.phaselist_button01);
		phaselist_button02 = (Button)findViewById(R.id.phaselist_button02);
		phaselist_button03 = (Button)findViewById(R.id.phaselist_button03);
		phaselist_button04 = (Button)findViewById(R.id.phaselist_button04);
		phaselist_button05 = (Button)findViewById(R.id.phaselist_button05);
		phaselist_button06 = (Button)findViewById(R.id.phaselist_button06);
		
		phaselist_button01.setOnClickListener(allbuttonListner);
		phaselist_button02.setOnClickListener(motobuttonListner);
		phaselist_button03.setOnClickListener(kobuttonListner);
		phaselist_button04.setOnClickListener(addbuttonListner);
		phaselist_button05.setOnClickListener(delbuttonListner);
		phaselist_button06.setOnClickListener(updbuttonListner);
        
		if(viewoption != null && viewoption.equals("viewjapan")) {
			phaselist_button01.setBackgroundResource(R.drawable.allview1);
			phaselist_button02.setBackgroundResource(R.drawable.motoview1on);
			phaselist_button03.setBackgroundResource(R.drawable.koview1);
		} else if(viewoption != null && viewoption.equals("viewkorean")) {
			phaselist_button01.setBackgroundResource(R.drawable.allview1);
			phaselist_button02.setBackgroundResource(R.drawable.motoview1);
			phaselist_button03.setBackgroundResource(R.drawable.koview1on);
		} else {
			phaselist_button01.setBackgroundResource(R.drawable.allview1on);
			phaselist_button02.setBackgroundResource(R.drawable.motoview1);
			phaselist_button03.setBackgroundResource(R.drawable.koview1);
		}
		
        common = new Common();
        
        phrasearraylist = new ArrayList<String>();
        phrasearrayfulllist = new ArrayList<String>();
        phrasearrayexlist = new ArrayList<String>();
        
        titlearraylist = new ArrayList<String>();
        conarraylist = new ArrayList<String>();
		try {
			raw2file(this);
		} catch (Exception e) {
		}
		
		mTitles = new String[phrasearraylist.size()];
		mDialogue = new String[phrasearraylist.size()];
		mExpanded = new boolean[phrasearraylist.size()];
		phrasearraylist.toArray(mTitles);
		phrasearrayexlist.toArray(mDialogue);
		
		for(int i=0;i<phrasearraylist.size();i++) {
			mExpanded[i] = false;
		}
		
	    // Use our own list adapter
	    setListAdapter(new SpeechListAdapter(this));
	}
	    
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) 
	{    
		String value = phrasearrayfulllist.get(position);
		String toastvalue = "";
		if(viewoption != null && viewoption.equals("viewjapan")) {
			toastvalue = common.getStringValue(value, 1);
			showToast(toastvalue);
			currentposition = position;
			((SpeechListAdapter)getListAdapter()).refresh();
			//Toast.makeText(this, toastvalue, Toast.LENGTH_SHORT).show();
		} else if(viewoption != null && viewoption.equals("viewkorean")) {
			toastvalue = common.getStringValue(value, 0);
			showToast(toastvalue);
			currentposition = position;
			((SpeechListAdapter)getListAdapter()).refresh();
			//Toast.makeText(this, toastvalue, Toast.LENGTH_SHORT).show();
		} else {
	    	LogUtil.d("PhraseList","=====================================================");
	    	LogUtil.d("PhraseList",position+"=====================================================");
	    	LogUtil.d("PhraseList","=====================================================");
	    	currentposition = position;
			((SpeechListAdapter)getListAdapter()).toggle(position);
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
		  ll.setBackgroundResource(R.drawable.toast1); 
		  ll.setGravity(Gravity.CENTER); 
		  ll.addView(tv); 
		  Toast t = Toast.makeText(this.getApplicationContext(), "", Toast.LENGTH_SHORT); 
		  t.setGravity(Gravity.CENTER, 0, 0); 
		  t.setView(ll); 
		  t.show(); 
	}
	
	//Raw 리소스 파일의 저장 （1）
	private void raw2file(Context context) throws Exception {
		titlearraylist = new ArrayList<String>();
		conarraylist = new ArrayList<String>();
		phrasearraylist = new ArrayList<String>();
		phrasearrayfulllist = new ArrayList<String>();
		phrasearrayexlist = new ArrayList<String>();
		
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
 
                    int nMaxColumn = 3;
                    int nRowStartIndex = 0;
                    int nRowEndIndex = sheet.getColumn(nMaxColumn - 1).length - 1;
                    int nColumnStartIndex = 0;
                    //int nColumnEndIndex = sheet.getRow(2).length - 1;
                    
                    for (int nRow = nRowStartIndex; nRow <= nRowEndIndex; nRow++) {
                        String title = sheet.getCell(nColumnStartIndex, nRow).getContents();
                        String body = sheet.getCell(nColumnStartIndex + 1, nRow).getContents();
                        String example = sheet.getCell(nColumnStartIndex + 2, nRow).getContents();
                        
                        titlearraylist.add(title);
                        conarraylist.add(body);
                        
                        if(viewoption != null && viewoption.equals("viewjapan")) {
                        	phrasearraylist.add(title);
    					} else if(viewoption != null && viewoption.equals("viewkorean")) {
    						phrasearraylist.add(body);
    					} else {
    						phrasearraylist.add(title+":"+body);
    					}
                        phrasearrayfulllist.add(title+":"+body);
                        phrasearrayexlist.add(example);
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
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(Menu.NONE, VIEWALL, Menu.NONE, "전체")
		.setIcon(R.drawable.menuall)
		.setAlphabeticShortcut('u');

		menu.add(Menu.NONE, VIEWJAPAN, Menu.NONE, "원어만")
		.setIcon(R.drawable.menujapan)
		.setAlphabeticShortcut('j');
		
		menu.add(Menu.NONE, VIEWKOREAN, Menu.NONE, "한국어만")
		.setIcon(R.drawable.menukorean)
		.setAlphabeticShortcut('k');
		
		return super.onCreateOptionsMenu(menu);
	}*/
	
	@Override
	protected void onResume() {
		//changePage();
		try {
			raw2file(this);
			mTitles = new String[phrasearraylist.size()];
			mDialogue = new String[phrasearraylist.size()];
			mExpanded = new boolean[phrasearraylist.size()];
			phrasearraylist.toArray(mTitles);
			phrasearrayexlist.toArray(mDialogue);
			for(int i=0;i<phrasearraylist.size();i++) {
				mExpanded[i] = false;
			}
			//setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,wordarraylist));
			setListAdapter(new SpeechListAdapter(this));
		} catch (Exception e) {
			e.printStackTrace();
		}
		LogUtil.d("onResume","=======================================");
		LogUtil.d("onResume","onResume");
		LogUtil.d("onResume","=======================================");
		// TODO Auto-generated method stub
		super.onResume();
		//super.onRestart();
		
	}
	public View.OnClickListener allbuttonListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
			Intent i = new Intent(PhraseList.this,PhraseList.class);
			//i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			i.putExtra("wordselect", wordselect);
			i.putExtra("viewoption", "viewall");
			startActivity(i);
        }
    };
    
    public View.OnClickListener motobuttonListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
			Intent i1 = new Intent(PhraseList.this,PhraseList.class);
			//i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			i1.putExtra("wordselect", wordselect);
			i1.putExtra("viewoption", "viewjapan");
			startActivity(i1);
        }
    };
    
    
    public View.OnClickListener kobuttonListner = new View.OnClickListener() {
        
        @Override
        public void onClick(View v) {
			Intent i2 = new Intent(PhraseList.this,PhraseList.class);
			//i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			i2.putExtra("wordselect", wordselect);
			i2.putExtra("viewoption", "viewkorean");
			startActivity(i2);
        }
    };
    
    
    public View.OnClickListener addbuttonListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
     		Intent i = new Intent(PhraseList.this, PlusAddPhase.class);
     		i.putExtra("wordselect", wordselect);
     		i.putExtra("title", "");
     		i.putExtra("content", "");
     		i.putExtra("example", "");
     		i.putExtra("currentposition", -1);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        }
    };
    
    public View.OnClickListener delbuttonListner = new View.OnClickListener() {
        
        @Override
        public void onClick(View v) {
     		if(currentposition == -1) {
     			showToast("삭제할 숙어를 선택해 주세요.");
     			return;
     		}
     		
     		doExcelChange(currentposition);
     		showToast("삭제하였습니다.");
			Intent i2 = new Intent(PhraseList.this,PhraseList.class);
			//i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			i2.putExtra("wordselect", wordselect);
			i2.putExtra("viewoption", viewoption);
			startActivity(i2);
        }
    };
    
    
    public View.OnClickListener updbuttonListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
     		if(currentposition == -1) {
     			showToast("수정할 숙어를 선택해 주세요.");
     			return;
     		}
     		
        	
        	
     		Intent i = new Intent(PhraseList.this, PlusAddPhase.class);
     		i.putExtra("wordselect", wordselect);
     		i.putExtra("title", titlearraylist.get(currentposition));
     		i.putExtra("content", conarraylist.get(currentposition));
     		i.putExtra("example", phrasearrayexlist.get(currentposition));
     		i.putExtra("currentposition", currentposition);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        }
    };
    
    
    public void doExcelChange(int position) {
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
				if(i < position) {
					if(addcell.getCellFormat() != null) {
						jxl.write.Label label = new jxl.write.Label(0,i,content,addcell.getCellFormat());
						jxl.write.Label label1 = new jxl.write.Label(1,i,content1,addcell.getCellFormat());
						jxl.write.Label label2 = new jxl.write.Label(2,i,content1,addcell.getCellFormat());
						sheet.addCell(label);
						sheet.addCell(label1);
						sheet.addCell(label2);
					} else {
						jxl.write.Label label = new jxl.write.Label ( 0, i, content);
						jxl.write.Label label1 = new jxl.write.Label ( 1, i, content1 );
						jxl.write.Label label2 = new jxl.write.Label ( 1, i, content2 );
						sheet.addCell(label);
						sheet.addCell(label1);
						sheet.addCell(label2);
					}
				} else if(position == i) {
					if(i == rows -1) {
						sheet.removeRow(i);
					}
					continue;
				} else {
					if(addcell.getCellFormat() != null) {
						jxl.write.Label label = new jxl.write.Label(0,i-1,content,addcell.getCellFormat());
						jxl.write.Label label1 = new jxl.write.Label(1,i-1,content1,addcell.getCellFormat());
						jxl.write.Label label2 = new jxl.write.Label(2,i-1,content2,addcell.getCellFormat());
						sheet.addCell(label);
						sheet.addCell(label1);
						sheet.addCell(label2);
					} else {
						jxl.write.Label label = new jxl.write.Label ( 0, i-1, content);
						jxl.write.Label label1 = new jxl.write.Label ( 1, i-1, content1 );
						jxl.write.Label label2 = new jxl.write.Label ( 1, i-1, content2 );
						sheet.addCell(label);
						sheet.addCell(label1);
						sheet.addCell(label2);
					}
				}
				if(i == rows -1) {
					/*
					if(addcell.getCellFormat() != null) {
						jxl.write.Label label = new jxl.write.Label(0,i,"",addcell.getCellFormat());
						jxl.write.Label label1 = new jxl.write.Label(1,i,"",addcell.getCellFormat());
						sheet.addCell(label);
						sheet.addCell(label1);
					} else {
						jxl.write.Label label = new jxl.write.Label ( 0, i, "");
						jxl.write.Label label1 = new jxl.write.Label ( 1, i, "" );
						sheet.addCell(label);
						sheet.addCell(label1);
					}*/
					sheet.removeRow(i);
				}
				
			}
			

			
			
			copy.write ();
			copy.close ();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		switch(item.getItemId()){
			case VIEWALL :
				Intent i = new Intent(PhraseList.this,PhraseList.class);
				//i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				i.putExtra("wordselect", wordselect);
				i.putExtra("viewoption", "viewall");
				startActivity(i);
				return(true);
			case VIEWJAPAN :
				Intent i1 = new Intent(PhraseList.this,PhraseList.class);
				//i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				i1.putExtra("wordselect", wordselect);
				i1.putExtra("viewoption", "viewjapan");
				startActivity(i1);
				return(true);
			case VIEWKOREAN :
				Intent i2 = new Intent(PhraseList.this,PhraseList.class);
				//i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				i2.putExtra("wordselect", wordselect);
				i2.putExtra("viewoption", "viewkorean");
				startActivity(i2);
				return(true);
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent i = new Intent(PhraseList.this,WordSelectPhase.class);
			i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	/**
	 * A sample ListAdapter that presents content
	 * from arrays of speeches and text.
	 *
	 */
	private class SpeechListAdapter extends BaseAdapter {
	    public SpeechListAdapter(Context context)
	    {
	        mContext = context;
	    }
	
	    
	    /**
	     * The number of items in the list is determined by the number of speeches
	     * in our array.
	     * 
	     * @see android.widget.ListAdapter#getCount()
	     */
	    public int getCount() {
	        return mTitles.length;
	    }
	
	    /**
	     * Since the data comes from an array, just returning
	     * the index is sufficent to get at the data. If we
	     * were using a more complex data structure, we
	     * would return whatever object represents one 
	     * row in the list.
	     * 
	     * @see android.widget.ListAdapter#getItem(int)
	     */
	    public Object getItem(int position) {
	        return position;
	    }
	
	    /**
	     * Use the array index as a unique id.
	     * @see android.widget.ListAdapter#getItemId(int)
	     */
	    public long getItemId(int position) {
	        return position;
	    }
	
	    /**
	     * Make a SpeechView to hold each row.
	     * @see android.widget.ListAdapter#getView(int, android.view.View, android.view.ViewGroup)
	     */
	    public View getView(int position, View convertView, ViewGroup parent) {
	        SpeechView sv;
	        if (convertView == null) {
	            sv = new SpeechView(mContext, mTitles[position], mDialogue[position], mExpanded[position]);
	        } else {
	            sv = (SpeechView)convertView;
	            sv.setTitle(mTitles[position]);
	            sv.setDialogue(mDialogue[position]);
	            
	            if(currentposition == position) {
	            	sv.setBackground(Color.BLUE);
	            	if(viewoption == null || (!viewoption.equals("viewjapan") && !viewoption.equals("viewkorean"))) {
		            	mExpanded[position] = true;
		            	sv.setExpanded(mExpanded[position]);
	            	}
	            } else {
	            	mExpanded[position] = false;
	            	sv.setExpanded(mExpanded[position]);
	            	sv.setBackground(Color.WHITE);
	            }
	        }
	        sv.setBackgroundColor(Color.WHITE);
	        
	        return sv;
	    }
	
	    public void toggle(int position) {
	        mExpanded[position] = !mExpanded[position];
	        notifyDataSetChanged();
	    }
	    
	    public void refresh() {
	        notifyDataSetChanged();
	    }
	    
	    /**
	     * Remember our context so we can use it when constructing views.
	     */
	    private Context mContext;
	}
	
	/**
	 * We will use a SpeechView to display each speech. It's just a LinearLayout
	 * with two text fields.
	 *
	 */
	private class SpeechView extends LinearLayout {
	    public SpeechView(Context context, String title, String dialogue, boolean expanded) {
	        super(context);
	        
	        this.setOrientation(VERTICAL);
	        
	        // Here we build the child views in code. They could also have
	        // been specified in an XML file.
	        
	        mTitle = new TextView(context);
	        mTitle.setText(title);
	        mTitle.setTextSize(20);
	        mTitle.setTextColor(Color.BLACK);
	        //mTitle.setHeight(60);
	        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
	        params.setMargins(5, 20, 5, 20);
	        
	        addView(mTitle, params);
	        
	        mDialogue = new TextView(context);
	        mDialogue.setText(dialogue);
	        mDialogue.setBackgroundColor(Color.BLUE);
	        mDialogue.setTextColor(Color.WHITE);
	        mDialogue.setTextSize(15);
	        //mDialogue.setBackgroundColor(Color.LTGRAY);
	        addView(mDialogue, new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
	        
	        mDialogue.setVisibility(expanded ? VISIBLE : GONE);
	    }
	    
	    /**
	     * Convenience method to set the title of a SpeechView
	     */
	    public void setTitle(String title) {
	        mTitle.setText(title);
	    }
	    
	    /**
	     * Convenience method to set the dialogue of a SpeechView
	     */
	    public void setDialogue(String words) {
	        mDialogue.setText(words);
	    }
	    
	    /**
	     * Convenience method to expand or hide the dialogue
	     */
	    public void setExpanded(boolean expanded) {
	        mDialogue.setVisibility(expanded ? VISIBLE : GONE);
	    }
	    
	    public void setBackground(int color) {
	    	mTitle.setBackgroundColor(color);
	    }
	    private TextView mTitle;
	    private TextView mDialogue;
	}
}
