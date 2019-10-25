package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*  App to get employee details whose is having highest salary  
 * 
 */
public class SelectTest6 {

	public static void main(String[] args) {
		Connection con=null;
		Statement st=null;
		ResultSet rs=null;
		try {
			//register JDBC driver 
			 Class.forName("oracle.jdbc.driver.OracleDriver");
			 //establish  the connection
			 con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system","manager");
			 //create Statement object
			 if(con!=null)
				 st=con.createStatement();
			 //send and execute SQL Query in DB s/w
			 if(st!=null) 
				 rs=st.executeQuery("SELECT EMPNO,ENAME,JOB,SAL FROM EMP WHERE SAL=(SELECT MAX(SAL) FROM EMP )");
			 //process the ResultSet object
			 if(rs!=null) {
				if(rs.next()) {
					 System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3)+"  "+rs.getFloat(4));
				 }//if
				else {
					System.out.println("DB Table is empty ");
				}
			 }//if
			 
		}//try
		catch(SQLException se) {
			 if(se.getErrorCode()==942)
				 System.out.println("db table not found");
			 else if(se.getErrorCode()==904)
				 System.out.println("given db table cols not available");
			 else if(se.getErrorCode()==936)
				 System.out.println("Query Syntax error");
			 else
				 System.out.println("Internal problem");
			//se.printStackTrace();
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
