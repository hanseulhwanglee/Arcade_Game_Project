package signUp;

import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.lang.reflect.Member;
import java.sql.*;



import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.ColorUIResource;

import logIn.LogIn;


public class SignUp extends JFrame {

	private JLabel LOGIN = new JLabel("Sign Up");
	
	private JTextField ID = new  HintTextField("���̵�");
	private JPasswordField PW = new HintPasswordField("��й�ȣ");
	public static JTextField NAME = new HintTextField("�̸�");
	
	
	private JButton btn =  new JButton("Sign Up");
	
	JOptionPane JOP = new JOptionPane();
	
	public SignUp(){
			
			
		setTitle("Sign Up");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = getContentPane();
		c.setBackground(new Color(255,255,255));
		c.setLayout(null);
		
		
		LOGIN.setLocation(105, 90);
		LOGIN.setSize(200, 70);
		LOGIN.setFont(new Font("���� ���", Font.BOLD, 50));
		LOGIN.setForeground(new Color(0,51,153));
		c.add(LOGIN);
		
		
		ID.setLocation(70, 200);
		ID.setSize(250, 30);
		ID.setText("���̵�");
		ID.setFont(new Font("���� ���", Font.PLAIN, 20));
		ID.setForeground(Color.LIGHT_GRAY);
		c.add(ID);
		
		
		PW.setLocation(70, 255);
		PW.setSize(250, 30);
		PW.setText("��й�ȣ");
		PW.setFont(new Font("���� ���", Font.PLAIN, 20));
		PW.setForeground(Color.LIGHT_GRAY);
		PW.setEchoChar((char)0);
		c.add(PW);
		
		
		NAME.setLocation(70, 310);
		NAME.setSize(250, 30);
		NAME.setText("�̸�");
		NAME.setFont(new Font("���� ���", Font.PLAIN, 20));
		NAME.setForeground(Color.LIGHT_GRAY);
		c.add(NAME);
		
		
		btn.setLocation(120, 380);
		btn.setSize(150, 50);
		btn.setBackground(new Color(0, 51, 153));
		btn.setForeground(Color.white);
		btn.setFont(new Font("���� ���", Font.BOLD, 25));
		btn.setBorderPainted(false);
		c.add(btn);
				
		
		setSize(400, 600);
		setLocation(750,200);
		setVisible(true);
		setResizable(false);
		
		this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e) { 
            	int result = JOP.showConfirmDialog(null, "�α��� ȭ������ ���ư��ðڽ��ϱ�?", "SGIN UP", JOP.YES_NO_OPTION, JOP.INFORMATION_MESSAGE, null);
            	if(result == 0) {
            		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            		new LogIn();
            	}
            	else {
            		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            	}
            	
            }
		});
		
		
		
		btn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent me) {
				
				if(ID.getText().equals("") || ID.getText().equals("���̵�"))
					JOP.showMessageDialog(null, "���̵� �Է����ּ���");
				else if(PW.getText().equals("") || PW.getText().equals("��й�ȣ"))
					JOP.showMessageDialog(null, "��й�ȣ�� �Է����ּ���");
				else if(NAME.getText().equals("") || NAME.getText().equals("�̸�"))
					JOP.showMessageDialog(null, "�̸��� �Է����ּ���");
				else if(ID.getText()!="" && PW.getText()!="" && NAME.getText()!="") {
					Connection con = null;
					PreparedStatement pstmt = null;
					Statement stmt = null;
					ResultSet rs = null; //���� ���� ������ �� ����ϴ� �۾� ��ü
					
					String userid = ID.getText();
					String userpw = PW.getText();
					String username = NAME.getText();
					
					
					
					try {
						Class.forName("oracle.jdbc.driver.OracleDriver");
						System.out.println("����Ŭ DB �˻� ����");
					}
					catch(ClassNotFoundException e) {
						System.out.println("����Ŭ DB �˻� ����");
					}
					
					try {
						String user = "scott";
						String pw = "tiger";
						String url = "jdbc:oracle:thin:@localhost:1521:orcl";
						con = DriverManager.getConnection(url, user, pw);
						
						System.out.println("����Ŭ�� ������");
						String query = "INSERT INTO ACCOUNT VALUES(?, ?, ?)";
						pstmt = con.prepareStatement(query);

						pstmt.setString(1, userid);
						pstmt.setString(2, userpw);
						pstmt.setString(3, username);
						
						
						pstmt.executeUpdate();
						
						pstmt.close();
						con.close();
						JOP.showMessageDialog(null, "ȸ�������� �Ϸ�Ǿ����ϴ�");
						new LogIn();
						setVisible(false);	
					}
					catch(SQLException e) {
						System.out.println("error = ���ᰴü ����� ���� : " + e);
						JOP.showMessageDialog(null,"�̹� �����ϴ� ���̵��Դϴ�");
					}
				}
			}
		});
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
			super.setEchoChar('��');
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

