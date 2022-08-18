
package minesweeper;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import mainForm.*;

public class GUIMine extends JFrame implements ActionListener{
	
	
	JFrame jf = new JFrame();
	private static final int Size = 32;
	private static final int width = 400;
	private static final int height = 600;
	
	private static ArrayList<NewButton> btns = new ArrayList<NewButton>();
	private static NewButton [][] stat;
	private static boolean[][] checked;
	
	private static int size, sizex;
	private static int flags;
	private static int watch;
	private static boolean start;
	private static Timer timer;
	
	private JPanel toppnl;
	private JPanel centerpnl;
	
	private static JLabel timehundred, timeten, timeone;
	private static Image timehundredimg, timetenimg, timeoneimg;
	
	private static JLabel flaghundred, flagten, flagone;
	private static Image flaghundredimg, flagtenimg, flagoneimg;
	
	private GridLayout grid;
	
	public static JButton newgame;
	private static Image face;
	
	public GUIMine() {
		super("Minesweeper");
		setSize(width, height);
		Dimension frameSize = getSize();
        Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((windowSize.width - frameSize.width) / 2,
                (windowSize.height - frameSize.height) / 2);
		setResizable(false);
		
		start = false;
		
		toppnl = new JPanel();
		toppnl.setLayout(new BoxLayout(toppnl, BoxLayout.LINE_AXIS));
		newgame = new JButton("");
		newgame.setPreferredSize(new Dimension(52, 52));
		newgame.setMaximumSize(newgame.getPreferredSize());
		setFace(0);
		newgame.addActionListener(this);
		
		flaghundred = new JLabel("");
		flagten = new JLabel("");
		flagone = new JLabel("");
		
		timehundred = new JLabel("");
		timeten = new JLabel("");
		timeone = new JLabel("");
		
		toppnl.add(flaghundred);
		toppnl.add(flagten);
		toppnl.add(flagone);
		toppnl.add(Box.createHorizontalGlue());
		toppnl.add(newgame);
		toppnl.add(Box.createHorizontalGlue());
		toppnl.add(timehundred);
		toppnl.add(timeten);
		toppnl.add(timeone);
		resetTime();
		centerpnl = new JPanel();
		
//		//초급으로 시작
//		loadGame(9);
		
		getContentPane().add(toppnl, BorderLayout.NORTH);
		getContentPane().add(centerpnl, BorderLayout.CENTER);
		pack();
		setVisible(true);

		
		this.addWindowListener(new WindowAdapter(){
			public void windowOpened(WindowEvent e) {
				loadGame(9);
			}
            public void windowClosing(WindowEvent e) { 
            	timer.stop();
            	clearGame();
            	jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
		});
	}
	
	public static int getBombNum() {
		switch(size) {
		case 9:
			return 10;
		case 16:
			return 40;
		case 30:
			return 99;
		default:
			return 0;
		}
	}


	public static void setFace(int i) {
		String a;
		switch(i) {
		case 0:
			a = "smile"; break;
		case 1:
			a = "surprise"; break;
		case 2:
			a = "dead"; break;
		case 3:
			a = "win"; break;
		default:
			a = "smile";
		}
		
		try {
			face = ImageIO.read(new File("src/minesweeper/images/face"+a+".gif"));
			newgame.setIcon(new ImageIcon(face));
		}
		catch(IOException e) {
			System.out.println("얼굴 이미지 파일을 찾을 수 없습니다.");
		}
		
	}
	
	public static boolean hasStarted() {
		return start;
	}
	
	public static void setStarted(boolean a) {
		start = a;
	}

	
	
	public static void startTime() {
		watch = 0;
		timer = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				watch++;
				setTime();
			}
		});
		timer.start();
	}
	
	private static void resetTime() {
		start = false;
		watch = 0;
		setTime();
	}
	
	private static void setTime() {
		int hundred = watch / 100;
		int ten = (watch % 100) / 10;
		int one = watch % 10;
		
		try {
			timehundredimg = ImageIO.read(new File("src/minesweeper/images/time"+hundred+".gif"));
			timetenimg = ImageIO.read(new File("src/minesweeper/images/time"+ten+".gif"));
			timeoneimg = ImageIO.read(new File("src/minesweeper/images/time"+one+".gif"));
			timehundred.setIcon(new ImageIcon(timehundredimg));
			timeten.setIcon(new ImageIcon(timetenimg));
			timeone.setIcon(new ImageIcon(timeoneimg));
		}
		catch(IOException e) {
			System.out.println("타이머 이미지 파일을 찾을 수 없습니다");
		}
	}
	
	private static void setLabel() {
		int hundreds = getFlags()/100;
		int tens = getFlags() /10;
		int ones = getFlags()%10;
		try {
			flaghundredimg = ImageIO.read(new File("src/minesweeper/images/time"+hundreds+".gif"));
			flagtenimg = ImageIO.read(new File("src/minesweeper/images/time"+tens+".gif"));
			flagoneimg = ImageIO.read(new File("src/minesweeper/images/time"+ones+".gif"));
			flaghundred.setIcon(new ImageIcon(flaghundredimg));
			flagten.setIcon(new ImageIcon(flagtenimg));
			flagone.setIcon(new ImageIcon(flagoneimg));
		}
		catch(IOException e) {
			System.out.println("타이머 이미지 파일을 찾을 수 없습니다");
		}
	}
	
	private void bombAssign() {
		Collections.shuffle(btns);
		for(int i = 0; i<getBombNum();i++)
			btns.get(i).setMine(true);
	}
	
	//주변 지뢰의 갯수를 카운트
	private void setValues() {
		for(int i = 0; i<sizex; i++) {	//열
			for(int j = 0; j<size; j++) {	//행
				int ct = 0;
				//왼쪽 칸
				if(i>0 && stat[i-1][j].getMine()) ct++;
				
				//오른쪽 칸
				if(i<sizex -1 && stat[i+1][j].getMine()) ct++;
				
				//위쪽 칸
				if(j>0 && stat[i][j-1].getMine()) ct++;
				
				//아래쪽 칸
				if(j<size -1 && stat[i][j+1].getMine()) ct++;
				
				//왼쪽 위 칸
				if(i>0 && j>0 && stat[i-1][j-1].getMine()) ct++;
				
				//오른쪽 위 칸
				if(i<sizex -1 && j>0 && stat[i+1][j-1].getMine()) ct++;
				
				//왼쪽 아래 칸
				if(i>0 && j<size-1 && stat[i-1][j+1].getMine()) ct++;
				
				//오른쪽 아래칸
				if(i<sizex-1 && j<size-1 && stat[i+1][j+1].getMine()) ct++;
				stat[i][j].setVal(ct);
				
			}
		}
	}
	
	//빈칸일 경우 확장하는 범위 설정
	public static void expandEmpty(int i, int j) {
		if(checked[i][j] == false && !stat[i][j].getMine()) {
			stat[i][j].setState(1);
			stat[i][j].setImg();
			checked[i][j] = true;
			
			if(stat[i][j].getVal() == 0) {
				
				if(i>0)	expandEmpty(i-1, j);
				
				if(i<sizex-1) expandEmpty(i+1, j);
				
				if(j>0) expandEmpty(i, j-1);
				
				if(j<size-1) expandEmpty(i, j+1);
				
				if(i>0 && j>0) expandEmpty(i-1, j-1);
				
				if(i<sizex-1 && j>0) expandEmpty(i+1, j-1);
				
				if(i>0 && j<size-1) expandEmpty(i-1, j+1);
				
				if(i<sizex-1 && j<size-1) expandEmpty(i+1, j+1);
			}
		}
		return;
	}
	
	public static int getFlags() {
		return getBombNum()-flags;
	}
	
	public static void decreaseFlags() {
		flags--;
		setLabel();
	}
	
	public static void increaseFlags() {
		flags++;
		setLabel();
		if(getFlags() == 0) checkWin();
	}
	
	public static void lost() {
		setFace(2);
		for(NewButton[] a : stat) {
			for(NewButton b : a) {
				b.getBtn().removeActionListener(b);
				b.getBtn().removeMouseListener(b);
			}
		}
		
		for(int i = 0; i<getBombNum(); i++) {
			if(btns.get(i).getState() != 2) btns.get(i).setState(4);
			btns.get(i).setImg();
		}
		
		for(int i = getBombNum(); i<sizex*size; i++) {
			if(btns.get(i).getState() == 3 && !btns.get(i).getMine()) {
				btns.get(i).setState(5);
				btns.get(i).setImg();
			}
		}
		timer.stop();
	}
	
	public static void checkWin() {
		boolean won1 = true;
		boolean won2 = true;
		
		//만약 모든 지뢰를 찾았거나 지뢰가 없는 곳을 모두 찾았을 경우
		for(int i = 0; i<getBombNum(); i++) {
			if(btns.get(i).getState() != 3) won1 = false;
		}
			
		for(int i = getBombNum(); i<size*sizex; i++) {
			if(btns.get(i).getState() != 1) won2 = false;
		}
		
		if(won1 || won2) win();
		JOptionPane.showMessageDialog(null, "지뢰를 모두 찾았습니다");
	}
	
	public static void win() {
		setFace(3);
		timer.stop();
		for(NewButton[] a: stat) {
			for(NewButton b: a) {
				b.getBtn().removeActionListener(b);
				b.getBtn().removeMouseListener(b);
			}
		}
	}
	
	private void loadGame(int n) {
		if(n == 30)
			sizex = 16;
		else
			sizex = n;
		int ct = 0;
		size = n;
		stat = new NewButton[sizex][size];
		centerpnl.setPreferredSize(new Dimension(size*Size, sizex*Size));
		centerpnl.setMaximumSize(centerpnl.getPreferredSize());
		centerpnl.setSize(size*Size, sizex*Size);
		
		grid = new GridLayout(0, size, 0, 0);
		centerpnl.setLayout(grid);
		for(int i = 0; i<sizex; i++) { //열
			for(int j = 0; j<size; j++) {	//행
				btns.add(new NewButton(i,j));
				stat[i][j] = btns.get(ct);
				centerpnl.add(btns.get(ct).getBtn());
				ct++;
			}
		}
		
		flags = 0;
		checked = new boolean[sizex][size];
		
		for(boolean[] a: checked) Arrays.fill(a, false);
		
		bombAssign();
		setValues();
		setLabel();
		pack();
		centerpnl.revalidate();
		centerpnl.repaint();
	}
	
	private void clearGame() {
		setFace(0);
		centerpnl.removeAll();
		repaint();
		btns.clear();
		resetTime();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(newgame)) {
			String[] buttons = {"초급", "중급", "고급"};
			int a = JOptionPane.showOptionDialog(this, "난이도", "새 게임", JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, buttons, buttons[1]);
			timer.stop();
			clearGame();
			if(a == 0)	loadGame(9);
			else if(a == 1) loadGame(16);
			else if(a == 2) loadGame(30);
			else if(size == 9) loadGame(9);
			else if (size == 16) loadGame(16);
			else if (size == 30) loadGame(30);
		}
	}
	
}
