public class InvaderMiddle extends Invader {
    public InvaderMiddle(int xi, int yi, Base base) {
        super(xi, yi, base, "middle");
    }

    @Override
    public int pointValue() {
        return 20;
    }

}
