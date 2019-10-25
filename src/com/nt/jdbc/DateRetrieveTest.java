package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

public class DateRetrieveTest {

	public static void main(String[] args) {
		Connection con=null;
		Statement st=null;
		ResultSet rs=null;
		SimpleDateFormat sdf=null;
		int pid=0;
		String pname=null;
		java.sql.Date sqdob=null,sqdoj=null,sqdom=null;
		java.util.Date  udob=null,udoj=null,udom=null;
		String   dob=null,doj=null,dom=null;
		try {
			//register JDBC driver 
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//establish the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");
			//create  Jdbc Statement object
			if(con!=null)
				st=con.createStatement();
			//send and execute SQL Query in DB s/w
			if(st!=null)
				rs=st.executeQuery("SELECT  PID,PNAME,DOB,DOJ,DOM FROM PERSON_DATE_TAB");
			//create SimpleDateFormat class obj having output date pattern
			sdf=new SimpleDateFormat("dd-MMM-yy");
			
			//process the ResultSet obj
			if(rs!=null) {
				while(rs.next()) {
					//System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getDate(3)+"  "+rs.getDate(4)+"  "+rs.getDate(5));
					pid=rs.getInt(1);
					pname=rs.getString(2);
					sqdob=rs.getDate(3);
					sqdoj=rs.getDate(4);
					sqdom=rs.getDate(5);
					//convert java.sql.Date class objs to java.util.Date class objs
					udob=(java.util.Date)sqdob;
					udom=(java.util.Date)sqdom;
					udoj=(java.util.Date)sqdoj;
					//convert java.util.Date class objs to String  date values
					dob=sdf.format(udob);
					dom=sdf.format(udom);
					doj=sdf.format(udoj);
					System.out.println(pid+"  "+pname+"  "+dob+"  "+dom+"  "+doj);
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
