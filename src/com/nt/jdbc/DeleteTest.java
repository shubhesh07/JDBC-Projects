package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class DeleteTest {

	public static void main(String[] args) {
		Scanner sc=null;
		int no=0;
		Connection con=null;
		Statement st=null;
		String query=null;
		int  count=0;
		try {
			//read inputs
			sc=new Scanner(System.in);
			if(sc!=null) {
				//read inputs
				System.out.println("Enter student number::");
				no=sc.nextInt();
			}
			//register  JDBC driver  s/w
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//establish the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");
			//create  JDBC Statement object
			if(con!=null)
				st=con.createStatement();
			//prepare SQL query
			   //delete from student where sno=103
			   query="DELETE FROM STUDENT WHERE SNO="+no;
			   System.out.println(query);
			//send and execute SQL Query to DB s/w
			   if(st!=null)
				   count=st.executeUpdate(query);
			   //process the Reuslt
			   if(count==0)
				      System.out.println("record not found for deletion");
			   else
				   System.out.println("record  found for deletion");
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
