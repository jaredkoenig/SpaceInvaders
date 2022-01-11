import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class SpaceInvaders extends JFrame {

    public SpaceInvaders() {
        super("Space Invaders");
        
        Panel panel = new Panel();
        add(panel);
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                panel.stopTimer();
                int result = JOptionPane.showConfirmDialog(SpaceInvaders.this,
                        "Dare to Quit?");
                if (result == JOptionPane.YES_OPTION) {
                    dispose();
                }
            }           
        });
        
        setSize(500,450);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    }
    
    public static void main(String[] args)
    {
        SpaceInvaders s = new SpaceInvaders();
        s.setIconImage(new ImageIcon("src/img_invaderbottomA.gif").getImage());
        s.setVisible(true);
    }
}
