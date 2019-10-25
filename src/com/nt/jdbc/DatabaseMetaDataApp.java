package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseMetaDataApp {

	public static void main(String[] args) {
		Connection con=null;
	    DatabaseMetaData dbmd=null;
		try {
			//register JDBC Driver s/w
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//establish the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system", "manager");
			//create DataBaseMetaData object
			if(con!=null)
				dbmd=con.getMetaData();
			//Get Info about DB s/w
			 if(dbmd!=null) {
				 System.out.println("db s/w name ::"+dbmd.getDatabaseProductName());
				 System.out.println("db s/w version::"+dbmd.getDatabaseProductVersion());
				 System.out.println("DB s/w major version::"+dbmd.getDatabaseMajorVersion());
				 System.out.println("Db s/w minor version::"+dbmd.getDatabaseMinorVersion());
				 System.out.println("JDBC driver s/w major version::"+dbmd.getDriverMajorVersion());
				 System.out.println("JDBC driver s/w minor version::"+dbmd.getDriverMinorVersion());
				 System.out.println("JDBC driver version::"+dbmd.getDriverVersion());
				 System.out.println(" JDBC drvier name ::"+dbmd.getDriverName());
				 System.out.println("Max connections to DB s/w::"+dbmd.getMaxConnections());
				 System.out.println("MAx chars in db table name::"+dbmd.getMaxTableNameLength());
				 System.out.println("max row size ::"+dbmd.getMaxRowSize());
				 System.out.println("Max Chars in col name::"+dbmd.getMaxColumnNameLength());
				 System.out.println(" supports Procedures??"+dbmd.supportsStoredProcedures());
			 }//if
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
					 if(con!=null)
						 con.close();
				 }
				 catch(SQLException se) {
					 se.printStackTrace();
				 }
			 }//finally
	}//main
}//class
