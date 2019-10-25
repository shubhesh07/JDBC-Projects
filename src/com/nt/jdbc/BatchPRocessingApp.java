package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class BatchPRocessingApp {

	public static void main(String[] args) {
		Connection con=null;
		Statement st=null;
		int result[]=null;
		int sum=0;
		try {
			//register JDBC driver s/w
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//establish the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "manager");
			//create Statement object
			if(con!=null)
			  st=con.createStatement();
			//add queries to the batch
			if(st!=null) {
			st.addBatch("INSERT INTO STUDENT VALUES(9007,'ramesh','hyd',90.55f)");
			st.addBatch("UPDATE STUDENT SET SADD='vizag1' WHERE SNO>=2000");
			st.addBatch("DELETE FROM STUDENT WHERE SNO<=10");
			}
			//execute the Batch
			if(st!=null) {
				result=st.executeBatch();
			}
		   //process the result
			if(result!=null) {
				for(int i=0;i<result.length;++i)
					sum=sum+result[i];
			}
			
			System.out.println("no.of records that are effected::"+sum);
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
