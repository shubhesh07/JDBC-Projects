package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class DateInsertTestWithMysql {
   private static final String  INSERT_QUERY="INSERT INTO PERSON_DATE_TAB VALUES(?,?,?,?,?)";
	public static void main(String[] args) {
		Scanner sc=null;
		int pid=0;
		String pname=null;
		String dob=null,dom=null,doj=null;
		java.util.Date udob=null,udoj=null;
		SimpleDateFormat sdf1=null,sdf2=null;
		java.sql.Date sqdob=null,sqdoj=null,sqdom=null;
		Connection con=null;
		PreparedStatement ps=null;
		int count=0;
		try {
			//read inputs
			sc=new Scanner(System.in);
			if(sc!=null) {
				System.out.println("Enter Person Id:: ");
				pid=sc.nextInt();
				System.out.println("Enter Person Name::");
				pname=sc.next();
				System.out.println("Enter Person DOB (dd-MM-yyyy)");
				dob=sc.next();
				System.out.println("Enter Person DOJ (MM-dd-yyyy)");
				doj=sc.next();
				System.out.println("Enter Person DOM (yyyy-MM-dd)");
				dom=sc.next();
			}
			//convert DOB,DOJ to java.util.Date class objs
			  //For DOB
			sdf1=new SimpleDateFormat("dd-MM-yyyy");
			udob=sdf1.parse(dob);
			// For DOJ
			sdf2=new SimpleDateFormat("MM-dd-yyyy");
			udoj=sdf2.parse(doj);
			//Convert  java.util.Date class objs to  java.sql.Date class objects
			sqdob=new java.sql.Date(udob.getTime());
			sqdoj=new java.sql.Date(udoj.getTime());
			//Convert  DOM to directly java.sql.class obj using valueOf(-) method
			sqdom=java.sql.Date.valueOf(dom);
			
			//register  JDBC Driver s/w
			Class.forName("com.mysql.cj.jdbc.Driver");
			//establish the connection
			con=DriverManager.getConnection("jdbc:mysql:///ntaj412db","root","root");
			//create PreparedStatement object
			if(con!=null) 
				ps=con.prepareStatement(INSERT_QUERY);
			//set values to query params
			if(ps!=null) {
				ps.setInt(1,pid);
				ps.setString(2,pname);
				ps.setDate(3,sqdob);
				ps.setDate(4,sqdoj);
				ps.setDate(5,sqdom);
			}
			//execute the SQL Query
			if(ps!=null) 
				count=ps.executeUpdate();
			//process the Reuslt
			if(count==0)
				System.out.println("record not inserted");
			else
				System.out.println("record inserted");
		}//try
		catch(ClassNotFoundException cnf) {
			cnf.printStackTrace();
		}
		catch(SQLException se) {
			se.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			//close jdbc objs
			try {
				if(ps!=null)
					ps.close();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
			try {
				if(con!=null)
					con.close();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
			
			try {
				if(sc!=null)
					sc.close();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			
		}//finally

	}//main
}//class
