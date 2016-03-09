

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class  main{
	
	public static void main (String[] args)
{   
		final Display frame = Display.getInstance(); 
		frame.makeBoard();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame.setSize(1100, 800);
					frame.getContentPane().setForeground(Color.BLACK);
					frame.setVisible(true);
				}
				catch (Exception e) {
				e.printStackTrace();
				}
			}
		});
	}
}