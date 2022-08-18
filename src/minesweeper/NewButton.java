package minesweeper;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

public class NewButton extends Spot implements ActionListener, MouseListener{
	
	private static final int size = 32;
	private JButton btn;
	private Image img;
	private int state;
	
	public NewButton() {
		super();
		btn = new JButton();
		setState(0);
		btn.setPreferredSize(new Dimension(size, size));
		btn.setMaximumSize(btn.getPreferredSize());
		
		btn.addActionListener(this);
		btn.addMouseListener(this);
		setImg();
		
	}
	
	public NewButton(int a, int b) {
		super(a,b);
		btn = new JButton();
		setState(0);
		btn.setPreferredSize(new Dimension(size, size));
		btn.setMaximumSize(btn.getPreferredSize());
		
		btn.addActionListener(this);
		btn.addMouseListener(this);
		setImg();
	}
	

	public static int getSize() {
		return size;
	}

	public JButton getBtn() {
		return btn;
	}

	public Image getImg() {
		return img;
	}

	public int getState() {
		return state;
	}

	public void setBtn(JButton btn) {
		this.btn = btn;
	}

	public void setImg() {
		String name;
		switch(state) {
		
			case 0:
				name = "blank"; break;
			case 1:
				name = "open" + getVal(); break;
			case 2:
				name = "bombdeath"; break;
			case 3:
				name = "bombflagged"; break;
			case 4:
				name = "bombrevealed"; break;
			case 5:
				name = "bombisflagged"; break;
			default:
				name = "blank"; break;
			
		}
		
		try {
			img = ImageIO.read(new File("src/minesweeper/images/"+name+".gif"));
			btn.setIcon(new ImageIcon(img));
		}
		catch (IOException e) {
			System.out.println("이미지 파일을 불러올 수 없습니다");
		}
	}

	public void setState(int st) {
		this.state = st;
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		GUIMine.setFace(1);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		btn.getModel().setArmed(false);
		btn.getModel().setPressed(false);
		
		if(SwingUtilities.isRightMouseButton(e)) {
			if(getState() == 3) {
				GUIMine.setFace(0);
				setState(0);
				GUIMine.decreaseFlags();
			}
			else if(getState()==0 && GUIMine.getFlags() > 0) {
				GUIMine.setFace(0);
				setState(3);
				GUIMine.increaseFlags();
			}
			setImg();
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(!GUIMine.hasStarted()) {
			GUIMine.setStarted(true);
			GUIMine.startTime();
		}
		
		GUIMine.setFace(0);
		
		if(getMine()) {
			setState(2);
			GUIMine.lost();
		}
		else {
			if(getVal() == 0) GUIMine.expandEmpty(getX(), getY());
			setState(1);
		}
		setImg();
	}
	
	
}
