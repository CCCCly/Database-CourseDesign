package pub;
import javax.swing.*;
public class Welcome extends JLabel
{
   ImageIcon icon  = new ImageIcon("images/xiaohui.jpg");
   public Welcome()
   {
	 //  icon.setImage(icon.getImage().getScaledInstance(2000,1000, 0));
	   icon.setImage(icon.getImage().getScaledInstance(1000,1000, 0));
       this.initialFrame();
   }
   public void initialFrame()
   {
	   this.setIcon(icon);
	   this.setBounds(0,0,100,100);
	   this.setHorizontalAlignment(JLabel.CENTER);
  	   this.setVerticalAlignment(JLabel.CENTER);
   }
}
