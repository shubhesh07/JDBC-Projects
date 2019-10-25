package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class LoginAppWithPS {
  private static final String  AUTH_QUERY="SELECT COUNT(*) FROM USERLIST WHERE UNAME=? AND PWD=?";
	public static void main(String[] args) {
		Scanner sc=null;
		String user=null,pass=null;
		Connection con=null;
		PreparedStatement ps=null;
		int result=0;
		ResultSet rs=null;
		int  count=0;
		try {
			//read inputs
			 sc=new Scanner(System.in);
			 if(sc!=null) {
				 System.out.println("Enter username::");
				 user=sc.nextLine();  //gives raja
				 System.out.println("enter Password ::");
				 pass=sc.nextLine();  //gives rani
			 }
			 //register JDBC driver s/w
			 Class.forName("oracle.jdbc.driver.OracleDriver");
			 //establish the conection
			  con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");
			  //create  PreparedStatement object
			  if(con!=null)
				  ps=con.prepareStatement(AUTH_QUERY);
			  //set input values to Query params
			  if(ps!=null) {
				  ps.setString(1, "USERLIST");
				  ps.setString(2,user);
				  ps.setString(3,pass);
			  }
			  //execute the SQL Query
			  if(ps!=null)
				  rs=ps.executeQuery();
			  
			   if(rs!=null) {
				   rs.next();
				   count=rs.getInt(1);
			   }
			   
			   //process the result
			   if(count==0)
				    System.out.println("InValid Credentials");
			   else
				   System.out.println("Valid Credentials");
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
			
		}//finally

	}//main
}//class
