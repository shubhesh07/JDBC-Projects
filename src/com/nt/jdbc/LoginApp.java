package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/*SQL> create table userlist(uname varchar2(15) primary  key,pwd varchar2(15));

SQL> select * from  userlist;

UNAME           PWD
--------------- ---------------
raja            rani
rani            rani
jani            begum
king            kingdom

SQL> commit;
*/
public class LoginApp {

	public static void main(String[] args) {
		Scanner sc=null;
		String user=null;
		String pwd=null;
		Connection con=null;
		Statement st=null;
		String query=null;
		int count=0;
		ResultSet rs=null;
		try {
			//read inputs
			sc=new Scanner(System.in);
			if(sc!=null) {
				System.out.println("enter username::");
				user=sc.nextLine();   //gives raja
				System.out.println("enter password ::");
				pwd=sc.nextLine(); //gives rani
			}
			//convert input values as required for the SQL Query
			user="'"+user+"'"; //gives  'raja'
			pwd="'"+pwd+"'"; //gives  'rani'
			//register  JDBC driver s/w
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//establish the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");
			//create STatement object
			if(con!=null)
				st=con.createStatement();
			//prepare SQL Query 
			      //select count(*) from userlist where uname='raja' and pwd='rani';
			      query="SELECT COUNT(*) FROM USERLIST WHERE UNAME="+user+"AND PWD="+pwd;
			      System.out.println(query);
			   //send and execute  SQL Query
			      if(st!=null)
			    	  rs=st.executeQuery(query);
			      //process the ResultSet
			      if(rs!=null) {
			    	  rs.next();
			    	    count=rs.getInt(1);
			      }
			      
			      if(count==0)
			    	    System.out.println("INVALID CREDENTIALS");
			      else
			    	  System.out.println("VALID CREDENTIALS");
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
