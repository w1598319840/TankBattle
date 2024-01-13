import java.util.Vector;

public class FriendlyTank extends Tank {
    Vector<Bullet> bullets = new Vector<>();
    public FriendlyTank(int x, int y, int direction, int type, int speed) {
        super(x, y, direction, type, speed);
    }

    public Vector<Bullet> getBullets() {
        return bullets;
    }

    public void setBullets(Vector<Bullet> bullets) {
        this.bullets = bullets;
    }
}
