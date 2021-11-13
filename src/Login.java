
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.*;

import javax.swing.*;
import java.io.*;
import java.sql.*;
import java.util.Properties;


public class Login extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;

	// composants login
	JPanel log=new JPanel(new GridLayout(0,2,10,10));
	JLabel connexionStatus=new JLabel();
	JLabel databaseText=new JLabel("database");
	JTextArea databaseTextArea=new JTextArea(0,20);	
	JLabel loginText=new JLabel("login");
	JTextArea loginTextArea=new JTextArea(0,20);
	JLabel passwordText=new JLabel("password");
	JPasswordField passwordTextArea=new JPasswordField();
	JLabel DriverText=new JLabel("driver");
	JTextArea DriverTextArea=new JTextArea(0,20);
	JButton connexion=new JButton("Connect");
	JButton cancel=new JButton("Cancel");
	
	Boolean connected=false;
	
	// Parametres de connection
	String url ="jdbc:mysql://localhost:3306/"; // URL de connexion
	//database : jdbc_tp
	String urlSuite="?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	String login = "root"; // user
	String driverName = "com.mysql.cj.jdbc.Driver";
	static Connection connection;
	static Connexion co;
	static String database;
	DriverPropertyInfo[] required;
	Properties props = new Properties();
	static Driver driver;
	
	


	public Login() {
		log.add(databaseText);
		log.add(databaseTextArea);
		log.add(loginText);
		log.add(loginTextArea);
		log.add(passwordText);
		log.add(passwordTextArea);
		log.add(DriverText);
		log.add(DriverTextArea);
		log.add(connexion);
		log.add(cancel);
		
		connexion.addActionListener(this);
		cancel.addActionListener(this);
		
		databaseTextArea.setText("jdbc_tp");
		loginTextArea.setText(login);
		DriverTextArea.setText(driverName);
		
		this.getContentPane().add(log,BorderLayout.CENTER);
		setSize(500,200);
	    setLocationRelativeTo(null);
		setVisible(true);

	}
	
	public static void main(String args[]) {
		Login l=new Login();
	    l.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	
	public void actionPerformed(ActionEvent e) {
		Object source=e.getSource();

		if(source==connexion) {
			co=new Connexion(databaseTextArea.getText(),loginTextArea.getText(),new String(passwordTextArea.getPassword()));
			
			try{connect(co);}
			catch(SQLException ex) {System.out.println(ex.getMessage());connexionStatus.setText("Connexion �chou�e : SQLException "+ex.getMessage()); reset();}
			catch(ClassNotFoundException ex) {System.out.println(ex.getMessage());connexionStatus.setText("Connexion �chou�e : ClassNotFound "+ex.getStackTrace());reset();}
			if(connected) {
				this.dispose();
				Fenetre f=new Fenetre(co,connection);
			}
		} else if (source==cancel) {
			reset();
		}
	}
	
	public void connect(Connexion co) throws SQLException, ClassNotFoundException {
		try{
			Class.forName(DriverTextArea.getText());
			url=url+co.getDatabase()+urlSuite;
			database=co.getDatabase();
			driver = DriverManager.getDriver(url);
			required = driver.getPropertyInfo(url, props);
			connection = DriverManager.getConnection(url, co.getLogin(), co.getPassword());
			connected = true;
			connexionStatus.setText("Connexion r�ussie");
			System.out.println("Connect� "+url);
		}catch(SQLException e) {e.printStackTrace();}		
		
	}
	
	public void reset() {
		databaseTextArea.setText("");
		loginTextArea.setText("");
		passwordTextArea.setText("");
		connexionStatus.setText("");	
	}
	
	public static Connection getConnection() {return connection;}

	
	
}
