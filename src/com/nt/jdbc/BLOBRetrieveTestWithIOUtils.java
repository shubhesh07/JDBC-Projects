package com.nt.jdbc;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import org.apache.commons.io.IOUtils;

public class BLOBRetrieveTestWithIOUtils {
   private static final String BLOB_RETRIEVE="SELECT PID,PNAME,PADDRS,PHOTO FROM  PERSONALL_INFO WHERE PID=?";
	public static void main(String[] args) {
		Scanner sc=null;
		int pid=0;
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		InputStream is=null; 
		OutputStream os=null;
		try {
			//read inputs
			sc=new Scanner(System.in);
			if(sc!=null) {
				System.out.println("Enter Person Id::");
				pid=sc.nextInt();
			}
			//register  JDBC driver s/w
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//establish the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system", "manager");
			//create PreparedStatement object 
			if(con!=null)
				ps=con.prepareStatement(BLOB_RETRIEVE);
			//set vlaues query params
			if(ps!=null)
				ps.setInt(1,pid);
			//execute the Query
			if(ps!=null) 
				rs=ps.executeQuery();
			//process the ResultSet and get InputSTream having blob value..
			if(rs.next()) {
				System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));
				is=rs.getBinaryStream(4);
				//create OutputStream pointing to dest file
				os=new FileOutputStream("new_image.jpg");
				//write buffer based stream logic to write content to dest file
				 if(os!=null && is!=null) {
                        IOUtils.copy(is, os);
                        System.out.println("BLOB vlaue retrieved to  new_image.jpg file");
					 }//if
			}//if
			else {
				System.out.println("record not found");
			}
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
			//close jdbc & stream objs
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
				if(is!=null)
					is.close();
			}
			catch(IOException ioe) {
				ioe.printStackTrace();
			}
			
			try {
				if(os!=null)
					os.close();
			}
			catch(IOException ioe) {
				ioe.printStackTrace();
			}
			
		}//finally
	}//main
}//class
