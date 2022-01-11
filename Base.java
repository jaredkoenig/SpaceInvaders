import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;

public class Base extends Ship {
    public enum Direction {
        LEFT, RIGHT
    }

    private Missile missile;
    private final Clip blaster = getSound("aud_basefire.wav");
    private final Clip hit = getSound("aud_hit.wav");
    private boolean left, right;
    private boolean dead;
    private int x = 220, y = 390;
    private int deadCount = 0;
    private Image i = new ImageIcon("src/img_base.gif").getImage();
    private int score = 0;
    String scoreString = "score: " + score;
    
    public void setAlive()
    {
        dead = false;
        i = new ImageIcon("src/img_base.gif").getImage();
        x = 220;
    }

    @Override
    public int getX()
    {
        return x;
    }

    @Override
    public int getY()
    {
        return y;
    }

    public int getScore() {
        return score;
    }

    public void addScore(int add) {
        score += add;
    }
    
    public boolean isDead()
    {
        return dead;
    }

    public boolean hasMissile() {
        return missile != null;
    }

    public Missile getMissile() {
        if (missile != null) {
            return missile;
        }
        return null;
    }

    @Override
    public void hitByMissile() {
        hitImage();
        hitSound();
        dead = true;
    }

    @Override
    public void hitImage() {
        i = new ImageIcon("src/img_basehit.gif").getImage();
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(i, x, y, null);
        if (missile != null) {
            missile.draw(g2);
        }
        scoreString = "score: " + score;
        g2.setColor(Color.GREEN);
        g2.drawString(scoreString, 425 - (score + "").length() * 7, 40);
    }

    @Override
    public void update() {
        if (left && x > 0) {
            x -= 5;
        }
        if (right && x < 460) {
            x += 5;
        }

        if (missile != null) {
            missile.update();
            if (!missile.isValid()) {
                missile = null;
            }
        }
    }

    public void shoot() {
        if (missile == null) {
            int mx = x + 12;
            int my = y;
            missile = new Missile(mx, my, this);
            blaster.setFramePosition(0);
            blaster.start();
        }
    }

    public void move(Direction dir, boolean pressed) {
        switch (dir) {
            case LEFT:
                left = pressed;
                break;
            case RIGHT:
                right = pressed;
                break;
        }
    }

    @Override
    public int deadCount() {
        return deadCount++;
    }

    @Override
    public void hitSound() {
        hit.setFramePosition(0);
        hit.start();
    }

}
