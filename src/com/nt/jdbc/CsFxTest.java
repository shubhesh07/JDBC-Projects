package com.nt.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Scanner;

/*create or replace function fx_get_emp_details(no in number,
        name out varchar,
         desg out varchar)return  number
as
salary  number;
begin
select   ename,job,sal  into  name,desg,salary  from emp where empno=no;
return salary;
end;
/
*/
public class CsFxTest {
  private static final String CALL_FX= "{ ?=call FX_GET_EMP_DETAILS(?,?,?)}";
	public static void main(String[] args) {
		Scanner sc=null;
		int empNo=0;
		Connection con=null;
		CallableStatement cs=null; 
	   try {
		   //read inputs
		   sc=new Scanner(System.in);
		   if(sc!=null) {
			   System.out.println("enter employee number::");
			   empNo=sc.nextInt();
		   }
		   //regsiter JDBC Driver s/w
		   Class.forName("oracle.jdbc.driver.OracleDriver");
		   //establish the connection
		   con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");
		   //create CallableStatement object
		   if(con!=null)
			   cs=con.prepareCall(CALL_FX);
		   //register return ,OUT params with JDBC data types
		   if(cs!=null) {
			   cs.registerOutParameter(1,Types.INTEGER); //return param
			   cs.registerOutParameter(3, Types.VARCHAR); //out param
			   cs.registerOutParameter(4,Types.VARCHAR);  // out param
		   }
		   //set values to IN params
		   if(cs!=null) 
			   cs.setInt(2,empNo);
		   
		   //call  PL/SQL function
		    if(cs!=null)
		    	cs.execute();
		    //gather results from  Out params, return param
		    if(cs!=null) {
		    	System.out.println("empName::"+cs.getString(3));
		    	System.out.println("emp Addrs::"+cs.getString(4));
		    	System.out.println( "empm salary ::"+cs.getInt(1));
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
