import java.awt.Graphics2D;
import java.awt.Image;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;

public abstract class Drawable {
    private int x = 0, y = 0, width = 10, height = 10;
    
    public int getX()
    {
        return x;
    }
    public int getY()
    {
        return y;
    }
    public int getWidth()
    {
        return width;
    }
    public int getHeight()
    {
        return height;
    }
    
    public abstract void draw(Graphics2D g2);
    public abstract void update();
    
    protected Clip getSound(String filename) {
        Clip clip = null;
        try {
            InputStream in  = getClass().getResourceAsStream( filename ); 
            InputStream buf = new BufferedInputStream( in );
            AudioInputStream stream = AudioSystem.getAudioInputStream( buf );
            clip = AudioSystem.getClip();
            clip.open( stream );
        } catch (UnsupportedAudioFileException |
                IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
        return clip;
    }
    protected Image getImage(String filename) {
        URL url = getClass().getResource( filename );
        ImageIcon icon = new ImageIcon( url );
        return icon.getImage();
    }

}