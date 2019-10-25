package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class UpdateTest {

	public static void main(String[] args) {
		Scanner  sc=null;
		int no=0;
		String newName=null,newAddrs=null;
		float newAvg=0.0f;
		Connection con=null;
		PreparedStatement ps=null;
		String query=null;
		int count=0;
		try {
			//read inputs
			/*
			 * sc=new Scanner(System.in); if(sc!=null) {
			 * System.out.println("Enter existing student number::"); no=sc.nextInt();
			 * //gives 901 System.out.println("Enter  student name ::"); newName=sc.next();
			 * //gives raja System.out.println("Enter student address:");
			 * newAddrs=sc.next(); // hyd System.out.println("Enter student marks avg::");
			 * newAvg=sc.nextFloat(); //90.33
			 */ /*
				 * } //convert input values as required for the SQL Query
				 * newName="'"+newName+"'"; //gives 'newraja' newAddrs="'"+newAddrs+"'"; //gives
				 * 'newhyd'
				 */			   //register JDBC driver s/w
			   Class.forName("oracle.jdbc.driver.OracleDriver");
			   //establish the connection
			   con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");
			   //prepare SQL Query
			     query="update student set sname='new raja',sadd='new hyd',avg=99.99 where sno=101";
			   //query="UPDATE STUDENT SET SNAME="+newName+",SADD="+newAddrs+",AVG="+newAvg+"WHERE SNO="+no;
			   System.out.println(query);
			   //create Jdbc PreparedSTatement object
			   if(con!=null)
				   ps=con.prepareStatement(query);
			   //send and execute SQL Query in DB s/w
			   if(ps!=null)
				   count=ps.executeUpdate();
			   //process the result
			   if(count==0)
				   System.out.println("record not updated");
			   else
				   System.out.println("record updated");
		}//try
		catch(SQLException se) {
			System.out.println("record not inserted");
			if(se.getErrorCode()==12899)
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
