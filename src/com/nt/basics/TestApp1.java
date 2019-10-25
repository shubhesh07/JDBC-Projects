package com.nt.basics;


public class TestApp1 {

	public static void main(String[] args) {
		java.util.Date date=null;
		java.sql.Date date1=null;
		long ms=0;
		date=new java.util.Date();
		System.out.println("util date::"+date);
		
		ms=date.getTime(); //gives  ms elapsed from  jan 1st 1970 midnight 00:00 hrs
		date1=new java.sql.Date(ms);
		System.out.println("sql date::"+date1);
		
		
		
		
		
	}

}
