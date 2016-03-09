import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

class ImagePanel extends JPanel {
  private Image img;
  private int x = 1000;
  private int y =1000;
  private int w = 2000;
  private int h =2000;

  public ImagePanel(String img) {
    this(new ImageIcon(img).getImage());
    this.x= 1000;
    this.y =1000;
  }
  public ImagePanel(String img,int h,int w, int x, int y) {
	    this(new ImageIcon(img).getImage());
	    this.x= x;
	    this.y =y;
	    this.h= h;
	    this.w =w;
	  }
  public ImagePanel(Image img) {
    this.img = img;
  }
  public void paintComponent(Graphics g) {
	    g.drawImage(img, 0,0,w,h,0,0, x,y,null);
	  }
}