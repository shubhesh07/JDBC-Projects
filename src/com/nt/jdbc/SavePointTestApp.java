package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.Scanner;

public class SavePointTestApp {
   private static final String   STOCK_MGMT_QUERY="UPDATE PRODUCT SET QTY=QTY-1 WHERE PID=?";
   private static final String   PAYMENT_MGMT_QUERY="UPDATE JDBC_ACCOUNT SET BALANCE=BALANCE-(SELECT  PRICE FROM PRODUCT WHERE PID=?) WHERE ACNO=?";
	public static void main(String[] args) {
		Scanner sc=null;
		int pid=0;
		int acno=0;
		Connection con=null;
		PreparedStatement ps1=null,ps2=null;
		int result1=0,result2=0;
		Savepoint sp=null;
		try {
           //read inputs
			sc=new Scanner(System.in);
			if(sc!=null) {
				System.out.println("Enter Product id::");
				pid=sc.nextInt();
				System.out.println("Enter Account number::");
				acno=sc.nextInt();
			}
			//register JDBC driver s/w
			   Class.forName("oracle.jdbc.driver.OracleDriver");
			   //establish the connection
			   con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system", "manager");
			   //enable /Being Tx
			   if(con!=null)
				   con.setAutoCommit(false);
			   //create PreparedStatement objects
			   if(con!=null) {
				   ps1=con.prepareStatement(STOCK_MGMT_QUERY);
				   ps2=con.prepareStatement(PAYMENT_MGMT_QUERY);
			   }
			   //set Query param values and execute the Query
			   if(ps1!=null && ps2!=null) {
				     ps1.setInt(1,pid);
				     ps2.setInt(1, pid);
				     ps2.setInt(2,acno);
			   }
			   //execute the Query
			   if(ps1!=null && ps2!=null) {
				   result1=ps1.executeUpdate();
				   sp=con.setSavepoint("sp1");
				   result2=ps2.executeUpdate();
			   }
			    
			   if(result1==0 && result2==0) {
				    con.rollback();
				    System.out.println("Tx rolled back");
			   }
			   else if(result1==0 && result2==1) {
				   con.rollback();
				    System.out.println("Tx rolled back");
			   }
			   else if(result1==1 && result2==1) {
				   con.commit();
				    System.out.println("Tx commited");
			   }
			   else {
				   con.rollback(sp);
				   con.commit();
				    System.out.println("Payment failed , but COD is enabled..");
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
				if(ps1!=null)
					ps1.close();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
			try {
				if(ps2!=null)
					ps2.close();
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
