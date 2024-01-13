public class Bomb {
    //用于做出在坦克被击中之后，的爆炸效果
    private int x,y;
    private int life = 900; //爆炸的持续时间
    private boolean isLive = true;//炸弹是否还存活

    public Bomb(int x, int y) {
        this.x = x;
        this.y = y;
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

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public void lifeDown(){// 减少生命值
        life--;
    }

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        isLive = live;
    }
}
