package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class BatchPRocessingAppWithTx {

	public static void main(String[] args) {
		Connection con = null;
		Statement st = null;
		int result[] = null;
		boolean flag=false;
		try {
			// register JDBC driver s/w
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// establish the connection
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "manager");
			
			// create Statement object
			if (con != null)
				st = con.createStatement();
			
			// begin Tx By disabling autoCommit
			if (con != null)
				con.setAutoCommit(false);

			// add queries to the batch
			if (st != null) {
				st.addBatch("INSERT INTO STUDENT VALUES(9010,'ramesh','hyd',90.55f)");
				st.addBatch("UPDATE STUDENT SET SADD='vizag6' WHERE SNO>=2000");
				st.addBatch("DELETE FROM STUDENT WHERE SNO<=25");
			}
			
			// execute the Batch  (Execute logics in Tx)
			if (st != null) {
				result = st.executeBatch();
			}
			
			//perform TxMgmt
			for(int i=0;i<result.length;++i) {
				if(result[i]==0) {
					flag=true;
					break;
				}
			}
			
		
		} // try
		catch (SQLException se) {
			 flag=false;
			se.printStackTrace();
		} catch (ClassNotFoundException cnf) {
			cnf.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
			//perfom Tx mgmt
			if(flag) {
				con.rollback();
				System.out.println("Tx rolledBack");
			}
			else {
				con.commit();
				System.out.println("Tx Committed");
			}
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
			
			// close jdbc objs
			try {
				if (st != null)
					st.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
			try {
				if (con != null)
					con.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		} // finally

	}// main
}// class
