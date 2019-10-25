package com.nt.basics;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class TestApp2 {

	public static void main(String[] args)throws Exception {
		 
		//Converting String date value to java.util.Date class object
		 String s1="34-22-1947"; //dd-MM-yyyy
		 SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
		 java.util.Date ud1=sdf.parse(s1);
		 System.out.println("util date::"+ud1);
		 
		 // converting java.util.DAte class obj to java.sql.DAte class object
		 long ms=ud1.getTime();
		 System.out.println("ms=="+ms);
		java.sql.Date sqd1=new java.sql.Date(ms);
		System.out.println("sql date:::"+sqd1);
		
		// we can convert String date value to directly to  java.sql.Date value
		// if  that String date value is available in yyyy-MM-dd pattern.
		String s2="2010-10-30";   //yyyy-MM-dd
		java.sql.Date sqd2=java.sql.Date.valueOf(s2);
		System.out.println("sql date2::"+sqd2);
		
		//converting  java.util.Date class obj to java.sql.Date class object
		java.util.Date  ud2=sqd2;
		System.out.println("util date:::"+ud2);
		//Converting  java.util.Date class obj to String date value
		SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-dd-MMM");
		String s3=sdf1.format(ud2);
		System.out.println("string date value::"+s3);
		
		
		
		
		
		
		
		
		
		
	}

}
