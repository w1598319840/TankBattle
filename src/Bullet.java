public class Bullet extends Thread {
    //子弹类
    private int x, y, speed = 10, direction;
    private boolean isLive = true;//用于标记子弹是否存活


    public Bullet(int x, int y, int direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    @Override
    public void run() {
        while (isLive) {
            if (this.x < 0 || this.y < 0 || this.x > 1000 || y > 750) {
                isLive = false;
            }
            if (direction == Tank.MOVE_UP) {
                moveUp();
            } else if (direction == Tank.MOVE_LEFT) {
                moveLeft();
            } else if (direction == Tank.MOVE_DOWN) {
                moveDown();
            } else if (direction == Tank.MOVE_RIGHT) {
                moveRight();
            }
            try {
                Thread.sleep(15);//休眠一下，不然子弹飞得太快了
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
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

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        isLive = live;
    }
}
