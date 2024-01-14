import java.util.Vector;

public class MyTank extends Tank {
    //我操控的坦克
    private static int lifeCount = 3;//我的坦克生命数
    private static int hitCount = 0;//我的坦克击杀数量
    public MyTank(int x, int y, int direction, int type, int speed) {
        super(x, y, direction, type, speed);
    }

    public static int getHitCount() {
        return hitCount;
    }

    public static void setHitCount(int hitCount) {
        MyTank.hitCount = hitCount;
    }

    public static int getLifeCount() {
        return lifeCount;
    }

    public static void setLifeCount(int lifeCount) {
        MyTank.lifeCount = lifeCount;
    }

    public static void LifeCountDown(){
        lifeCount--;
    }

    public MyTank newMyTank(){
        MyTank myTank = new MyTank(100, 600, Tank.MOVE_UP, Tank.MY_TANK, Tank.TANK_DEFAULT_SPEED);
        return myTank;
    }

    public static void hitCountUp(){
        hitCount++;
    }
}
