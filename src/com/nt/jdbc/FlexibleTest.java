package com.nt.jdbc;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class FlexibleTest {
  private static final String  GET_STUDENTS="SELECT SNO,SNAME,SADD,AVG FROM STUDENT";
	public static void main(String[] args) {
		  InputStream is=null; 
		  Properties props=null;
		  Connection con=null;
		  Statement st=null;
		  ResultSet rs=null;
		try {
			//loacate properties file 
			is=new FileInputStream("src/com/nt/commons/jdbc.properties");
			//Load info into java.util.Properties obj from properties file
			props=new Properties();
			props.load(is);
			System.out.println(props);
			//register JDBC driver s/w
			Class.forName(props.getProperty("jdbc.driver"));
			//establish the connection
			con=DriverManager.getConnection(props.getProperty("jdbc.url"),
					                                                            props.getProperty("jdbc.user"),
					                                                            props.getProperty("jdbc.pwd"));
			//create Statement object
			if(con!=null)
			  st=con.createStatement();
			  //send and executeSQL Query to the DB s/w
			if(st!=null)
				rs=st.executeQuery(GET_STUDENTS);
			//process the ResultSet object
			if(rs!=null) {
				while(rs.next()) {
					 System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3)+"  "+rs.getFloat(4));
				}//while
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
				 if(st!=null)
					 st.close();
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
		}//finally
	}//main
}//class
