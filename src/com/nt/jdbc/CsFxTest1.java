package com.nt.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Scanner;

import oracle.jdbc.OracleTypes;

/*create or replace function fx_view_delete_student(no in number,
        cnt out number)return sys_refcursor
as
details sys_refcursor;
begin
open details for
select sno,sname,sadd,avg from student where sno=no;
delete from student where sno=no;
cnt:=SQL%ROWCOUNT;
return details;
end;
/*/

public class CsFxTest1 {
  private static final String CALL_FX= "{ ?=call FX_VIEW_DELETE_STUDENT(?,?)}";
	public static void main(String[] args) {
		Scanner sc=null;
		int  no=0;
		Connection con=null;
		CallableStatement cs=null; 
		ResultSet rs=null;
		int count=0;
	   try {
		   //read inputs
		   sc=new Scanner(System.in);
		   if(sc!=null) {
			   System.out.println("enter student number::");
			   no=sc.nextInt();
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
			   cs.registerOutParameter(1,OracleTypes.CURSOR); //return param
			   cs.registerOutParameter(3,Types.INTEGER);  // out param
		   }
		   //set values to IN params
		   if(cs!=null) 
			   cs.setInt(2,no);
		   
		   //call  PL/SQL function
		    if(cs!=null)
		    	cs.execute();
		    //gather results from  Out params, return param
		    if(cs!=null) {
		    	//from return params
		    	rs=(ResultSet) cs.getObject(1);
		    	if(rs.next()) {
		    		System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3)+" "+rs.getFloat(4));
		    	}
		    	else {
		    		System.out.println("record not found");
		    		return;
		    	}
		    	//from out param
		    	count=cs.getInt(3);
		    	if(count==0)
		    		System.out.println("record not found for deletion");
		    	else
		    		System.out.println("record found,displayed and deleted");
		    }//if
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
			   if(rs!=null)
				   rs.close();
		   }
		   catch(SQLException se) {
			   se.printStackTrace();
		   }
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
