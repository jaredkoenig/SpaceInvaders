public class InvaderBottom extends Invader {
    public InvaderBottom(int xi, int yi, Base base) {
        super(xi, yi, base, "bottom");
    }

    @Override
    public int pointValue() {
        return 10;
    }

}
