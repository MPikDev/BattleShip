import javax.swing.JButton;
import java.awt.Color;
import java.awt.Dimension;

public class Button  extends JButton
{
  private int x;
  private int y;
  private final short W = 40;
  private final short L = 40;

  public Button (int x, int y)
  {
    this.x = x;
    this.y = y;
    setBorderPainted(false);
    setForeground(Color.blue);
    setSize(new Dimension(W, L));
    setOpaque(true);
  }
}
