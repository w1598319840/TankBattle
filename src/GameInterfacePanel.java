import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;


public class GameInterfacePanel extends JPanel implements KeyListener, Runnable {
    //游戏界面的Panel
    private MyTank myTank; //一个自己的坦克，直接实例化
    Vector<MyTank> myTanks = new Vector<>();
    private Vector<EnemyTank> enemyTanks = new Vector<>();// 多个敌方坦克，用 Vector 存储(因为后续要考虑多线程)
    private Vector<FriendlyTank> friendlyTanks = new Vector<>();
    private Vector<Tank> allTanks = new Vector<>();
    private Vector<Bomb> bombs = new Vector<>();

    public GameInterfacePanel() {
        //添加我的坦克
        myTank = new MyTank(100, 600, Tank.MOVE_UP, Tank.MY_TANK, Tank.TANK_DEFAULT_SPEED);
        myTanks.add(myTank);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        //setBackground(new Color(0,0,0));
        g.fillRect(0, 0, 1000, 750);//绘制一个填充的矩形，颜色默认是黑色
        g.setColor(new Color(140, 140, 140));
        g.fillRect(1000, 0, 300, 750);
        //黑色的就是游戏区域,白的是信息显示区域
        //游戏区域信息：
        //画自己的坦克
        if (myTank.isLive()) {
            drawTank(g, myTank.getX(), myTank.getY(), myTank.getDirection(), myTank.getType());
        } else {
            myTanks.remove(myTank);
        }
        //画敌人的坦克
        for (int i = 0; i < enemyTanks.size(); i++) {
            EnemyTank enemyTank = enemyTanks.get(i);
            if (enemyTank.isLive()) {
                drawTank(g, enemyTank.getX(), enemyTank.getY(), enemyTank.getDirection(), enemyTank.getType());
            } else {
                enemyTanks.remove(enemyTank);
            }
        }
        //画出友方坦克
        for (int i = 0; i < friendlyTanks.size(); i++) {
            FriendlyTank friendlyTank = friendlyTanks.get(i);
            if (friendlyTank.isLive()) {
                drawTank(g, friendlyTank.getX(), friendlyTank.getY(), friendlyTank.getDirection(), friendlyTank.getType());
            } else {
                friendlyTanks.remove(friendlyTank);
            }
        }
        //画出我的子弹
        drawBullets(g, myTank.getBullets());
        //画出敌方子弹
        for (int i = 0; i < enemyTanks.size(); i++) {
            EnemyTank enemyTank = enemyTanks.get(i);
            drawBullets(g, enemyTank.getBullets());
        }
        //画出友方子弹
        for (int i = 0; i < friendlyTanks.size(); i++) {
            FriendlyTank friendlyTank = friendlyTanks.get(i);
            drawBullets(g, friendlyTank.getBullets());
        }
        //画出爆炸效果
        for (int i = 0; i < bombs.size(); i++) {
            Bomb bomb = bombs.get(i);
            if (bomb.isLive()) {
                drawBomb(g, bomb);
            } else {
                bombs.remove(bomb);
            }
        }
        //更新 allTanks 集合
        allTanks.clear();
        allTanks.addAll(myTanks);
        allTanks.addAll(enemyTanks);
        allTanks.addAll(friendlyTanks);
        Tank.setAllTanks(allTanks);
        //右侧提示信息：
        //您累计击杀的坦克：
        showHitCount(g);
        //我的坦克的生命数
        showMyTankLifeCount(g);
        //友军坦克数量
        showFriendlyTankCount(g);
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
        //每个坦克正面宽度为 40， 轮长 60
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
        if (key == 'w' && myTank.isLive()) {
            myTank.moveUp();
            myTank.setDirection(Tank.MOVE_UP);
        } else if (key == 'a' && myTank.isLive()) {
            myTank.moveLeft();
            myTank.setDirection(Tank.MOVE_LEFT);
        } else if (key == 's' && myTank.isLive()) {
            myTank.moveDown();
            myTank.setDirection(Tank.MOVE_DOWN);
        } else if (key == 'd' && myTank.isLive()) {
            myTank.moveRight();
            myTank.setDirection(Tank.MOVE_RIGHT);
        } else if (key == 'j' && myTank.isLive()) {
            if (myTank.getBullets().size() <= 4 && myTank.isLive()) {
                myTank.shot();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public void drawBullets(Graphics g, Vector<Bullet> bullets) {
        if (bullets == null) {
            return;
        }
        for (int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);
            if (bullet != null && bullet.isLive()) {
                g.setColor(new Color(255, 255, 255));
                g.drawRect(bullet.getX(), bullet.getY(), 1, 1);
            } else if (bullet != null && !(bullet.isLive())) {
                bullets.remove(bullet);
            }
        }
    }

    @Override
    public void run() {
        while (true) {
            repaint(30);
            try {
                //可以在这里判断子弹又没有击中坦克
                //判断自己的坦克发射的子弹有没有击中敌方
                Vector<Bullet> myTankBullets = myTank.getBullets();
                myTank.hitTank(myTankBullets, enemyTanks, bombs);
                //判断友军的坦克发射的子弹有没有击中敌方
                for (int i = 0; i < friendlyTanks.size(); i++) {
                    FriendlyTank friendlyTank = friendlyTanks.get(i);
                    Vector<Bullet> friendlyTankBullets = friendlyTank.getBullets();
                    friendlyTank.hitTank(friendlyTankBullets, enemyTanks, bombs);
                }
                //判断敌方的坦克发射的子弹有没有击中自己
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    Vector<Bullet> enemyTankBullets = enemyTank.getBullets();
                    enemyTank.hitTank(enemyTankBullets, myTanks, bombs);
                    if (MyTank.getLifeCount() > 0 && !myTank.isLive()) {
                        myTank = new MyTank(100, 600, Tank.MOVE_UP, Tank.MY_TANK, Tank.TANK_DEFAULT_SPEED);
                        myTanks.clear();
                        myTanks.add(myTank);
                    }
                }
                //判断敌方的坦克发射的子弹有没有击中友军
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    Vector<Bullet> enemyTankBullets = enemyTank.getBullets();
                    enemyTank.hitTank(enemyTankBullets, friendlyTanks, bombs);
                }
                //判断场上敌方坦克数量，保证能有 5 个
                if(enemyTanks.size() < 5){
                    EnemyTank enemyTank = EnemyTank.newEnemyTank();
                    enemyTanks.add(enemyTank);
                    new Thread(enemyTank).start();
                }
                //判断场上友方坦克数量，保证能有 3 个
                if(friendlyTanks.size() < 3 && FriendlyTank.getFriendlyTankCount() > 2){
                    FriendlyTank friendlyTank = FriendlyTank.newFriendlyTank();
                    friendlyTanks.add(friendlyTank);
                    new Thread(friendlyTank).start();
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                //多线程之中很容易出现 ArrayIndexOutOfBoundsException 的错误
                //但是我现在还不知道如何解决
                //System.out.println("ArrayIndexOutOfBoundsException");
            }
        }
    }

    public void drawBomb(Graphics g, Bomb bomb) {
        int life = bomb.getLife();
        g.setColor(new Color(255, 124, 122));
        if (life >= 666) {
            g.fillOval(bomb.getX(), bomb.getY(), 50, 50);
            bomb.lifeDown();
        } else if (life >= 333) {
            g.fillOval(bomb.getX(), bomb.getY(), 25, 25);
            bomb.lifeDown();
        } else if (life >= 0) {
            g.fillOval(bomb.getX(), bomb.getY(), 10, 10);
            bomb.lifeDown();
        } else {
            bomb.setLive(false);
        }
    }

    public void showMyTankLifeCount(Graphics g) {
        //展示我的坦克的生命数
        g.setColor(new Color(0, 0, 0));
        Font font = new Font("宋体", Font.BOLD, 20);
        g.setFont(font);
        g.drawString("您的坦克的剩余生命数:", 1010, 150);
        //画坦克
        drawTank(g, 1010, 170, Tank.MOVE_UP, Tank.MY_TANK);
        //画数字
        g.setColor(new Color(0, 0, 0));
        font = new Font("宋体", Font.BOLD, 25);
        g.setFont(font);
        g.drawString(MyTank.getLifeCount() + "", 1070, 205);
    }

    public void showFriendlyTankCount(Graphics g) {
        //展示我的坦克的生命数
        g.setColor(new Color(0, 0, 0));
        Font font = new Font("宋体", Font.BOLD, 20);
        g.setFont(font);
        g.drawString("友军坦克剩余数量:", 1010, 270);
        //画坦克
        drawTank(g, 1010, 290, Tank.MOVE_UP, Tank.FRIENDLY_TANK);
        //画数字
        g.setColor(new Color(0, 0, 0));
        font = new Font("宋体", Font.BOLD, 25);
        g.setFont(font);
        g.drawString(FriendlyTank.getFriendlyTankCount() + "", 1070, 325);
    }

    public void showHitCount(Graphics g) {
        //展示我的坦克的击杀数
        g.setColor(new Color(0, 0, 0));
        Font font = new Font("宋体", Font.BOLD, 20);
        g.setFont(font);
        g.drawString("累计击毁的敌方坦克:", 1010, 30);
        //画坦克
        drawTank(g, 1010, 50, Tank.MOVE_UP, Tank.ENEMY_TANK);
        //画数字
        g.setColor(new Color(0, 0, 0));
        font = new Font("宋体", Font.BOLD, 25);
        g.setFont(font);
        g.drawString(MyTank.getHitCount() + "", 1070, 85);
    }
}
