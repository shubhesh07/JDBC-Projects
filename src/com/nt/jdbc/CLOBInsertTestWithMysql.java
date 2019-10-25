package com.nt.jdbc;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class CLOBInsertTestWithMysql {
    private static final String   CLOB_INSERT="INSERT INTO NAUKRI_JOBSEEKER_INFO(JSNAME,JSQLFY,JSRESUME) VALUES(?,?,?)";
	public static void main(String[] args) {
		Scanner sc=null;
		String jsName=null,jsQlfy=null,resumePath=null;
		Connection con=null;
		PreparedStatement ps=null;
		File file=null;
		Reader reader=null;
		long length=0;
		int result=0;
		try {
			//read inputs
			sc=new Scanner(System.in);
			if(sc!=null) {
				System.out.println("Enter JobSeekerName::");
				jsName=sc.next();
				System.out.println("Enter JobSeekerQualification::");
				jsQlfy=sc.next();
				System.out.println("Enter JobSeeker resumePath::");
				resumePath=sc.next();
			}//if
			//register JDBC driver s/w
			Class.forName("com.mysql.cj.jdbc.Driver");
			//establish the connection
			con=DriverManager.getConnection("jdbc:mysql:///ntaj412db", "root","root");
			//create PreparedStatement object
			if(con!=null)
				ps=con.prepareStatement(CLOB_INSERT);
			//Locate resume file based on its given path
			   file=new File(resumePath);
			   //get the lenght of the file..
			   length=file.length();
			   //create Reader stream pointing to the file
			   reader=new FileReader(file);
			   
			   //set vlaues to Query params
			   if(ps!=null) {
				   ps.setString(1,jsName);
				   ps.setString(2,jsQlfy);
				   ps.setCharacterStream(3, reader,length);
			   }
			   //execute  the Query
			   if(ps!=null) 
				   result=ps.executeUpdate();
			   //process the Result
			   if(result==0)
				    System.out.println("record not inserted");
			   else
				   System.out.println("record inserted");
		}//try
		catch(SQLException se) {
			se.printStackTrace();
		}
		catch(ClassNotFoundException cnf) {
			cnf.printStackTrace();
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
			try {
				if(reader!=null)
					reader.close();
			}
			catch(IOException ioe) {
				ioe.printStackTrace();
			}
		}//finally

	}//main

}//class
