public class InvaderTop extends Invader {
    public InvaderTop(int xi, int yi, Base base) {
        super(xi, yi, base, "top");
    }

    @Override
    public int pointValue() {
        return 30;
    }

}
