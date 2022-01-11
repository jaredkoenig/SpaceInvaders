public class Mystery extends Invader {
    public Mystery(int xi, int yi, Base base, String lvl, boolean left) {
        super(xi, yi, base, lvl);
        this.base = base;
        this.x = xi;
        this.y = yi;
        this.left = left;
        this.right = !left;
    }

    private boolean left, right;
    private boolean dead;
    private int x;
    private int y;
    private Base base;
    
    @Override
    public boolean isDead()
    {
        return dead;
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
    
    @Override
    public void hitByMissile() {
        if(base.hasMissile())
        {
            if (base.getMissile().getX() >= x
                    && base.getMissile().getX() <= x + 36
                    && base.getMissile().getY() >= y
                    && base.getMissile().getY() <= y + 18) {
                hitImage();
                hitSound();
                dead = true;
                base.getMissile().setValid(false);
                base.addScore(this.pointValue());
            }
        }
    }

    @Override
    public void update() {
        if (!dead) {
            hitByMissile();
            if (left && x < -20 || right && x > 475) {
                dead = true;
            }
            if (left && x >= 0) {
                x -= 1;
            }
            if (right && x <= 460) {
                x += 1;
            }
        }
        else
        {
            hitImage();
        }
    }

    @Override
    public int pointValue() {
        int chance = (int)(Math.random()*4);
        int num = 50;
        switch(chance)
        {
            case 0:
                num = 50;
                break;
            case 1:
                num = 100;
                break;
            case 2:
                num = 150;
                break;
            case 3:
                num = 300;
                break;
        }
        return num;
    }

}
