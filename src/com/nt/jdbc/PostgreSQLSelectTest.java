package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PostgreSQLSelectTest {

	public static void main(String[] args) {
	   Connection con=null;
	   Statement st=null;
	   ResultSet rs=null;
	   boolean flag=false;
		try {
			//register  JDBC dirver s/w
			//Class.forName("org.postgresql.Driver");
			//establish the connection
			//con=DriverManager.getConnection("jdbc:postgresql:ntaj412DB","postgres","root");
			con=DriverManager.getConnection("jdbc:postgresql://localhost:5432/ntaj412DB","postgres","root");
			//create JDBC statement object
			if(con!=null)
				st=con.createStatement();
			//send and execute SQL Query in DB s/w
			if(st!=null)
				rs=st.executeQuery("SELECT PID,PNAME,PRICE,QTY FROM PRODUCT");
			//gather and process the Results
			if(rs!=null) {
				while(rs.next()) {
					flag=true;
					System.out.println(rs.getInt(1)+" "+rs.getString(2)+"  "+rs.getFloat(3)+" "+rs.getFloat(4));
				}//while
				if(!flag)
					System.out.println("records not found");
				else
					System.out.println("records  found  and displayed");
			}//if
		}//try
		catch(SQLException se) {
			se.printStackTrace();
		}
		/*catch(ClassNotFoundException cnf) {
			cnf.printStackTrace();
		}*/
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
