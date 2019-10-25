package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class ResultMetaDataApp {

	public static void main(String[] args) {
		Connection con=null;
	    DatabaseMetaData dbmd=null;
	    Statement st=null;
	    ResultSet rs=null;
	    ResultSetMetaData rsmd=null;
	    int colCnt=0;
		try {
			//register JDBC Driver s/w
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//establish the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system", "manager");
			//create JDBC Statement object
			if(con!=null)
				st=con.createStatement();
			//send and execute SQL Query in DB s/w
			if(st!=null) 
				rs=st.executeQuery("SELECT SNO,SNAME,SADD,AVG FROM STUDENT");
			//get ResultMetaDAta object
			rsmd=rs.getMetaData();
			//get colulmn count
			colCnt=rsmd.getColumnCount();
			//display col names
			if(rsmd!=null) {
			for(int i=1;i<=colCnt;++i) {
				System.out.print(rsmd.getColumnLabel(i)+"   ");
			}
			}
			System.out.println();
			//display col data types
			if(rsmd!=null) {
			for(int i=1;i<=colCnt;++i) {
				System.out.print(rsmd.getColumnTypeName(i)+"   ");
			}
			}
			System.out.println();
			//process the Result
			if(rs!=null) {
				while(rs.next()) {
					for(int i=1;i<=colCnt;++i) {
						System.out.print(rs.getString(i)+"  ");
					}//for
					System.out.println();
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
					 if(con!=null)
						 con.close();
				 }
				 catch(SQLException se) {
					 se.printStackTrace();
				 }
			 }//finally
	}//main
}//class
