package com.nt.jdbc;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import org.apache.commons.io.IOUtils;

public class CLOBRetrieveTestWithIOUtils {
   private static final String  CLOB_RETRIEVE_BY_ID="SELECT JSID,JSNAME,JSQLFY,JSRESUME FROM NAUKRI_JOBSEEKER_INFO WHERE JSID=?";
	public static void main(String[] args) {
		Scanner sc=null;
		int jsId=0;
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		String jsName=null,jsQlfy=null;
		Reader reader=null;
		Writer writer=null;
	
		
		
		try {
			//read inputs
			sc=new Scanner(System.in);
			if(sc!=null) {
				System.out.println("enter JobSeekerId::");
				jsId=sc.nextInt();
			}
			/*//register JDBC driver s/w
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//estalish the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");*/
			
			//register JDBC driver s/w
			Class.forName("com.mysql.cj.jdbc.Driver");
			//estalish the connection
			con=DriverManager.getConnection("jdbc:mysql:///ntaj412db","root","root");
			
			
			//create PreparedStatement object
			if(con!=null)
				ps=con.prepareStatement(CLOB_RETRIEVE_BY_ID);
			//set values to query params
			if(ps!=null) 
			   ps.setInt(1, jsId);	
			//execute the Query
			if(ps!=null)
				 rs=ps.executeQuery();
			//process the ResultSet
			if(rs!=null) {
			 if(rs.next()) {
				jsId=rs.getInt(1);
				jsName=rs.getString(2);
				jsQlfy=rs.getString(3);
				System.out.println(jsId+"  "+jsName+"   "+jsQlfy);
				reader=rs.getCharacterStream(4);
				//create Writer stream pointing to empty destination file
				writer=new  FileWriter("new_resume.txt");
				if(reader!=null && writer!=null) {
				      IOUtils.copy(reader,writer);
					System.out.println("resume is retrieved to  new_resume.txt file");
				}//if
			 }//if
			 else {
				 System.out.println("record not found");
			 }
			}//if
			 
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
				if(rs!=null)
					rs.close();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
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
			try {
				if(writer!=null)
					writer.close();
			}
			catch(IOException ioe) {
				ioe.printStackTrace();
			}
		}//finally
	}//main
}//class
