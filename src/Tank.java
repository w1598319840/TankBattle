import java.util.Vector;

public class Tank {
    //作为所有坦克(包括友方坦克和地方坦克)的父类
    private int x, y, direction, type, speed;
    private boolean isLive = true;//用于标记坦克是否存活
    private Vector<Bullet> bullets = new Vector<>();//坦克的子弹数组

    public static final int TANK_DEFAULT_SPEED = 5;
    public static final int MOVE_UP = 0;
    public static final int MOVE_LEFT = 1;
    public static final int MOVE_DOWN = 2;
    public static final int MOVE_RIGHT = 3;
    public static final int MY_TANK = 0;
    public static final int ENEMY_TANK = 1;
    public static final int FRIENDLY_TANK = 2;

    public Tank(int x, int y, int direction, int type, int speed) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.type = type;
        this.speed = speed;
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
        Bullet bullet = new Bullet(initBulletX, initBulletY, getDirection(),Bullet.BULLET_DEFUALT_SPEED);
        bullet.start();
        bullets.add(bullet);
    }

    public void moveUp() {
        if(getY() > 0){
            y = y - speed;
        }
    }

    public void moveDown() {
        if(getY() + 60 < 711){
            y = y + speed;
        }
    }

    public void moveLeft() {
        if(getX() > 0){
            x = x - speed;
        }
    }

    public void moveRight() {
        if(getX() + 60 < 984){
            x = x + speed;
        }
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        isLive = live;
    }

    public Vector<Bullet> getBullets() {
        return bullets;
    }

    public void setBullets(Vector<Bullet> bullets) {
        this.bullets = bullets;
    }
}
