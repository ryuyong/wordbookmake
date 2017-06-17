package com.word.wordbookmake;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Random;

public class Common {
	public String[] getArrayViewValue(String[] lists) {
		String[] rewordlists = new String[lists.length];
		for(int i=0;i<lists.length;i++) {
			String temp = lists[i];
			String[] temps = temp.split(":");
			rewordlists[i] = temps[0];
		}
		return rewordlists;
	}
	
	public String[] getArrayKeyValue(String[] lists) {
		String[] rewordlists = new String[lists.length];
		for(int i=0;i<lists.length;i++) {
			String temp = lists[i];
			String[] temps = temp.split(":");
			rewordlists[i] = temps[1];
		}
		return rewordlists;
	}
	
	public String getStringValue(String value, int index) {
		String resultvalue="";
		String[] temps = value.split(":");
		resultvalue = temps[index];
		return resultvalue;
	}
	
	public int getResourceId(String name) {
		//Raw 리소스 파일의 저장 （1）
        Integer id = null;
        Class classtest = com.word.wordbookmake.R.id.class;
        try {
			Field field = classtest.getDeclaredField(name);
			field.setAccessible(true);
			id = (Integer)field.get(name);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e1) {
			e1.printStackTrace();
		} catch (NoSuchFieldException e1) {
			e1.printStackTrace();
		}
		if(id == null) {
			return 0;
		} else {
			return id.intValue();
		}
	}
	
	
	// 난수 구하기
    private Random rand=new Random();
    public int rand(int num) {
        return (rand.nextInt()>>>1) %num;
    }
    
    public int randArray(int num, String val,ArrayList<String> arrayval, ArrayList<String> randtemp) {
    	boolean result = true;
    	int tempnum=0;
    	while(result) {
    		tempnum = (rand.nextInt()>>>1) %num;
			if(val.equals(arrayval.get(tempnum))) {
				continue;
			}
    		if(randtemp == null || randtemp.size() == 0) {
    			result = false;
    		} else {
    			int resultcount = 0;
    			for(int i=0;i<randtemp.size();i++) {
    				int value = Integer.parseInt(randtemp.get(i));
    				if(tempnum == value) {
    					resultcount++;
    				}
        			if(arrayval.get(Integer.parseInt(randtemp.get(i))).equals(arrayval.get(tempnum))) {
        				resultcount++;
        			}
    			}
    			if(resultcount == 0) {
    				result = false;
    			}
    		}
    	}
    	return tempnum;
        
    }
}
