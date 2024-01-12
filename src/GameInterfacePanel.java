import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameInterfacePanel extends JPanel implements KeyListener {
    //游戏界面的Panel
    private MyTank myTank = new MyTank(100, 100, 0, 0);//我的坦克
    private EnemyTank enemyTank1 = new EnemyTank(200, 100, 0, 1);//敌军坦克
    private EnemyTank enemyTank2 = new EnemyTank(300, 100, 0, 1);//敌军坦克
    private FriendlyTank friendlyTank = new FriendlyTank(400, 100, 0, 2);//友军坦克

    public GameInterfacePanel() {

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        //setBackground(new Color(0,0,0));
        g.fillRect(0, 0, 1000, 750);//绘制一个填充的矩形，颜色默认是黑色
        //把整个界面都改成黑色，黑色的就是游戏区域

        drawTank(g, myTank.getX(), myTank.getY(), myTank.getDirection(), myTank.getType());
//        drawTank(g, enemyTank1.getX(), enemyTank1.getY(), 1, 1);
//        drawTank(g, enemyTank2.getX(), enemyTank2.getY(), 2, 1);
//        drawTank(g, friendlyTank.getX(), friendlyTank.getY(), 3, 2);
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
        if (type == 0) { //用 0 表示我的坦克
            g.setColor(new Color(255, 228, 225));
        } else if (type == 1) { //用 1 表示敌人的坦克
            g.setColor(new Color(255, 0, 0));
        } else if (type == 2) { //用 2 表示友军坦克
            g.setColor(new Color(124, 252, 0));
        }

        //再根据 direction 来确定坦克的方向,从而绘画不同的坦克
        if (direction == 0) { // 0 表示 向上
            //使用 3D 方法可以让绘画出来的坦克更有立体感
            g.fill3DRect(x, y, 10, 60, false);//左轮
            g.fill3DRect(x + 30, y, 10, 60, false);//右轮
            g.fill3DRect(x + 10, y + 10, 20, 40, false);//中间的正方形
            g.fillOval(x + 9, y + 20, 20, 20);//坦克盖
            g.drawLine(x + 19, y + 30, x + 19, y);//炮管
        } else if (direction == 1) {// 1 表示 向左
            g.fill3DRect(x, y, 60, 10, false);//上轮
            g.fill3DRect(x, y + 30, 60, 10, false);//下轮
            g.fill3DRect(x + 10, y + 10, 40, 20, false);//中间的正方形
            g.fillOval(x + 20, y + 9, 20, 20);//坦克盖
            g.drawLine(x + 30, y + 19, x, y + 19);//炮管
        } else if (direction == 2) {// 2 表示 向下
            g.fill3DRect(x, y, 10, 60, false);//左轮
            g.fill3DRect(x + 30, y, 10, 60, false);//右轮
            g.fill3DRect(x + 10, y + 10, 20, 40, false);//中间的正方形
            g.fillOval(x + 9, y + 20, 20, 20);//坦克盖
            g.drawLine(x + 19, y + 30, x + 19, y + 60);//炮管
        } else if (direction == 3) {// 3 表示 向右
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
        if (key == 'w') {
            int tempY = myTank.getY() - 5;
            myTank.setY(tempY);
            myTank.setDirection(0);
        } else if (key == 'a') {
            int tempX = myTank.getX() - 5;
            myTank.setX(tempX);
            myTank.setDirection(1);
        } else if (key == 's') {
            int tempY = myTank.getY() + 5;
            myTank.setY(tempY);
            myTank.setDirection(2);
        } else if (key == 'd') {
            int tempX = myTank.getX() + 5;
            myTank.setX(tempX);
            myTank.setDirection(3);
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
