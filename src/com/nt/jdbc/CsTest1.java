package com.nt.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Scanner;

/*create or  replace procedure p_first(x in number, y out number,z out number)as 
begin 
   y:=x*x;
  z:=x*x*x;
end;
/
*/

public class CsTest1 {
  private static final String  CALL_PROCEDURE="{ CALL P_FIRST(?,?,?) }";
	public static void main(String[] args) {
		Scanner sc=null;
		int no=0;
		Connection con=null;
		CallableStatement cs=null;
		try {
			//read inputs
			sc=new Scanner(System.in);
			if(sc!=null) {
				System.out.println("enter value::");
				no=sc.nextInt();
			}
			//register JDBC driver s/w
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//establish the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");
			//create CallableStatement object
			if(con!=null)
				cs=con.prepareCall(CALL_PROCEDURE);
			//register Outparams with  JDBC data types
			if(cs!=null) {
				cs.registerOutParameter(2,Types.INTEGER);
				cs.registerOutParameter(3,Types.INTEGER);
			}
			//set values to IN params
			if(cs!=null)
				cs.setInt(1,no);
			//call/execute PL/SQL procedure
			if(cs!=null)
				cs.execute();
			//gather results from OutParams
			if(cs!=null) {
				System.out.println("Square :::"+cs.getInt(2));
				System.out.println("Cube :::"+cs.getInt(3));
			}
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
				if(cs!=null)
					cs.close();
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
