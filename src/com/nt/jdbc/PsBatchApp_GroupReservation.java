package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

/*CREATE SEQUENCE  "SYSTEM"."PSGNR_ID_SEQ"  MINVALUE 1 MAXVALUE 10000 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;
 
 CREATE TABLE "SYSTEM"."RAILWAY_TICKET_RESERVATION" 
   (	"PSGNR_ID" NUMBER(10,0) NOT NULL ENABLE, 
	"PSGNR_NAME" VARCHAR2(20 BYTE), 
	"PSGNR_AGE" NUMBER(3,0), 
	"SOURCE_PLACE" VARCHAR2(20 BYTE), 
	"DEST_PLACE" VARCHAR2(20 BYTE), 
	"FARE" FLOAT(126), 
	 CONSTRAINT "RAILWAY_TICKET_RESERVATION_PK" PRIMARY KEY ("PSGNR_ID")
 */

public class PsBatchApp_GroupReservation {
    private static final String  INSERT_TICKET_RESERVATION_QUERY="INSERT INTO RAILWAY_TICKET_RESERVATION VALUES(PSGNR_ID_SEQ.NEXTVAL,?,?,?,?,?)";
	public static void main(String[] args) {
		Scanner sc=null;
		int groupSize=0;
		String sourcePlace=null,destPlace=null;
		float fare=0;
		Connection con=null;
		String name=null;
		int age=0;
		PreparedStatement ps=null;
		int result[]=null;
		try {
			//read inputs
			sc=new Scanner(System.in);
			if(sc!=null) {
				System.out.println("Enter group Size::");
				groupSize=sc.nextInt();
				System.out.println("Enter source place ::");
				sourcePlace=sc.next();
				System.out.println("Enter Dest Place::");
				destPlace=sc.next();
				System.out.println("Enter Ticket Price");
				fare=sc.nextFloat();
			}//if
		  //register  JDBC driver s/w
		Class.forName("oracle.jdbc.driver.OracleDriver");
		//establish the connection
		con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system", "manager");
		//create PreparedStaetment object
		if(con!=null)
			ps=con.prepareStatement(INSERT_TICKET_RESERVATION_QUERY);
		//read inputs from enduser and add them query param values to the batch
		if(ps!=null && sc!=null) {
			for(int i=1;i<=groupSize;++i) {
				System.out.println("Enter "+i+" passenger name::");
				name=sc.next();
				System.out.println("Enter "+i+" passgenger age::");
				age=sc.nextInt();
				//add query param values to the batch
				ps.setString(1, name);
				ps.setInt(2,age);
				ps.setString(3,sourcePlace);
				ps.setString(4,destPlace);
				ps.setFloat(5,fare);
				ps.addBatch();
			}//for
		}//if
		//execute batch..
		result=ps.executeBatch();
		
		if(result!=null)
			System.out.println("Group reservation of tickets is done");
		else
			System.out.println("Group reservation of tickets is not done");
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
