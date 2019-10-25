package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UpdatableScrollableRsTest {
   private static final String  GET_STUDENTS="SELECT  SNO,SNAME,SADD,AVG  FROM STUDENT";
	public static void main(String[] args) {
		Connection con=null;
		Statement st=null;
		ResultSet rs=null;
		try {
			//register JDBC driver s/w
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//establish the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system", "manager");
			//create JDBC Statement object
			if(con!=null)
				st=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
						                                        ResultSet.CONCUR_UPDATABLE); 
				
			//send and execute SQL Query in Db s/w
			if(st!=null)
				rs=st.executeQuery(GET_STUDENTS);
			//process the ReusltSEt
			if(rs!=null) {
				System.out.println("Records (Top-Bottom)");
				while(rs.next()) {
					System.out.println(rs.getInt(1)+"  "+rs.getString(2)+" "+rs.getString(3)+"  "+rs.getFloat(4));
				}
				/*//insert operation
				rs.moveToInsertRow();
				rs.updateInt(1,9988);
				rs.updateString(2,"rajesh");
				rs.updateString(3,"chennai");
				rs.updateFloat(4,89.55f);
				rs.insertRow();
				System.out.println("record inserted");*/
				
				//modify operation
				/*	rs.absolute(5);
					rs.updateString(3,"kolkata");
					rs.updateRow();
					System.out.println("record updated..");
					*/
				//delete operation
				  rs.absolute(3);
				  rs.deleteRow();
				
				
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
