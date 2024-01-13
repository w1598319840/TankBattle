import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Vector;


public class GameInterfacePanel extends JPanel implements KeyListener, Runnable {
    //游戏界面的Panel
    private MyTank myTank; //一个自己的坦克，直接实例化
    private Vector<EnemyTank> enemyTanks = new Vector<>();// 多个敌方坦克，用 Vector 存储(因为后续要考虑多线程)
    private Vector<FriendlyTank> friendlyTanks = new Vector<>();


    public GameInterfacePanel() {
        //添加我的坦克
        myTank = new MyTank(100, 600, Tank.MOVE_UP, Tank.MY_TANK, Tank.TANK_DEFUALT_SPEED);
        //添加敌人坦克
        for (int i = 0; i < 3; i++) {
            EnemyTank enemyTank = new EnemyTank
                    (200 * (i + 1), 0, Tank.MOVE_DOWN, Tank.ENEMY_TANK, Tank.TANK_DEFUALT_SPEED);
            enemyTanks.add(enemyTank);
        }
        //添加友军坦克
        for (int i = 0; i < 1; i++) {
            FriendlyTank friendlyTank = new FriendlyTank
                    (400 * (i + 1), 600, Tank.MOVE_UP, Tank.FRIENDLY_TANK, Tank.TANK_DEFUALT_SPEED);
            friendlyTanks.add(friendlyTank);
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        //setBackground(new Color(0,0,0));
        g.fillRect(0, 0, 1000, 750);//绘制一个填充的矩形，颜色默认是黑色
        //把整个界面都改成黑色，黑色的就是游戏区域
        //画自己的坦克
        drawTank(g, myTank.getX(), myTank.getY(), myTank.getDirection(), myTank.getType());
        //画敌人的坦克
        for (EnemyTank enemyTank : enemyTanks) {
            drawTank(g, enemyTank.getX(), enemyTank.getY(), enemyTank.getDirection(), enemyTank.getType());
        }
        //画出友方坦克
        for (FriendlyTank friendlyTank : friendlyTanks) {
            drawTank(g, friendlyTank.getX(), friendlyTank.getY(), friendlyTank.getDirection(), friendlyTank.getType());
        }
        //画出我的子弹
        drawBullets(g, myTank.getBullets());
        //画出敌方子弹
        for (EnemyTank enemyTank : enemyTanks) {
            drawBullets(g, enemyTank.getBullets());
        }
        //画出友方子弹
        for (FriendlyTank friendlyTank : friendlyTanks) {
            drawBullets(g, friendlyTank.getBullets());
        }
    }

    /**
     * @param g         画笔
     * @param x         坦克的横坐标
     * @param y         坦克的纵坐标
     * @param direction 坦克的方向(上下左右)
     * @param type      坦克的类型(可以是颜色不同，种类不同……)
     */
    public void drawTank(Graphics g, int x, int y, int direction, int type) {
        //先根据 type 来确定坦克类型
        if (type == Tank.MY_TANK) { //用 0 表示我的坦克
            g.setColor(new Color(255, 228, 225));
        } else if (type == Tank.ENEMY_TANK) { //用 1 表示敌人的坦克
            g.setColor(new Color(255, 0, 0));
        } else if (type == Tank.FRIENDLY_TANK) { //用 2 表示友军坦克
            g.setColor(new Color(124, 252, 0));
        }

        //再根据 direction 来确定坦克的方向,从而绘画不同的坦克
        if (direction == Tank.MOVE_UP) { // 0 表示 向上
            //使用 3D 方法可以让绘画出来的坦克更有立体感
            g.fill3DRect(x, y, 10, 60, false);//左轮
            g.fill3DRect(x + 30, y, 10, 60, false);//右轮
            g.fill3DRect(x + 10, y + 10, 20, 40, false);//中间的正方形
            g.fillOval(x + 9, y + 20, 20, 20);//坦克盖
            g.drawLine(x + 19, y + 30, x + 19, y);//炮管
        } else if (direction == Tank.MOVE_LEFT) {// 1 表示 向左
            g.fill3DRect(x, y, 60, 10, false);//上轮
            g.fill3DRect(x, y + 30, 60, 10, false);//下轮
            g.fill3DRect(x + 10, y + 10, 40, 20, false);//中间的正方形
            g.fillOval(x + 20, y + 9, 20, 20);//坦克盖
            g.drawLine(x + 30, y + 19, x, y + 19);//炮管
        } else if (direction == Tank.MOVE_DOWN) {// 2 表示 向下
            g.fill3DRect(x, y, 10, 60, false);//左轮
            g.fill3DRect(x + 30, y, 10, 60, false);//右轮
            g.fill3DRect(x + 10, y + 10, 20, 40, false);//中间的正方形
            g.fillOval(x + 9, y + 20, 20, 20);//坦克盖
            g.drawLine(x + 19, y + 30, x + 19, y + 60);//炮管
        } else if (direction == Tank.MOVE_RIGHT) {// 3 表示 向右
            g.fill3DRect(x, y, 60, 10, false);//上轮
            g.fill3DRect(x, y + 30, 60, 10, false);//下轮
            g.fill3DRect(x + 10, y + 10, 40, 20, false);//中间的正方形
            g.fillOval(x + 20, y + 9, 20, 20);//坦克盖
            g.drawLine(x + 30, y + 19, x + 60, y + 19);//炮管
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        char key = e.getKeyChar();
        if (key == 'w' && myTank.getY() > 0) {
            myTank.moveUp();
            myTank.setDirection(Tank.MOVE_UP);
        } else if (key == 'a' && myTank.getX() > 0) {
            myTank.moveLeft();
            myTank.setDirection(Tank.MOVE_LEFT);
        } else if (key == 's' && myTank.getY() + 60 < getHeight()) {
            myTank.moveDown();
            myTank.setDirection(Tank.MOVE_DOWN);
        } else if (key == 'd' && myTank.getX() + 60 < getWidth()) {
            myTank.moveRight();
            myTank.setDirection(Tank.MOVE_RIGHT);
        } else if (key == 'j') {
            myTank.shot();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public void drawBullets(Graphics g, Vector<Bullet> bullets) {
        ArrayList<Bullet> removeBullets = new ArrayList<>();// 要删除的子弹的集合
        if (bullets == null) {
            return;
        }
        for (Bullet bullet : bullets) {
            if (bullet != null && bullet.isLive()) {
                g.setColor(new Color(255, 255, 255));
                g.drawRect(bullet.getX(), bullet.getY(), 1, 1);
            } else if (bullet != null && !(bullet.isLive())) {
                removeBullets.add(bullet);
            }
        }
        bullets.removeAll(removeBullets);
    }

    @Override
    public void run() {
        while (true) {
            repaint(15);
        }
    }
}
