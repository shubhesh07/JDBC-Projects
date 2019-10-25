package com.nt.jdbc;

/*  JDBC App to get student detals based  given initial chars of
 *  student name 
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class SelectTest5 {

	public static void main(String[] args) {
		System.out.println("SelectTest5.main()");
		Scanner sc=null;
		 String initChars=null;
		 Connection con=null;
		 Statement st=null;
		 String query=null;
		 ResultSet rs=null;
		 boolean flag=false;
		try {
			//read inputs
			sc=new Scanner(System.in);
			if(sc!=null) {
				System.out.println("Enter initial chars of student name::");
			   initChars=sc.next();  //gives sa
			}
			//convert input  value as required for the  SQL query
			initChars="'"+initChars+"%'"; // gives 'sa%' 
			//regsiter JDBC driver s/w
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//establish the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");
			//create  JDBC Statement object
			if(con!=null)
				st=con.createStatement();
			//prepare SQL query
			     //select * from student where sname like 'ram%'
			   query="SELECT SNO,SNAME,SADD,AVG FROM STUDENT WHERE SNAME LIKE"+initChars;
			   System.out.println(query);
			 //send and execute SQL query in DB s/w
			   if(st!=null)
				   rs=st.executeQuery(query);
			   //process the ResultSet obj
			   if(rs!=null) {
				   while(rs.next()) {
					   flag=true;
					   System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3)+"  "+rs.getFloat(4));
				   }//while
			   }//if
			   if(flag)
				   System.out.println("Records found and displayed");
			   else
				   System.out.println("Records not found ");
			   
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
