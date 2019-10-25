package com.nt.jdbc;

import java.sql.SQLException;

import javax.sql.rowset.JdbcRowSet;

import oracle.jdbc.rowset.OracleJDBCRowSet;

public class JdbcRowSetDemo {

	public static void main(String[] args) {
		JdbcRowSet jrowset=null; 
		try {
			//Bean style programming
			jrowset=new OracleJDBCRowSet();
			jrowset.setUrl("jdbc:oracle:thin:@localhost:1521:xe");
			jrowset.setUsername("system");
			jrowset.setPassword("manager");
			jrowset.setCommand("SELECT * FROM STUDENT");
			//excute the query
			jrowset.execute();
			//process the RowSet
			while(jrowset.next()) {
				System.out.println(jrowset.getInt(1)+"  "+jrowset.getString(2)+"  "+jrowset.getString(3)+"  "+jrowset.getFloat(4));
			}
		}//try
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			//close rowset
			try {
				if(jrowset!=null)
					jrowset.close();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
		}//finally
	}//main
}//class
