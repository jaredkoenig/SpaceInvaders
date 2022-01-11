import java.awt.Graphics2D;
import java.awt.Image;

import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;

public abstract class Invader extends Ship {
    public abstract int pointValue();

    private Missile missile;
    private final Clip hit = getSound("aud_hit.wav");
    private boolean left, right = true, ab = true, bb, sw, change, dead;
    private String level = "";
    private Image a = new ImageIcon("src/img_invader" + level + "A.gif")
            .getImage();
    private Image b = new ImageIcon("src/img_invader" + level + "B.gif")
            .getImage();
    private Image i = a;
    private int x = 220, y = 10, speed = 5;
    private Base base;
    private int delayCount = 0;
    private int deadCount = 0;

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    public Base getBase() {
        return base;
    }

    public int getDelayCount() {
        return delayCount;
    }

    public boolean isDead() {
        return dead;
    }

    public Missile getMissile() {
        return missile;
    }

    @Override
    public void hitSound() {
        hit.setFramePosition(0);
        hit.start();
    }

    @Override
    public int deadCount() {
        return deadCount++;
    }

    public boolean hasMissile() {
        return missile != null;
    }

    public boolean getChange() {
        return change;
    }

    public void changeDirection() {
        right = !right;
        left = !left;

        if (left) {
            y += 19;
        }
    }

    public void changeImage() {
        sw = true;
    }

    public Invader(int xi, int yi, Base base, String lvl) {
        x = xi;
        y = yi;
        level = lvl;
        if (!lvl.equals("mystery")) {
            a = new ImageIcon("src/img_invader" + level + "A.gif").getImage();
            b = new ImageIcon("src/img_invader" + level + "B.gif").getImage();
            i = a;
        }
        if (lvl.equals("mystery")) {
            i = a = b = new ImageIcon("src/img_mystery.gif").getImage();
            int chance = (int) (Math.random() * 2);
            switch (chance) {
                case 0:
                    left = true;
                    right = false;
                    x = 450;
                    y = 30;
                    break;
                case 1:
                    left = false;
                    right = true;
                    x = 0;
                    y = 30;
                    break;
            }
        }
        this.base = base;
    }

    @Override
    public void hitByMissile() {
        if (base.hasMissile() && !dead) {
            if (base.getMissile().getX() + 1 > x
                    && base.getMissile().getX() + 1 < x + 30
                    && base.getMissile().getY() > y
                    && base.getMissile().getY() < y + 24) {
                hitImage();
                hitSound();
                dead = true;
                base.getMissile().setValid(false);
                base.addScore(this.pointValue());
            }
        }
    }

    @Override
    public void hitImage() {
        i = a = b = new ImageIcon("src/img_invaderhit.gif").getImage();
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(i, x, y, null);
        if (missile != null) {
            missile.draw(g2);
        }
        if (sw) {
            sw = false;
            i = ab ? a : b;
            ab = !ab;
            bb = !bb;
        }
    }

    @Override
    public void update() {
        if (!dead) {
            delayCount++;
            hitByMissile();
            sw = false;
            if (left && x <= 0 || right && x >= 460) {
                change = true;

                if (x <= 0) {
                    x += 2 * speed;
                }
                else {
                    x += speed;
                }
            }
            else {
                change = false;
            }
            if (left && x > 0) {
                x -= speed;
            }
            if (right && x < 460) {
                x += speed;
            }
            
            sw = false;
        }
        else {
            hitImage();
        }
    }

    public void updateMissile() {
        if (missile != null) {
            missile.update();
            if (!missile.isValid()) {
                missile = null;
            }
        }
    }

    public void shoot() {
        if (missile == null) {
            int mx = x + 15;
            int my = y + 12;
            missile = new Missile(mx, my, this);
            // blaster.setFramePosition(0);
            // blaster.start();
        }
    }
}
