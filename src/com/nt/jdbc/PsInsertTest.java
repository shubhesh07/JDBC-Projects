package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class PsInsertTest {
	private static final String  INSERT_QUERY="INSERT INTO STUDENT VALUES(?,?,?,?)";

	public static void main(String[] args) {
		Scanner sc=null;
		int count=0;
		Connection con=null;
		PreparedStatement ps=null;
		int no=0;
		String name=null,addrs=null;
		float avg=0.0f;
		int result=0;
		try {
			//read students count
			sc=new Scanner(System.in);
			if(sc!=null) {
				System.out.println("Enter Students count::");
				count=sc.nextInt();
			}
			//register JDBC driver s/w
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//establish the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");
			//create PreparedStatement by making the SQL Query as pre-compiled  SQL Query
			if(con!=null)
				ps=con.prepareStatement(INSERT_QUERY);
			if(ps!=null && sc!=null) {
				for(int i=1;i<=count;++i) {
					//get each Student details
					System.out.println("Enter "+i+"student  details");
					System.out.println("Enter stduent number::");
					no=sc.nextInt();
					System.out.println("Enter Student name::");
					name=sc.next();
					System.out.println("Enter  student address::");
					addrs=sc.next();
					System.out.println("Enter student marks avg::");
					avg=sc.nextFloat();
					//set each student details to query params
					ps.setInt(1,no);
					ps.setString(2,name);
					ps.setString(3,addrs);
					ps.setFloat(4, avg);
					//execute the query
					result=ps.executeUpdate();
					//process the reuslt
					if(result==0)
						System.out.println(i+" record is not inserted");
					else
						System.out.println(i+" record is inserted");
				}//for
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
