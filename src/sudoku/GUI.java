package sudoku;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class GUI extends JFrame {

	JFrame jf = new JFrame();
	Sudoku gameSudoku = new Sudoku();
	JButton button1 = new JButton("확인");
	JButton button2 = new JButton("정답 확인");
	JButton button3 = new JButton("새게임");
	JPanel panels[] = new JPanel[9];
	JPanel cells[][] = new JPanel[9][9];
	JLabel labels[][] = new JLabel[9][9];
	JTextField texts[][] = new JTextField[9][9];
	JPanel topPanel = new JPanel();
	JPanel bottomPanel = new JPanel();
	Container mainContainer = this.getContentPane();
	int games[][][];
	int gameNumber = 0;
	int totalGames;

	
	public GUI(String title, int[][][] g) {
		
		super("Sudoku");
		this.setSize(500, 550);
		this.setLocation(700, 250);
		mainContainer.setLayout(new BorderLayout(5, 5));
		mainContainer.setBackground(Color.white);
		this.getRootPane().setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.white));

		games = g;
		totalGames = games.length;

//		topPanel.setBorder(new LineBorder(Color.black, 3));
//		topPanel.setBackground(Color.black);
		topPanel.setLayout(new GridLayout(1, 2, 50, 5));
		button1.addActionListener(new check());
		button2.addActionListener(new solve());
		button3.addActionListener(new newGame());
		topPanel.add(button1);
		topPanel.add(button2);
		mainContainer.add(topPanel, BorderLayout.NORTH);
		bottomPanel.setBorder(new LineBorder(Color.black, 3));
		bottomPanel.setBackground(Color.black);
		bottomPanel.setLayout(new GridLayout(3, 3, 5, 5));
		for (int i = 0; i < 9; i++) {
			panels[i] = new JPanel();
			panels[i].setLayout(new GridLayout(3, 3, 2, 2));
			panels[i].setBackground(Color.black);
			bottomPanel.add(panels[i]);
		}
		int row;
		int col;
		for (int i = 0; i < 9; i++) {
			row = i / 3;
			for (int j = 0; j < 9; j++) {
				col = j / 3;
				cells[i][j] = new JPanel();
				cells[i][j].setLayout(new GridLayout(1, 1, 5, 5));
				panels[row * 3 + col].add(cells[i][j]);
			}
		}
		
		this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e) { 
            	jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
		});
		
		setupGame();
		mainContainer.add(bottomPanel);
	}
//------------------------------------------------------------------------------------------------
//for toggle check button
	boolean finished = true;
	class check implements ActionListener {// 토글 버튼
		boolean on = true;
		check() {}

		@Override
		public void actionPerformed(ActionEvent e) {//틀렸거나 비어있는 셀 빨간색으로 변경
			// TODO Auto-generated method stub
			if (on) {
				on = false;
				button1.setText("확인");
				button2.setEnabled(false);
				for (int i = 0; i < 9; i++) {
					for (int j = 0; j < 9; j++) {
						if (gameSudoku.getOrig(i, j) == 0) {// 답과 틀린 숫자 확인
							String temp = texts[i][j].getText();
							String temp2 = String.valueOf(gameSudoku.getPuzzle(i, j));
							if (!temp.equals(temp2) & !temp.isEmpty()) {
								texts[i][j].setForeground(Color.red);
								finished = false;
							}
							if (temp.isEmpty()) {
								finished = false;
							}
						}
					}
				}
				if (finished) {
					JOptionPane.showMessageDialog(null, "정답입니다!");
					solve newSolve = new solve();
					newSolve.actionPerformed(e);
				}
//			} else {
//				button2.setEnabled(true);
//				on = true;
//				finished = true;
//				button1.setText("확인");
//				for (int i = 0; i < 9; i++) {
//					for (int j = 0; j < 9; j++) {
//						if (gameSudoku.getOrig(i, j) == 0) {// 답과 틀린 숫자 확인
//							texts[i][j].setForeground(Color.black);
//						}
//					}
//				}
			}
		}
	}
//------------------------------------------------------------------------------------------------
//정답 확인 버튼
	class solve implements ActionListener {
		solve() {}

		@Override
		public void actionPerformed(ActionEvent e) {
			button1.setEnabled(false);
			// TODO Auto-generated method stub
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					if (gameSudoku.getOrig(i, j) == 0) {// 답과 틀린 숫자 확인
						String temp = texts[i][j].getText();
						String temp2 = String.valueOf(gameSudoku.getPuzzle(i, j));
						texts[i][j].setEnabled(false);
						if (!temp.equals(temp2) | temp.isEmpty()) {
							texts[i][j].setText(temp2);
							texts[i][j].setForeground(Color.blue);
							texts[i][j].setBackground(Color.GRAY);
						} else {
							texts[i][j].setDisabledTextColor(Color.black);
						}
					}
				}
			}
			topPanel.remove(button2);
			topPanel.add(button3);
			repaint();
			revalidate();
		}
	}
	
//------------------------------------------------------------------------------------------------
// 새게임 버튼
	class newGame implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					cells[i][j].removeAll();
				}
			}
			button1.setEnabled(true);
			topPanel.remove(button3);
			topPanel.add(button2);
			button2.setEnabled(true);
			setupGame();
			repaint();
			revalidate();
		}
	}
	
	private void setupGame() {
		// 준비된 게임 4개를 다 했을 때 게임 나가짐 (마지막 게임이랑 전체 게임이랑 값이 같을 때)
		if (gameNumber == totalGames) {
//			String name = JOptionPane.showInputDialog(mainContainer, "Out of new games ):", null);
			System.exit(0);
		}
		gameSudoku.changePuzzle(games[gameNumber]);
		gameNumber++;
		gameSudoku.solve();
		gameSudoku.print();
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (gameSudoku.getOrig(i, j) != 0) {
					String temp = String.valueOf(gameSudoku.getOrig(i, j));
					labels[i][j] = new JLabel(temp);
					labels[i][j].setHorizontalAlignment(JLabel.CENTER);
					labels[i][j].setFont(new Font("Courier", Font.BOLD, 20));
					labels[i][j].setOpaque(true);
					labels[i][j].setBackground(Color.lightGray);
					cells[i][j].add(labels[i][j]);
				} else {
					texts[i][j] = new JTextField("");
					texts[i][j].setHorizontalAlignment(JTextField.CENTER);// 텍스트 가운데 정렬
					texts[i][j].setFont(new Font("Courier", Font.BOLD, 20));
					// tFields[i][j].setForeground(Color.blue);
					cells[i][j].add(texts[i][j]);
				}
			}
		}
	}

}
