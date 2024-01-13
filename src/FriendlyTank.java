import java.util.Vector;

public class FriendlyTank extends Tank implements Runnable {
    //友军坦克
    public FriendlyTank(int x, int y, int direction, int type, int speed) {
        super(x, y, direction, type, speed);
    }
    public void run() {
        while (isLive()) {
            //现根据坦克现在的方向移动随机距离
            int direction = getDirection();
            int distance = (int) (Math.random() * 6);
            if (direction == Tank.MOVE_UP) {
                for (int i = 0; i < distance; i++) {
                    moveUp();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            } else if (direction == Tank.MOVE_LEFT) {
                for (int i = 0; i < distance; i++) {
                    moveLeft();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            } else if (direction == Tank.MOVE_DOWN) {
                for (int i = 0; i < distance; i++) {
                    moveDown();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            } else if (direction == Tank.MOVE_RIGHT) {
                for (int i = 0; i < distance; i++) {
                    moveRight();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            //然后在随机地改变方向
            int newDirection = (int) (Math.random() * 9);
            if (newDirection >= 0 && newDirection < 3) {
                setDirection(Tank.MOVE_UP);
            } else if (newDirection >= 3 && newDirection < 5) {
                setDirection(Tank.MOVE_LEFT);
            } else if (newDirection >= 5 && newDirection < 7) {
                setDirection(Tank.MOVE_RIGHT);
            } else if (newDirection >= 7) {
                setDirection(Tank.MOVE_DOWN);
            }
            //控制随机发射子弹
            int isShot = (int) (Math.random() * 3);
            if(isShot == 0){
                shot();
            }
        }
    }
}
