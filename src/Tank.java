public class Tank {
    //作为所有坦克(包括友方坦克和地方坦克)的父类
    private int x, y, direction, type, speed;
    public static final int DEFUALT_SPEED = 5;
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

    public void moveUp() {
        y = y - speed;
    }

    public void moveDown() {
        y = y + speed;
    }

    public void moveLeft() {
        x = x - speed;
    }

    public void moveRight() {
        x = x + speed;
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
}
