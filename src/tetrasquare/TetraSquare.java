package tetrasquare;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

public class TetraSquare extends JFrame implements TableCellRenderer{
	
	JFrame jf = new JFrame();
	
    JPanel p1 = new JPanel();
    JPanel p2 = new JPanel();
    JPanel p3 = new JPanel();
    JPanel p4 = new JPanel();
    

    JLabel l1 = new JLabel("TETRASQUARE");  
    JButton bt1 = new JButton("FINISH");
    JLabel lb_result = new JLabel(" RESULT?!");

    //����
   //11x11
   private String[][] data4 = {
              {"","","3","","","","","","","",""},
              {"4","","3","","","","","","","",""},
              {"","","10","","","","","","18","",""},
              {"","","","","","3","","","3","",""},
              {"","","","","10","","","","","","2"},
              {"","2","","","","8","","","","",""},
              {"","","","5","","","","","","",""},
              {"","","","","20","5","","4","","6",""},
              {"","","","","","","3","","2","",""},
              {"","","","","","","","","","",""},
              {"","","","","","","10","","","",""},
   };
   
   //���
   //��� ���� �⺻ �� 0
   //11x11
   private int[][] answer_11_2 = {
         {0,0,0,0,0,0,0,0,0,0,0},    
         {0,0,0,0,0,0,0,0,0,0,0},      
         {0,0,0,0,0,0,0,0,0,0,0},      
         {0,0,0,0,0,0,0,0,0,0,0},      
         {0,0,0,0,0,0,0,0,0,0,0},      
         {0,0,0,0,0,0,0,0,0,0,0},      
         {0,0,0,0,0,0,0,0,0,0,0},      
         {0,0,0,0,0,0,0,0,0,0,0},      
         {0,0,0,0,0,0,0,0,0,0,0},      
         {0,0,0,0,0,0,0,0,0,0,0},      
         {0,0,0,0,0,0,0,0,0,0,0},      
   };

    //-----------------�𵨰� ������ ����---------------------   
   //   ���� ���� �Ұ� 
   DefaultTableModel model = new DefaultTableModel(data4, data4) {
    public boolean isCellEditable(int i, int k) {
       return false;
    }
   };
      
   Random rand = new Random();
    float r;
    float g;
    float b;
    Color randomColor1;
    Color randomColor2;
    
