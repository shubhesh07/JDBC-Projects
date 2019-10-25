package com.nt.jdbc;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class MiniProjectGUI_AllStatementsApp2 extends  JFrame  implements ActionListener {
	private static final String GET_STUDENT_BY_SNO="SELECT SNO,SNAME,M1,M2,M3 FROM ALL_STUDENT WHERE SNO=?";
	private static final String GET_ALL_SNOS="SELECT SNO FROM ALL_STUDENT";
	private static final String CALL_PRO="{CALL P_FIND_PASS_FAIL(?,?,?,?)}"; 
	  private JLabel lno,lname,lm1,lm2,lm3,lresult;
	  private JComboBox<Integer> tno;
	  private JTextField tname,tm1,tm2,tm3,tresult;
	  private  JButton bDetails,bResult,bClear;
	  private Connection con;
	  private Statement st;
	  private PreparedStatement ps;
	  private CallableStatement cs;
	  private ResultSet rs1,rs2;
	
	public MiniProjectGUI_AllStatementsApp2() {
		System.out.println("MiniProjectGUI_AllStatementsApp:: 0-param constructor");
		setTitle("MiniProject App");
		setSize(300,300);
		setBackground(Color.gray);
		setLayout(new FlowLayout());
		//add comps
		lno=new JLabel("Student Number");
		add(lno);
		tno=new JComboBox<Integer>();
		add(tno);
		bDetails=new JButton("details");
		bDetails.addActionListener(this);
		add(bDetails);
		
		lname=new JLabel("Student name::");
		add(lname);
		tname=new JTextField(10);
		add(tname);
		
		lm1=new JLabel("Marks1::");
		add(lm1);
		tm1=new JTextField(10);
		add(tm1);
		
		lm2=new JLabel("Marks2::");
		add(lm2);
		tm2=new JTextField(10);
		add(tm2);
		
		lm3=new JLabel("Marks3::");
		add(lm3);
		tm3=new JTextField(10);
		add(tm3);
		
		bResult=new JButton("Result");
		bResult.addActionListener(this);
		add(bResult);
		
		lresult=new JLabel("Result::");
		add(lresult);
		tresult=new JTextField(10);
		add(tresult);
		
		bClear=new JButton("clear");
		bClear.addActionListener(this);
		add(bClear);
		
		
		
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initializeJdbc();
		tm1.setEditable(false);
		tm2.setEditable(false);
		tm3.setEditable(false);
		tname.setEditable(false);
		tresult.setEditable(false);
		//add window Listener to frame
		this.addWindowListener(new WindowAdapter() {
			public  void windowClosing(WindowEvent we) {
			System.out.println("MiniProjectGUI_AllStatementsApp.windowClosing()");
			try {
				if(rs2!=null)
					rs2.close();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
			try {
				if(rs1!=null)
					rs1.close();
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
				if(ps!=null)
					ps.close();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
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
			
		}//windowClosing method
			
		}); //method call
		
	}//constructor
	
	private  void initializeJdbc() {
		System.out.println("MiniProjectGUI_AllStatementsApp.initializeJdbc()");
		try {
			//register JDBC Driver
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//estalish the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");
			// create SimpleSatement object
			st=con.createStatement();
			//create PreparedStatement obj having pre-compiled SQL Query
			ps=con.prepareStatement(GET_STUDENT_BY_SNO);
			//create CallableStatement obj
			cs=con.prepareCall(CALL_PRO);
			//register OUT params with jdbc  data types
			cs.registerOutParameter(4,Types.VARCHAR);
			//load snos into ComboBox only for during App startup
			rs1=st.executeQuery(GET_ALL_SNOS);
			//copy snos of ResultSet obj to  ComboBox
			while(rs1.next()) {
				tno.addItem(rs1.getInt(1));
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
		
	}

	public static void main(String[] args) {
		System.out.println("MiniProjectGUI_AllStatementsApp.main(-)");
		new MiniProjectGUI_AllStatementsApp2();
		

	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		System.out.println("MiniProjectGUI_AllStatementsApp.actionPerformed()");
		int no=0;
		String name=null;
		int m1=0,m2=0,m3=0;
		String result=null;
		if(ae.getSource()==bDetails) {
			System.out.println("details button is clicked");
			try {
				//read selected item from combo box
				no=(int) tno.getSelectedItem();
				//set query param value
				ps.setInt(1,no);
				//execute the SQL query
				rs2=ps.executeQuery();
				//set values to text boxes
				if(rs2.next()) {
					name=rs2.getString(2);
					m1=rs2.getInt(3);
					m2=rs2.getInt(4);
					m3=rs2.getInt(5);
					tname.setText(name);
					tm1.setText(String.valueOf(m1));
					tm2.setText(String.valueOf(m2));
					tm3.setText(String.valueOf(m3));
				}
			}//try
			catch(SQLException se) {
				se.printStackTrace();
			}
			
		}
		else if(ae.getSource()==bResult) {
			System.out.println("result button is clicked");
			try {
				// read text box values
				m1=Integer.parseInt(tm1.getText());
				m2=Integer.parseInt(tm2.getText());
				m3=Integer.parseInt(tm3.getText());
				//set marks  as query param values
				cs.setInt(1, m1);
				cs.setInt(2, m2);
				cs.setInt(3,m3);
				//execute Pl/SQL procedure
				cs.execute();
				//gather results from OUT params
				result=cs.getString(4);
				//set results to textbox
				tresult.setText(result);
			}//try
			catch(SQLException se) {
				se.printStackTrace();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			
		}//else
		else if(ae.getSource()==bClear) {
			//empty textboxes
			tname.setText("");
			tm1.setText("");
			tm2.setText("");
			tm3.setText("");
			tresult.setText("");
			
		}
	}//actionPerformed(-)

  
  
}//class
