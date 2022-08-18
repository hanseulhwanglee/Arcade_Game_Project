package nonogram;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import logIn.LogIn;


public class nonogram extends JFrame implements ActionListener {
	
	JFrame jf = new JFrame();
   
   JOptionPane JOP = new JOptionPane();
   
   private JPanel pn1 = new JPanel();
   
   private String[] datar = { //위쪽 배열
         "10", "10", "3/5", "2/2/1", "1/3/1", "1/1", "2/5", "3/5", "10", "10"
   };
   private String[] datac = { //왼쪽 배열
         "10", "4/4", "3/3", "2/2", "2/2", "5/4", "5/4", "3/1/4", "3/4", "10"
   };
   private DefaultTableModel model;
   
   
   private Color[][] ansarr = {   {Color.black, Color.black, Color.black, Color.black, Color.black, Color.black, Color.black, Color.black, Color.black, Color.black},
                           {Color.black, Color.black, Color.black, Color.black, Color.white, Color.white, Color.black, Color.black, Color.black, Color.black},
                           {Color.black, Color.black, Color.black, Color.white, Color.white, Color.white, Color.white, Color.black, Color.black, Color.black},
                           {Color.black, Color.black, Color.white, Color.white, Color.white, Color.white, Color.white, Color.white, Color.black, Color.black},
                           {Color.black, Color.black, Color.white, Color.white, Color.white, Color.white, Color.white, Color.white, Color.black, Color.black},
                           {Color.black, Color.black, Color.black, Color.black, Color.black, Color.white, Color.black, Color.black, Color.black, Color.black},
                           {Color.black, Color.black, Color.black, Color.black, Color.black, Color.white, Color.black, Color.black, Color.black, Color.black},
                           {Color.black, Color.black, Color.black, Color.white, Color.black, Color.white, Color.black, Color.black, Color.black, Color.black},
                           {Color.black, Color.black, Color.black, Color.white, Color.white, Color.white, Color.black, Color.black, Color.black, Color.black},
                           {Color.black, Color.black, Color.black, Color.black, Color.black, Color.black, Color.black, Color.black, Color.black, Color.black}};
   
   int value =0; 
   JMenuBar mb;
   JMenu mn1, mn2;
   JButton bt1;
      
   int gridRows=10, gridCols=10;
   JButton[][] bts = new JButton[gridRows][gridCols];
   
   public nonogram() {
      setTitle("nonogram 퍼즐 풀기");
      Container c = getContentPane();
      c.setLayout(null);
      this.setResizable(false);
      pn1 = new JPanel();
      
      pn1.setLayout(new GridLayout(10,10));
            
      for(int i=0;i<10;i++) {
         for(int j=0;j<10;j++) {
            bts[i][j] = new JButton();
            bts[i][j].addActionListener(this);
            bts[i][j].setBackground(Color.white);
            bts[i][j].addMouseListener(new MouseAdapter() { 
                 @Override
                 public void mouseReleased(MouseEvent e) {//마우스 버튼 클릭시 색상 변경 이벤트
               if(e.getButton()==MouseEvent.BUTTON1) {
                  if (e.getComponent().getBackground() == Color.black) {
                  e.getComponent().setBackground(Color.WHITE);
               } else {
                  e.getComponent().setBackground(Color.black);
               }
               }
                 }          
             });
               pn1.add(bts[i][j]);
               this.setVisible(true);
         }
      }
      
      
      JButton submit = new JButton("제   출");
      submit.setFont(new Font("맑은 고딕", Font.BOLD, 15));
      submit.setForeground(Color.white);
      submit.setSize(150, 60);
      submit.setLocation(300, 480);
      submit.setBackground(new Color(102, 153, 255));
      submit.setBorder(null);
      //2중 for문으로 X,Y좌표 탐색해 답안 확인 (작성예정)
      submit.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            int cnt = 0;
            for(int i=0; i<10; i++) {
               for(int j=0; j<10; j++) {
                     if(bts[i][j].getBackground() == ansarr[i][j])
                        cnt++;
               }
            }
            if(cnt==100) {
               JOP.showMessageDialog(null, "정답입니다");
            }
            else
               JOP.showMessageDialog(null, "틀렸습니다");
         }
      });
      c.add(submit);
      
      pn1.setLocation(50, 50);
      pn1.setSize(400,400);
      c.add(pn1);
      
      JButton ng = new JButton("새 게 임");
      ng.setFont(new Font("맑은 고딕", Font.BOLD, 15));
      ng.setForeground(Color.white);
      ng.setBackground(new Color(102, 153, 255));
      ng.setBorder(null);
      ng.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            for(int i=0;i<10;i++) {
               for(int j=0;j<10;j++) {
                  bts[i][j].setBackground(Color.white);
               }
            }
            pn1.setEnabled(false);
         }

      });
      
      
      this.addWindowListener(new WindowAdapter(){
          public void windowClosing(WindowEvent e) { 
          	jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          }
		});

      //위쪽 라벨
      for(int i=0; i<=9; i++) {
         JLabel lb = new JLabel(datar[i]);
         int dist = 60;
         lb.setLocation(dist+(40*i), 12);
         lb.setSize(60, 40);
         lb.setFont(new Font("맑은 고딕", Font.BOLD, 12));
         c.add(lb);
      }
      setVisible(true);
      
      
      //왼쪽 라벨
      for(int i=0; i<=9; i++) {
          JLabel lb = new JLabel(datac[i]);
          int dist = 50;
          lb.setLocation(7, dist+(40*i));
          lb.setSize(32, 40);
          lb.setFont(new Font("맑은 고딕", Font.BOLD, 12));
          lb.setHorizontalAlignment(JLabel.RIGHT);
          c.add(lb);
       }
      
      
      ng.setLocation(50, 480);
      ng.setSize(150, 60);
      c.add(ng);
      
      

      
      
      Dimension sc=Toolkit.getDefaultToolkit().getScreenSize();
      Dimension frm=super.getSize();
      int xpos = (int)(sc.getWidth()/2-frm.getWidth()/2);
      int ypos = (int)(sc.getHeight()/2-frm.getHeight()/2);
      super.setLocation(xpos, ypos);
      super.setVisible(true);
            
      super.setSize(515,620);
      this.setLocationRelativeTo(null);
      
      
   }   
   

   @Override
   public void actionPerformed(ActionEvent e) {
      // TODO Auto-generated method stub
      
   }

}