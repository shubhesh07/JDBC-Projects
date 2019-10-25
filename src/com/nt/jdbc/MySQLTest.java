package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQLTest {
    private static final String  SELECT_PRODUCT_QUERY="SELECT PID,PNAME,PRICE,QTY  FROM PRODUCT";
	public static void main(String[] args) {
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			//register  JDBC driver 
			//Class.forName("com.mysql.cj.jdbc.Driver");
			//establish the connection
			//con=DriverManager.getConnection("jdbc:mysql:///ntaj412db","root","root");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/ntaj412db1","root","root");
			//create PreparedStatement object
			if(con!=null)
				ps=con.prepareStatement(SELECT_PRODUCT_QUERY);
			//execute SQL Query in DB s/w
			if(ps!=null)
				rs=ps.executeQuery();
			//process the ReusltSet obj
			if(rs!=null) {
				while(rs.next()) {
					System.out.println(rs.getInt(1)+" "+rs.getString(2)+"  "+rs.getFloat(3)+"  "+rs.getFloat(4));
				}//while
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
		}
	}//main
}//class
