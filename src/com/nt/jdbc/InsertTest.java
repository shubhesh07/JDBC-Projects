package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class InsertTest {

	public static void main(String[] args) {
		Scanner  sc=null;
		int no=0;
		String name=null,addrs=null;
		float avg=0.0f;
		Connection con=null;
		Statement st=null;
		String query=null;
		int count=0;
		try {
			//read inputs
			sc=new Scanner(System.in);
			 if(sc!=null) {
				 System.out.println("Enter student number::");
				 no=sc.nextInt(); //gives 901
				 System.out.println("Enter  student name ::");
				 name=sc.next();  //gives raja
				 System.out.println("Enter student address:");
				 addrs=sc.next(); // hyd
				 System.out.println("Enter student marks avg::");
				 avg=sc.nextFloat();  //90.33
			 }
			 //convert input values as required for the SQL Query
			   name="'"+name+"'";  //gives 'raja'
			   addrs="'"+addrs+"'"; //gives  'hyd'
			   //register JDBC driver s/w
			   Class.forName("oracle.jdbc.driver.OracleDriver");
			   //establish the connection
			   con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");
			   //create Jdbc STatement object
			   if(con!=null)
				   st=con.createStatement();
			   //prepare SQL Query
			              //insert into student values(1001,'raja','hyd',90.44)
			       query="INSERT  STUDENT VALUES("+no+","+name+","+addrs+","+avg+")";
			      System.out.println(query);
			   //send and execute SQL Query in DB s/w
			   if(st!=null)
				   count=st.executeUpdate(query);
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
