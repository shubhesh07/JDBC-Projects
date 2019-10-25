package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;
import java.util.Scanner;

public class InsertTestWithAutoSnoFromJavaCode {
   private static final String INSERT_QUERY="INSERT INTO STUDENT VALUES(?,?,?,?)";
	public static void main(String[] args) {
		Scanner  sc=null;
		String name=null,addrs=null;
		float avg=0.0f;
		Connection con=null;
		PreparedStatement ps=null;
		String query=null;
		int count=0;
		try {
			//read inputs
			sc=new Scanner(System.in);
			 if(sc!=null) {
				 System.out.println("Enter  student name ::");
				 name=sc.next();  //gives raja
				 System.out.println("Enter student address:");
				 addrs=sc.next(); // hyd
				 System.out.println("Enter student marks avg::");
				 avg=sc.nextFloat();  //90.33
			 }
			/*	   //register JDBC driver s/w
				   Class.forName("oracle.jdbc.driver.OracleDriver");
				   //establish the connection
				   con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");
			*/
			   //register JDBC driver s/w
			   Class.forName("com.mysql.cj.jdbc.Driver");
			   //establish the connection
			   con=DriverManager.getConnection("jdbc:mysql:///ntaj412db","root","root");
			 //create Jdbc STatement object
			   if(con!=null)
				   ps=con.prepareStatement(INSERT_QUERY);
			   //set query param values
			   if(ps!=null) {
				   ps.setInt(1,new Random().nextInt(10000));
				   ps.setString(2,name);
				   ps.setString(3,addrs);
				   ps.setFloat(4, avg);
			   }
			   //send and execute SQL Query in DB s/w
			   if(ps!=null)
				   count=ps.executeUpdate();
			   //process the result
			   if(count==0)
				   System.out.println("record not inserted");
			   else
				   System.out.println("record inserted");
		}//try
		catch(SQLException se) {
			System.out.println("record not inserted");
			if(se.getErrorCode()==1)
				System.out.println("duplicates can not be inserted to sno column");
			else if(se.getErrorCode()==12899)
				System.out.println("values are larger than col size");
			else if(se.getErrorCode()>=900 && se.getErrorCode()<=1000)
				System.out.println("SQL Query  Syntax problem"); 
				
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
		}//finally
	}//main
}//class
