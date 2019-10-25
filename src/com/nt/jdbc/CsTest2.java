package com.nt.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Scanner;

/*create or replace procedure p_get_emp_details(no in number,name out varchar,desg out varchar,bsal out number) as
begin
     select  ename,job,sal into name,desg,bsal from emp where empno=no;   
end;
/
*/
public class CsTest2 {
   private static final String  CALL_PROCEDURE="{CALL P_GET_EMP_DETAILS(?,?,?,?) }";
	public static void main(String[] args) {
		Scanner sc=null;
		int no=0;
		Connection con=null;
		CallableStatement cs=null;
		try {
			//read inputs
			sc=new Scanner(System.in);
			if(sc!=null) {
				System.out.println("enter Empno:::");
				no=sc.nextInt();
			}
			//register JDBC driver s/w
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//establish the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");
			//create CallableStatement object
			if(con!=null)
				cs=con.prepareCall(CALL_PROCEDURE);
			//register  out params with JDBC data types
			if(cs!=null) {
				cs.registerOutParameter(2, Types.VARCHAR);
				cs.registerOutParameter(3,Types.VARCHAR);
				cs.registerOutParameter(4, Types.INTEGER);
			}
			//set value IN Param
			if(cs!=null) 
				cs.setInt(1,no);
			//execute /call PL/SQL procedure
			if(cs!=null)
				cs.execute();
			//gather results from OUT params
			if(cs!=null) {
				System.out.println("Emp Name::"+cs.getString(2));
				System.out.println("Emp Job ::"+cs.getString(3));
				System.out.println("Emp Salary::"+cs.getInt(4));
			}
		}//try
		catch(SQLException se) {
			if(se.getErrorCode()==1403)
				System.out.println("data not found");
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
