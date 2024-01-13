import java.util.Vector;

public class MyTank extends Tank {
    //我操控的坦克
    private Vector<Bullet> bullets = new Vector<>();

    public MyTank(int x, int y, int direction, int type, int speed) {
        super(x, y, direction, type, speed);
    }

    public void shot() {
        int initBulletX = getX() - 1;
        int initBulletY = getY() - 1;
        if (getDirection() == Tank.MOVE_UP) {
            initBulletX += 20;
        } else if (getDirection() == Tank.MOVE_LEFT) {
            initBulletY += 20;
        } else if (getDirection() == Tank.MOVE_DOWN) {
            initBulletX += 20;
            initBulletY += 60;
        } else if (getDirection() == Tank.MOVE_RIGHT) {
            initBulletX += 60;
            initBulletY += 20;
        }
        Bullet bullet = new Bullet(initBulletX, initBulletY, getDirection());
        bullet.start();
        bullets.add(bullet);
    }

    public Vector<Bullet> getBullets() {
        return bullets;
    }

    public void setBullets(Vector<Bullet> bullets) {
        this.bullets = bullets;
    }
}
