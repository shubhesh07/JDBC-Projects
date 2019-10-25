package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PsScrollableRsTest {
   private static final String  GET_STUDENTS="SELECT SNO,SNAME,SADD,AVG FROM STUDENT";
	public static void main(String[] args) {
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			//register JDBC driver s/w
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//establish the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system", "manager");
			//create JDBC Statement object
			if(con!=null)
			   ps=con.prepareStatement(GET_STUDENTS,ResultSet.TYPE_SCROLL_SENSITIVE,
					                                                                              ResultSet.CONCUR_READ_ONLY);
				
			//send and execute SQL Query in Db s/w
			if(ps!=null)
				rs=ps.executeQuery();
			//process the ReusltSEt
			if(rs!=null) {
				System.out.println("Records (Top-Bottom)");
				while(rs.next()) {
					System.out.println(rs.getInt(1)+"  "+rs.getString(2)+" "+rs.getString(3)+"  "+rs.getFloat(4));
				}
				
				System.out.println("Records (Bottom-Top)");
				rs.afterLast();
				while(rs.previous()) {
					System.out.println(rs.getInt(1)+"  "+rs.getString(2)+" "+rs.getString(3)+"  "+rs.getFloat(4));
				}
				
				rs.first();
				System.out.println(rs.getRow()+"-->"+rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3)+" "+rs.getFloat(4));
				rs.last();
				System.out.println(rs.getRow()+"-->"+rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3)+" "+rs.getFloat(4));
				rs.absolute(3);
				System.out.println(rs.getRow()+"-->"+rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3)+" "+rs.getFloat(4));
				rs.absolute(-4);
				System.out.println(rs.getRow()+"-->"+rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3)+" "+rs.getFloat(4));
				rs.relative(2);
				System.out.println(rs.getRow()+"-->"+rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3)+" "+rs.getFloat(4));
				rs.relative(-5);
				System.out.println(rs.getRow()+"-->"+rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3)+" "+rs.getFloat(4));
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
				if(rs!=null)
					rs.close();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
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
