package logIn;

import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.sql.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.ColorUIResource;

import mainForm.MainForm;
import signUp.SignUp;


public class LogIn extends JFrame {

	private JLabel LOGIN = new JLabel("LOG IN");
	
	private JTextField ID = new  HintTextField("아이디 입력");
	private JPasswordField PW = new HintPasswordField("비밀번호 입력");
	
	private JLabel signup = new JLabel("Sign Up");
	
	private JButton btn =  new JButton("Log In");
	
	JOptionPane JOP = new JOptionPane();
	
	

	
	public LogIn(){
			
			
		setTitle("LOGIN");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = getContentPane();
		c.setBackground(new Color(255,255,255));
		c.setLayout(null);
		
		LOGIN.setLocation(108, 90);
		LOGIN.setSize(200, 50);
		LOGIN.setFont(new Font("맑은 고딕", Font.BOLD, 50));
		LOGIN.setForeground(new Color(0,51,153));
		c.add(LOGIN);
		
		
		ID.setLocation(70, 200);
		ID.setSize(250, 30);
		ID.setText("아이디 입력");
		ID.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		ID.setForeground(Color.LIGHT_GRAY);
		c.add(ID);
		
		PW.setLocation(70, 270);
		PW.setSize(250, 30);
		PW.setText("비밀번호 입력");
		PW.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		PW.setForeground(Color.LIGHT_GRAY);
		PW.setEchoChar((char)0);
		c.add(PW);
		
		
		btn.setLocation(120, 380);
		btn.setSize(150, 50);
		btn.setBackground(new Color(0, 51, 153));
		btn.setForeground(Color.white);
		btn.setFont(new Font("맑은 고딕", Font.BOLD, 25));
		btn.setBorderPainted(false);
		c.add(btn);
		
		signup.setLocation(165, 320);
		signup.setSize(60, 25);
		signup.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		signup.setForeground(new Color(102,153,255));
		c.add(signup);
				
		
		setSize(400, 600);
		setLocation(750,200);
		setVisible(true);
		setResizable(false);
		
		this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e) { 
            	int result = JOP.showConfirmDialog(null, "종료하시겠습니까?", "EXIT", JOP.YES_NO_OPTION, JOP.INFORMATION_MESSAGE, null);
            	if(result == 0) {
            		setDefaultCloseOperation(EXIT_ON_CLOSE);
            	}
            	else {
            		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            	}
            	
            }
		});
		
		
		signup.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new SignUp();
				setVisible(false);
			}
		});
		
		btn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent me) {
				
				if(ID.getText().equals(""))
					JOP.showMessageDialog(null, "아이디를 입력해주세요");
				else if(PW.getText().equals(""))
					JOP.showMessageDialog(null, "비밀번호를 입력해주세요");
				else {
					Connection con = null;
					PreparedStatement pstmt = null;
					Statement stmt = null;
					ResultSet rs = null; //여러 행을 가져올 때 사용하는 작업 객체
					
					String userid = ID.getText();
					String userpw = PW.getText();
					
					int result = 0;
					

					String query = "SELECT USERPW FROM ACCOUNT WHERE USERID =?";
					
					try {
						Class.forName("oracle.jdbc.driver.OracleDriver");
						System.out.println("오라클 DB 검색 성공");
					}
					catch(ClassNotFoundException e) {
						System.out.println("오라클 DB 검색 실패");
					}
					try {
						String user = "scott";
						String pw = "tiger";
						String url = "jdbc:oracle:thin:@localhost:1521:orcl";
						con = DriverManager.getConnection(url, user, pw);
						
						System.out.println("오라클과 연동됨");
						pstmt = con.prepareStatement(query);
						
						
						pstmt.setString(1, userid);
						rs = pstmt.executeQuery();
						
						if(rs.next()) {
							if(rs.getString(1).equals(userpw)) {
								result = 1;
							}
							else {
								result = -1;
							}
						}
						
						if(result == 1) {
							JOP.showMessageDialog(null, "로그인에 성공했습니다");
							new MainForm();
							setVisible(false);
						}
						else if(result == -1) {
							JOP.showMessageDialog(null, "비밀번호를 확인하세요");
						}
						else if(result == 0) {
							JOP.showMessageDialog(null, "아이디를 확인하세요");
						}

						pstmt.close();
						con.close();
					}
					catch(SQLException e) {
						System.out.println("error = 연결객체 만들기 오류 : " + e);
					}
				}
			}
		});
	
		
	}
	


	public static void main(String[] args) {
		new LogIn();

	}
}


class HintTextField extends JTextField implements FocusListener {

	private final String hint;
	private boolean showingHint;

	public HintTextField(final String hint) {
		super(hint);
		this.hint = hint;
		this.showingHint = true;
		super.addFocusListener(this);
	}

	@Override
	public void focusGained(FocusEvent e) {
		if(this.getText().isEmpty()) {
			super.setText("");
			super.setForeground(Color.black);
			showingHint = false;
		}
	}
	@Override
	public void focusLost(FocusEvent e) {
		if(this.getText().isEmpty()) {
			super.setText(hint);
			super.setForeground(Color.LIGHT_GRAY);
			showingHint = true;
		}
	}
	
	@Override
	public String getText() {
		return showingHint ? "" : super.getText();
	}
}


class HintPasswordField extends JPasswordField implements FocusListener {

	private final String hint;
	private boolean showingHint;

	public HintPasswordField(final String hint) {
		super(hint);
		this.hint = hint;
		this.showingHint = true;
		super.addFocusListener(this);
	}

	@Override
	public void focusGained(FocusEvent e) {
		if(this.getText().isEmpty()) {
			super.setText("");
			super.setEchoChar('●');
			super.setForeground(Color.black);
			showingHint = false;
		}
	}
	@Override
	public void focusLost(FocusEvent e) {
		if(this.getText().isEmpty()) {
			super.setText(hint);
			super.setEchoChar((char)0);
			super.setForeground(Color.LIGHT_GRAY);
			showingHint = true;
		}
	}

	@Override
	public String getText() {
		return showingHint ? "" : super.getText();
	}
}

