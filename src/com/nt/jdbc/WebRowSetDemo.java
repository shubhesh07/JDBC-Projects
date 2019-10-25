package com.nt.jdbc;

import java.io.FileWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.SQLException;

import javax.sql.rowset.WebRowSet;

import oracle.jdbc.rowset.OracleWebRowSet;

public class WebRowSetDemo {

	public static void main(String[] args) {
		WebRowSet wrowset=null; 
		Writer writer=null;
		StringWriter sw=null;
		try {
			//Bean style programming
			wrowset=new OracleWebRowSet();
			wrowset.setUrl("jdbc:oracle:thin:@localhost:1521:xe");
			wrowset.setUsername("system");
			wrowset.setPassword("manager");
			wrowset.setCommand("SELECT * FROM STUDENT");
			//excute the query
			wrowset.execute();
			//process the RowSet
			while(wrowset.next()) {
				System.out.println(wrowset.getInt(1)+"  "+wrowset.getString(2)+"  "+wrowset.getString(3)+"  "+wrowset.getFloat(4));
			}
			System.out.println("....................................");
			writer=new FileWriter("e:/student.xml");
			wrowset.writeXml(writer);
			System.out.println("writes the  info  to  xml file..");
			System.out.println("..............................");
	         sw=new StringWriter();
	         wrowset.writeXml(sw);
	         System.out.println(sw);
	         
			
			
		}//try
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			//close rowset
			try {
				if(wrowset!=null)
					wrowset.close();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
		}//finally
	}//main
}//class