    int x1; int y1;
    int x2; int y2;

    
//----------------------------------------------------------------------------------   
   public TetraSquare() {
      setTitle("GAME");
      Container c = getContentPane();
      
      c.setLayout(new BorderLayout());
      
      //â ũ��
      this.setLocation(700, 250);
      this.setSize(1000, 800);
      this.setVisible(true);  
      this.setResizable(true);
      
      this.addWindowListener(new WindowAdapter(){
          public void windowClosing(WindowEvent e) { 
          	jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          }
		});


      //�ǳ�
       add(p1,"North"); 
       p1.add(l1); //�ǳ�1�� ��1 �߰� 
       setSize(500, 510); // ������ ������ ����
       setVisible(true); // �������� ���̰� ��
       l1.setFont(new Font("Arial Black",Font.BOLD,15));
       
       add(p2,"East");
       p2.setLayout(new GridLayout(6,1));
       p2.add(bt1);
       p2.add(lb_result);
       p2.setVisible(true);
       bt1.setFont(new Font("Arial Black",Font.PLAIN,11));
       lb_result.setFont(new Font("HY�߰��",Font.PLAIN,13));
       
       add(p3,"West");
       p3.setLayout(new FlowLayout());
       p3.setVisible(true);
       
       add(p4,"Center");
       p4.setLayout(new BorderLayout());
       

       //-------------------------���̺�-------------------------
       JTable table = new JTable(model);
       p4.add(table);
       p4.setVisible(true);
       p4.setBorder(BorderFactory.createLineBorder(Color.red));
       
       table.setRowHeight(40); //�� ����
       table.getTableHeader().setReorderingAllowed(false); //�̵� �Ұ� ?
       table.setColumnSelectionAllowed(true); //�� ����!
              
       //  ������ ��� ����     
       DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
       dtcr.setHorizontalAlignment(SwingConstants.CENTER);
       TableColumnModel tcm = table.getColumnModel();
       for(int i=0; i<tcm.getColumnCount();i++) {
          tcm.getColumn(i).setCellRenderer(dtcr);
       }
       
//-----------���� ���� TEST-------------
  
//       if(isSelected) {
//          this.setBackground(randomColor1);
//       }
       
//-------------------TABLE MouseEvent--------------------------         
         table.addMouseListener(new MouseListener() {

         @Override
         public void mouseClicked(MouseEvent e) {
         }

         @Override
         public void mousePressed(MouseEvent e) {
            table.setSelectionBackground(Color.LIGHT_GRAY);
            
            //���õ� ���� ��ġ
            x1 = table.rowAtPoint(e.getPoint());
            y1 = table.columnAtPoint(e.getPoint());
         }

         @Override
         public void mouseReleased(MouseEvent e) {
            r  = rand.nextFloat();
            g  = rand.nextFloat();
            b  = rand.nextFloat();
            Color randomColor1  = new Color(r, g, b);
            randomColor1 = new Color(r,g,b);
            table.setSelectionBackground(randomColor1);
                        
            x2 = table.rowAtPoint(e.getPoint());
            y2 = table.columnAtPoint(e.getPoint());
            
            
            
            //���õ� ��          
            for (int i=x1; i<=x2; i++) {
               for (int k=y1; k<=y2; k++){   
                  // ���õ� �� ������ �� 1 ����   
                  answer_11_2[i][k] = 1;
                  
                  //���õ� �� ���� ��氪 ���� (�ϰ�ʹ�)------------------
//                  DefaultTableCellRenderer render1 = (DefaultTableCellRenderer) table.getDefaultRenderer(getClass());
//                  render1.setBackground(randomColor1);
                  
//                  TableCellRenderer render2 = table.getCellRenderer(i, k);
//                  render2.setBackground(randomColor1);
                  
                  TableCellRenderer render = table.getCellRenderer(i, k);
//                  Component comp = table.prepareRenderer(render, i, k);
                  
//                  render.setBackground(randomColor1);
//                  table.setBackground(randomColor1);
                  
                  
               }
            }
            
            //���� �� ������ Ȯ��
            for (int i=0; i<answer_11_2.length; i++) {
               for (int k=0; k<answer_11_2.length; k++){   
//                  System.out.print(answer_11_2[i][k]);
               }
            }
         }


         @Override
         public void mouseEntered(MouseEvent e) {}

         @Override
         public void mouseExited(MouseEvent e) {}
                     
         });
         
//---------------------FINISH BUTTON MOUSELISTENER------------------   
// ��ư�� ���� ��� ����
         bt1.addMouseListener(new MouseListener () {

         @Override
         public void mouseClicked(MouseEvent e) {
            //��� ���� ���� 1�� �� = ��� ���� ���� 1*��� ĭ�� ���� ��
            int sum = 0;
            for (int i=0; i<answer_11_2.length; i++) {
               for (int k=0; k<answer_11_2.length; k++){   
                  sum += answer_11_2[i][k];
               }
            }
//            System.out.println("�հ� :" +sum);
            
            if (sum == answer_11_2.length*answer_11_2.length){
               lb_result.setText(" �����Դϴ�");
            }
            else {
               lb_result.setText(" �����Դϴ�");
            }
         }

         @Override
         public void mousePressed(MouseEvent e) {   
         }

         @Override
         public void mouseReleased(MouseEvent e) {   
         }

         @Override
         public void mouseEntered(MouseEvent e) {   
         }

         @Override
         public void mouseExited(MouseEvent e) {   
         }
            
         });

   }



   //--------------TableCellRenderer-----------------------------
@Override
public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
      int row, int column) {
   // TODO Auto-generated method stub
//   Component cell = this.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);


//   if(isSelected) {
//      this.setBackground(randomColor1);
//   }
//   else {
//      this.setBackground(table.getBackground());
//   }
   
   return this;
}




}
