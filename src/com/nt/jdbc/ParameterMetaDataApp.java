package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ParameterMetaDataApp {
   private static final String  INSERT_QUERY="INSERT INTO STUDENT VALUES(?,?,?,?)";
	public static void main(String[] args) {
	   Connection con=null;	
	   PreparedStatement ps=null;
	   ParameterMetaData pmd=null;
	   int  count=0;
		try {
		//register JDBC driver s/w
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//establish the connection
		   con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");
		   //create PreparedStatement object
		   if(con!=null)
			   ps=con.prepareStatement(INSERT_QUERY);
		   //create ParameterMetaData object
		   if(ps!=null)
			    pmd=ps.getParameterMetaData();
		   //display info about parameters(?)
		   count=pmd.getParameterCount();
		   //display details about parameters
		    for(int i=1;i<=count;++i) {
		    	System.out.println("param mode::"+pmd.getParameterMode(i));
		    	System.out.println("param type::"+pmd.getParameterTypeName(i));
		    	System.out.println("signed ?"+pmd.isSigned(i));
		    	System.out.println("nullable ?"+pmd.isNullable(i));
		    	System.out.println("scale "+pmd.getScale(i));
		    	System.out.println("precision"+pmd.getPrecision(i));
		    }//for
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
	}//finally
	}//main
}//class
