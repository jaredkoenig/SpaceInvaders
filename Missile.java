import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class Missile extends Drawable {
    private int x;
    private int y;
    private boolean valid;
    private boolean baseMissile = true;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Missile getMissile() {
        return this;
    }

    public Missile(int x, int y, Base b) {
        this.x = x;
        this.y = y;
        this.valid = true;
        baseMissile = true;
    }

    public Missile(int x, int y, Invader i) {
        this.x = x;
        this.y = y;
        this.valid = true;
        baseMissile = false;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean b) {
        valid = b;
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        g2.fill(new Rectangle2D.Double(x, y, 2, 10));
    }

    @Override
    public void update() {
        if (baseMissile) {
            if (valid) {
                y -= 5;
            }
            if (y < 0) {
                valid = false;
            }
        }
        else {
            if (valid) {
                y += 5;
            }
            if (y > 450) {
                valid = false;
            }
        }
    }
}