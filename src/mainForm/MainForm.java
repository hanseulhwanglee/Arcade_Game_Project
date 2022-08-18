package mainForm;

import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.sql.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.ColorUIResource;

import logIn.LogIn;
import signUp.SignUp;
import sudoku.games;
import tetrasquare.TetraSquare;
import minesweeper.GUIMine;
import minesweeper.NewButton;
import minesweeper.Spot;
import nonogram.nonogram;


public class MainForm extends JFrame {

	private JLabel choosegame = new JLabel("°ÔÀÓÀ» ¼±ÅÃÇÏ¼¼¿ä");
	
	private JButton sudokubtn =  new JButton("½ºµµÄí");
	private JButton tetrasquarebtn =  new JButton("Å×Æ®¶ó½ºÄù¾î");
	private JButton nonogrambtn =  new JButton("³ë³ë±×·¥");
	private JButton minesweeperbtn =  new JButton("Áö·ÚÃ£±â");
	
	JOptionPane JOP = new JOptionPane();

	public MainForm(){
			
			
		setTitle("MainForm");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		Container c = getContentPane();
		c.setBackground(new Color(255,255,255));
		c.setLayout(null);
		
		
		choosegame.setLocation(500, 100);
		choosegame.setSize(700, 100);
		choosegame.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 50));
		choosegame.setForeground(new Color(0,51,153));
		c.add(choosegame);
		

		sudokubtn.setLocation(120, 350);
		sudokubtn.setSize(150, 150);
		sudokubtn.setBackground(new Color(0, 51, 153));
		sudokubtn.setForeground(Color.white);
		sudokubtn.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 21));
		sudokubtn.setBorderPainted(false);
		c.add(sudokubtn);
		
		tetrasquarebtn.setLocation(450, 350);
		tetrasquarebtn.setSize(150, 150);
		tetrasquarebtn.setBackground(new Color(0, 51, 153));
		tetrasquarebtn.setForeground(Color.white);
		tetrasquarebtn.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 19));
		tetrasquarebtn.setBorderPainted(false);
		c.add(tetrasquarebtn);
		
		nonogrambtn.setLocation(780, 350);
		nonogrambtn.setSize(150, 150);
		nonogrambtn.setBackground(new Color(0, 51, 153));
		nonogrambtn.setForeground(Color.white);
		nonogrambtn.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 21));
		nonogrambtn.setBorderPainted(false);
		c.add(nonogrambtn);
		
		minesweeperbtn.setLocation(1110, 350);
		minesweeperbtn.setSize(150, 150);
		minesweeperbtn.setBackground(new Color(0, 51, 153));
		minesweeperbtn.setForeground(Color.white);
		minesweeperbtn.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 21));
		minesweeperbtn.setBorderPainted(false);
		c.add(minesweeperbtn);
		
				
		
		setSize(1400, 800);
		setLocation(280,130);
		setVisible(true);
		setResizable(false);
		
		this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e) { 
            	int result = JOP.showConfirmDialog(null, "·Î±×¾Æ¿ô ÇÏ½Ã°Ú½À´Ï±î?", "Log Out", JOP.YES_NO_OPTION, JOP.INFORMATION_MESSAGE, null);
            	if(result == 0) {
            		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            		new LogIn();
            	}
            	else {
            		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            	}
            	
            }
		});
		
		sudokubtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new games();
			}
		});
		
		tetrasquarebtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new TetraSquare();
			}
		});
		
		nonogrambtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new nonogram();
			}
		});
		
		
		minesweeperbtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new GUIMine();
			}
		});
	}
}
