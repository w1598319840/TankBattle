import java.util.Vector;

public class EnemyTank extends Tank {
    Vector<Bullet> bullets = new Vector<>();
    public EnemyTank(int x, int y, int direction, int type, int speed) {
        super(x, y, direction, type, speed);
    }
}
