package com.nt.jdbc;

import java.sql.SQLException;

import javax.sql.rowset.CachedRowSet;

import oracle.jdbc.rowset.OracleCachedRowSet;

public class CachedRowSetDemo {

	public static void main(String[] args) {
		CachedRowSet crowset=null; 
		try {
			//Bean style programming
			crowset=new OracleCachedRowSet();
			crowset.setUrl("jdbc:oracle:thin:@localhost:1521:xe");
			crowset.setUsername("system");
			crowset.setPassword("manager");
			crowset.setCommand("SELECT * FROM STUDENT");
			//excute the query
			crowset.execute();
			//process the RowSet
			while(crowset.next()) {
				System.out.println(crowset.getInt(1)+"  "+crowset.getString(2)+"  "+crowset.getString(3)+"  "+crowset.getFloat(4));
			}
			
			System.out.println("keep the DB s/w in stopped mode");
			Thread.sleep(50000);
			//modify rowset object data
			crowset.absolute(4);
			crowset.updateString(3,"jojong");
			crowset.updateRow();
		
			
			System.out.println("keep the DB s/w in runnig mode");
			Thread.sleep(50000);
			crowset.acceptChanges();
			
			//process the RowSet
			while(crowset.next()) {
				System.out.println(crowset.getInt(1)+"  "+crowset.getString(2)+"  "+crowset.getString(3)+"  "+crowset.getFloat(4));
			}
			
			
		}//try
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			//close rowset
			try {
				if(crowset!=null)
					crowset.close();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
		}//finally
	}//main
}//class
