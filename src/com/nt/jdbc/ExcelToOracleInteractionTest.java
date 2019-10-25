package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ExcelToOracleInteractionTest {
   private static final String  EXCEL_SELECT_QUERY="SELECT PID,PNAME,PRICE,QTY FROM  college.Sheet2";
   private static  final  String ORA_INSERT_QUERY="INSERT INTO PRODUCT VALUES(?,?,?,?)";
	public static void main(String[] args) {
		Connection oraCon=null,excelCon=null;
		Statement st=null;
		PreparedStatement  ps=null;
		ResultSet rs=null;
		int id=0;
		String name=null;
		float price=0.0f,qty=0.0f; 
		try {
			//register JDBC driver s/ws
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Class.forName("com.hxtt.sql.excel.ExcelDriver");
			//establish the connections
			oraCon=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");
			excelCon=DriverManager.getConnection("jdbc:Excel:///F:\\Worskpaces\\advjava\\NTAJ412");
			//create JDBC Statement objects
			if(excelCon!=null)
				st=excelCon.createStatement();
			if(oraCon!=null)
				ps=oraCon.prepareStatement(ORA_INSERT_QUERY);
			
			//execute Query on Excel Sheet
			if(st!=null) 
				  rs=st.executeQuery(EXCEL_SELECT_QUERY);
			
			//copy records from ResutSet (ExcelSheet) to Oracle Db table 
			if(ps!=null && rs!=null) {
				while(rs.next()) {
				   //get each record from ResultSet object
					id=rs.getInt(1);
					name=rs.getString(2);
					price=rs.getFloat(3);
					qty=rs.getFloat(4);
					//set the above recieved values to insert Query param values
					ps.setInt(1,id);
					ps.setString(2,name);
					ps.setFloat(3,price);
					ps.setFloat(4,qty);
					//execute the Query
					ps.executeUpdate();
				}//while
				System.out.println("Records are copied from Excel Sheet to Oracle table");
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
				if(st!=null)
					st.close();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
			try {
				if(excelCon!=null)
					excelCon.close();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
			try {
				if(oraCon!=null)
					oraCon.close();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
		}//finally
	}//main
}//class
