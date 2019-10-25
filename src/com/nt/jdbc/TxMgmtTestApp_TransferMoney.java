package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/*CREATE TABLE "SYSTEM"."JDBC_ACCOUNT" 
(	"ACNO" NUMBER(10,0) NOT NULL ENABLE, 
	"HOLDERNAME" VARCHAR2(20 BYTE), 
	"BALANCE" FLOAT(126), 
	 CONSTRAINT "JDBC_ACCOUNT_PK" PRIMARY KEY ("ACNO")
*/
public class TxMgmtTestApp_TransferMoney {

	public static void main(String[] args) {
		Scanner sc=null;
		int srcAcno=0,destAcno=0;
		float amount=0.0f;
		Connection con=null;
		Statement st=null;
		int result[]=null;
		boolean flag=false;
		try {
			//read inputs
			sc=new Scanner(System.in);
			if(sc!=null) {
				System.out.println("enter source Account number:::");
				srcAcno=sc.nextInt();
				System.out.println("enter Destination Account number:::");
				destAcno=sc.nextInt();
				System.out.println("Enter Amount to transfer::");
				amount=sc.nextFloat();
			}
			//register JDBC driver s/w
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//establish the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "manager");
			//Begin Tx 
			con.setAutoCommit(false);
			//create JDBC statement object
			if(con!=null)
				st=con.createStatement();
			//add withdraw, deposite queries to the batch..
			  if(st!=null) {
				  st.addBatch("UPDATE JDBC_ACCOUNT SET  BALANCE=BALANCE-"+amount+" WHERE ACNO="+srcAcno);
				  st.addBatch("UPDATE JDBC_ACCOUNT SET BALANCE=BALANCE+"+amount+" WHERE  ACNO="+destAcno);
			  }
			  //execute the Batch
			  if(st!=null)
				  result=st.executeBatch();
			  //check the results
			  if(result!=null) {
				  for(int i=0;i<result.length;++i) {
					  if(result[i]==0) {
						  flag=true;
						  break;
					  }//if
				  }//for
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
			//perform Tx mgmt
			try {
				if(!flag) {
					con.commit();
					System.out.println("Money Transfered and Tx Committed");
				}
				else {
					con.rollback();
					System.out.println("Money not Transfered and Tx rolledback");
				}
			}//try
			catch(SQLException se) {
				se.printStackTrace();
			}
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
