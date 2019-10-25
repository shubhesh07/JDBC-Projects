package com.nt.jdbc;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class GUIScrollFrameApp extends JFrame implements ActionListener {
	private static final String  GET_ALL_STUDENTS="SELECT SNO,SNAME,SADD,AVG FROM STUDENT";
	private   JLabel lno,lname,ladd,lavg;
	private   JTextField tno,tname,taddrs,tavg;
	private JButton bFirst,bNext,bPrevious,bLast;
	private Connection con;
	private PreparedStatement ps;
	private ResultSet rs;
	
	public GUIScrollFrameApp() {
		System.out.println("GUIScrollFrameApp::0-param constructor");
		setTitle("ScrollFrameApp");
		setSize(400,300);
		setBackground(Color.GRAY);
		setLayout(new FlowLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//add comps
		lno=new JLabel("student number");
		add(lno);
		tno=new JTextField(10);
		add(tno);
		lname=new JLabel("student name");
		add(lname);
		tname=new JTextField(10);
		add(tname);
		ladd=new JLabel("student addrs");
		add(ladd);
		taddrs=new JTextField(10);
		add(taddrs);
		lavg=new JLabel("student avg");
		add(lavg);
		tavg=new JTextField(10);
		add(tavg);
		bFirst=new JButton("first");
		add(bFirst);
		bNext=new JButton("next");
		add(bNext);
		bPrevious=new JButton("previous");
		add(bPrevious);
		bLast=new JButton("Last");
		add(bLast);
		//Add ActionListener to all the 4 buttons
		bFirst.addActionListener(this);
		bNext.addActionListener(this);
		bPrevious.addActionListener(this);
		bLast.addActionListener(this);
		//set editable false
		tno.setEditable(false);
		tname.setEditable(false);
		tavg.setEditable(false);
		taddrs.setEditable(false);
		
		setVisible(true);
		initializeJdbc();
		this.addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent e) {
				System.out.println("GUIScrollFrameApp()::windowClosing()");
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
			  
			}
			
		});
	}
	
	private void initializeJdbc() {
		System.out.println("GUIScrollFrameApp.initJdbc()");
		try {
			//register JDBC driver s/w
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//establish the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");
			//create Statement object
			ps=con.prepareStatement(GET_ALL_STUDENTS,
					                                          ResultSet.TYPE_SCROLL_SENSITIVE,
					                                         ResultSet.CONCUR_READ_ONLY);
			
			//create ScrollableResultSet object
			rs=ps.executeQuery();
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
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
	     System.out.println("GUIScrollFrameApp.actionPErformed(-)");
	     boolean flag=false;
	     try {
	     if(ae.getSource()==bFirst) {
	    	 rs.first();
	    	 flag=true;
	     }
	     else if(ae.getSource()==bNext) {
	    	 
	    	 if(!rs.isLast()) {
	    		 rs.next();
	    		 flag=true;
	    	 }
	    	 
	     }
	     else if(ae.getSource()==bPrevious) {
	    	 if(!rs.isFirst()) {
	    		 rs.previous();
	    		 flag=true;
	    	 }
	    	 
	     }
	     else {
	    	 rs.last();
	    	 flag=true;
	     }
	     
	     if(flag==true) {
	    	 tno.setText(rs.getString(1));
	    	 tname.setText(rs.getString(2));
	    	 taddrs.setText(rs.getString(3));
	    	 tavg.setText(rs.getString(4));
	     }
	     
	     }//try
	     catch(SQLException se) {
	    	 se.printStackTrace();
	     }
	     catch(Exception e) {
	    	 e.printStackTrace();
	     }
	     
	}//actionPerfromed(-)

	public static void main(String[] args) {
		System.out.println("main(-) method");
		new GUIScrollFrameApp();

	}

}
