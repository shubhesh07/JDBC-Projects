package com.nt.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import oracle.jdbc.pool.OracleConnectionPoolDataSource;

public class PooledConnTest {

	public static void main(String[] args) {
		OracleConnectionPoolDataSource ds=null;
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
		// create Datasource obj
		ds=new OracleConnectionPoolDataSource();
		//give jdbc properties details to  DataSource obj to create jdbc con objs in the jdbc con pool
		ds.setDriverType("thin");
		ds.setURL("jdbc:oracle:thin:@localhost:1521:xe");
		ds.setUser("system");
		ds.setPassword("manager");
		//get jdbc con object from jdbc con pool
		if(ds!=null)
		  con=ds.getConnection();
		//create PreparedStatement object
		if(con!=null)
			ps=con.prepareStatement("SELECT * FROM STUDENT");
		//send and execute SQL Query to DB s/w
		if(ps!=null)
			rs=ps.executeQuery();
		//process the ResultSet obj
		if(rs!=null) {
			while(rs.next()) {
				System.out.println(rs.getInt(1)+" "+rs.getString(2)+"  "+rs.getString(3)+"  "+rs.getFloat(4));
			}//while
		}//if
		}//try
		catch(SQLException se) {
			se.printStackTrace();
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
				if(ds!=null)
					ds.close();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
		}//finally
	}//main
}//class
