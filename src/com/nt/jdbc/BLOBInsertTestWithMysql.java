package com.nt.jdbc;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

/*
 CREATE TABLE `ntaj412db`.`personall_info` (
  `pid` INT NOT NULL AUTO_INCREMENT,
  `pname` VARCHAR(20) NULL,
  `paddrs` VARCHAR(20) NULL,
  `photo` BLOB NULL,
  PRIMARY KEY (`pid`));
   
	*/


public class BLOBInsertTestWithMysql {
   private static final String INSERT_PERSON_QUERY="INSERT INTO PERSONALL_INFO(PNAME,PADDRS,PHOTO) VALUES(?,?,?)";
	public static void main(String[] args) {
		Scanner sc=null;
		String pname=null;
		String paddrs=null,photoPath=null;
		Connection con=null;
		PreparedStatement ps=null;
		File file=null;
		long length=0;
		InputStream is=null;
		int result=0;
	
		try {
			//read inputs
			sc=new Scanner(System.in);
			if(sc!=null) {
				System.out.println("enter Person name::");
				pname=sc.next();
				System.out.println("Enter PErson Address::");
				paddrs=sc.next();
				System.out.println("enter Person's  Photo Location::");
				photoPath=sc.next();
			}
			//register JDBC driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			//establish the connection
			con=DriverManager.getConnection("jdbc:mysql:///ntaj412db","root","root");
			//create PreparedStatement obj having pre-compiled SQL Query
			if(con!=null)
				ps=con.prepareStatement(INSERT_PERSON_QUERY);
			//create java.io.File class obj locating photograph file
			file=new File(photoPath);
			//get the length of the file..
			length=(int) file.length();
			//create InputStream locating the file..
			is=new FileInputStream(file);
			//set query param vlaues
			if(ps!=null) {
				ps.setString(1,pname);
				ps.setString(2,paddrs);
				//ps.setBinaryStream(3,is,(int)length/2);
				ps.setBlob(3,is);
			}
			//execute the Query
			if(ps!=null)
				result=ps.executeUpdate();
			//process the Result
			if(result==0)
				System.out.println("Record not inserted");
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
				if(is!=null)
					is.close();
			}
			catch(IOException ioe) {
				ioe.printStackTrace();
			}
			
		}//finally

	}//main
}//class
