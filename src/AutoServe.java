import java.awt.*;
import java.awt.BorderLayout;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

class ManagerPage extends JFrame{
	private JLabel tempLabel = new JLabel("Manager", JLabel.CENTER);

	public ManagerPage(){
		super("AutoServe/Manager");

		this.init();
	
		this.setSize(800, 800);

		this.setLocation(250, 250);

		this.setVisible(true);		
	}

	public void init(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());

		this.add(this.tempLabel);
	}

	public void start(){
	}
}

class LogInPage extends JFrame implements ActionListener{
	private JPanel panel = new JPanel();

	private JLabel autoServeLogo = new JLabel(new ImageIcon("../image/autoServeLogo.png"));

	private JLabel labelId = new JLabel("ID : ", JLabel.LEFT);
	private JLabel labelPass = new JLabel("PW : ", JLabel.LEFT);

	private JTextField textId = new JTextField(10);
	private JPasswordField passPass = new JPasswordField(10);

	private JButton LogInButton = new JButton("Log In");


	public LogInPage(){
		super("AutoServe");
		
		this.init();

		this.setComponents();

		this.setSize(400, 600);

		//Dimension screen = (Toolkit.getDefaultToolkit()).getScreenSize();

		//this.setLocation((int)(screen.getWidth()-this.getWidth()/2), (int)(screen.getHeight()-this.getHeight()/2));
		this.setLocation(250, 250);

		this.setVisible(true);
	}
	
	public void init(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.placeComponents();

		this.setLayout(new BorderLayout());
		this.panel.setSize(400, 600);
		this.panel.setBackground(new Color(250, 255, 168));
		this.add("Center", this.panel);
	}

	public void setComponents(){
		this.LogInButton.addActionListener(this); 
	}

	public void placeComponents(){
		this.panel.setLayout(null);

		this.autoServeLogo.setBounds(75, 125, 250, 70);
		this.panel.add(this.autoServeLogo);

		this.labelId.setBounds(95, 300, 40, 25);
		this.panel.add(this.labelId);

		this.textId.setBounds(145, 300, 160, 25);
		this.panel.add(this.textId);

		this.labelPass.setBounds(95, 330, 40, 25);
		this.panel.add(this.labelPass);

		this.passPass.setBounds(145, 330, 160, 25);
		this.panel.add(this.passPass);

		this.LogInButton.setBounds(160, 360, 80, 25);
		this.panel.add(this.LogInButton);
	}


	public void actionPerformed(ActionEvent e){
		String id = null;
		String pass = null;
		if(e.getSource() == this.LogInButton){
			id = new String(this.textId.getText());
			pass = new String(this.passPass.getText());
		}

		logInProc(id, pass);
	}

	public void logInProc(String id, String pass){
		try{	
			//for windows
			//Class.forName("org.gjt.mm.mysql.Driver");

			//for ubuntu
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("The driver was installed normally");
		}catch(ClassNotFoundException e){
			System.err.println("No driver");
		}

		Connection conn = null;

		String url = "jdbc:mysql://127.0.0.1:3306/AutoServe";
		String dbId = "root";
		String dbPass = "4ahrfkd";

		try{
			conn = DriverManager.getConnection(url, dbId, dbPass);
		}catch(SQLException e){
		}

		String query = "select * from employee where id = ? and pass = ?";
		ResultSet result = null;
		try{
			PreparedStatement pstmt = conn.prepareStatement(query);
				pstmt.setString(1, id);
				pstmt.setString(2, pass);
			
			result = pstmt.executeQuery();

			if(result.next()){
				if(result.getString(4).equals(new String("M"))){
						
					this.setVisible(false);
					(new ManagerPage()).setVisible(true);
				}
			}
			else{
				JDialog dlg = new JDialog(this, "Warning");
					Container dlgcon = dlg.getContentPane();
					dlgcon.setLayout(new BorderLayout());
					dlgcon.add("Center", new JLabel("ID or password does not exist", JLabel.CENTER));
				dlg.setSize(300,150);
				dlg.setLocation(300, 300);
				dlg.setVisible(true);
			}
		}
		catch(SQLException e){
		}
	}
}

public class AutoServe{
	public static void main(String[] args){
		LogInPage lip = new LogInPage();
	}
}
