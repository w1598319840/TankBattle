import java.util.Vector;

public class Tank {
    //作为所有坦克(包括友方坦克和地方坦克)的父类
    private int x, y, direction, type, speed;
    private boolean isLive = true;//用于标记坦克是否存活
    private Vector<Bullet> bullets = new Vector<>();//坦克的子弹数组
    private static Vector<Tank> allTanks = new Vector<>();//保存所有坦克
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
        Bullet bullet = new Bullet(initBulletX, initBulletY, getDirection(), Bullet.BULLET_DEFUALT_SPEED);
        bullet.start();
        bullets.add(bullet);
    }

    public void moveUp() {
        if (getY() > 0 && !isTouch()) {
            y = y - speed;
        }
    }

    public void moveDown() {
        if (getY() + 60 < 711 && !isTouch()) {
            y = y + speed;
        }
    }

    public void moveLeft() {
        if (getX() > 0 && !isTouch()) {
            x = x - speed;
        }
    }

    public void moveRight() {
        if (getX() + 60 < 984 && !isTouch()) {
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

    /**
     * @param bullets 要判断哪些子弹
     * @param tanks   这些子弹可以击中哪些坦克
     * @param bombs   在判断击中成功后，需要为 bombs 数组加入要爆炸的元素
     */
    public void hitTank(Vector<Bullet> bullets, Vector<? extends Tank> tanks, Vector<Bomb> bombs) throws ArrayIndexOutOfBoundsException {
        //判断子弹是否击中坦克
        if (bullets != null) {
            for (int i = 0; i < bullets.size(); i++) {
                Bullet bullet = bullets.get(i);
                if (bullet.isLive()) {
                    for (int j = 0; j < tanks.size(); j++) {
                        Tank tank = tanks.get(j);
                        int direction = tank.getDirection();
                        int bulletX = bullet.getX();
                        int bulletY = bullet.getY();
                        int tankX = tank.getX();
                        int tankY = tank.getY();
                        //下面判断子弹是否在坦克范围内
                        if (direction == Tank.MOVE_UP || direction == Tank.MOVE_DOWN) {
                            if (bulletX > tankX && bulletX < tankX + 40 && bulletY > tankY && bulletY < tankY + 60) {
                                bullet.setLive(false);
                                tank.setLive(false);
                                if (tank instanceof MyTank) {
                                    MyTank.LifeCountDown();
                                } else if (tank instanceof EnemyTank) {
                                    MyTank.hitCountUp();
                                } else if (tank instanceof FriendlyTank) {
                                    FriendlyTank.FrendlyTankCountDown();
                                }
                                bombs.add(new Bomb(tankX + 10, tankY + 10));
                            }
                        } else if (direction == Tank.MOVE_LEFT || direction == Tank.MOVE_RIGHT) {
                            if (bulletX > tankX && bulletX < tankX + 60 && bulletY > tankY && bulletY < tankY + 40) {
                                bullet.setLive(false);
                                tank.setLive(false);
                                if (tank instanceof MyTank) {
                                    MyTank.LifeCountDown();
                                } else if (tank instanceof EnemyTank) {
                                    MyTank.hitCountUp();
                                } else if (tank instanceof FriendlyTank) {
                                    FriendlyTank.FrendlyTankCountDown();
                                }
                                bombs.add(new Bomb(tankX + 10, tankY + 10));
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * @return 返回一个布尔值，true 表示有碰撞, false 表示无碰撞
     */
    public boolean isTouch() {
        try {
            //判断坦克之间是否会相互碰撞，防止重叠(自己的、友军的和敌军的都不能相互碰撞)
            int tankDirection = this.getDirection();
            int tankX = this.getX();
            int tankY = this.getY();
            if (tankDirection == Tank.MOVE_UP) {
                //让当前要判断的坦克和所有其他坦克进行比较
                //tank 的左上角坐标 (x,y),右上角坐标 (x+40,y)
                for (int i = 0; i < allTanks.size(); i++) {
                    Tank otherTank = allTanks.get(i);
                    int otherTankX = otherTank.getX();
                    int otherTankY = otherTank.getY();
                    //要把自己剔除，不然永远都是碰撞的
                    if (otherTank == this) {
                        continue;
                    }
                    int otherTankDirection = otherTank.getDirection();
                    if (otherTankDirection == Tank.MOVE_UP || otherTankDirection == Tank.MOVE_DOWN) {//如果其他坦克是上下方向的
                        //tank 的左上角或右上角进入 otherTank 的区域内，则重叠
                        //otherTank 的 x 范围 [x,x+40], y 的范围 [y,y+60]
                        if (tankX >= otherTankX
                                && tankX <= otherTankX + 40
                                && tankY >= otherTankY
                                && tankY <= otherTankY + 60) {//先判断左上角
                            return true;
                        }
                        if (tankX + 40 >= otherTankX
                                && tankX + 40 <= otherTankX + 40
                                && tankY >= otherTankY
                                && tankY <= otherTankY + 60) {//再判断右上角
                            return true;
                        }
                    } else if (otherTankDirection == Tank.MOVE_LEFT || otherTankDirection == Tank.MOVE_RIGHT) {//如果其他坦克是左右方向的
                        ////otherTank 的 x 范围 [x,x+60], y 的范围 [y,y+40]
                        if (tankX >= otherTankX
                                && tankX <= otherTankX + 60
                                && tankY >= otherTankY
                                && tankY <= otherTankY + 40) {//先判断左上角
                            return true;
                        }
                        if (tankX + 40 >= otherTankX
                                && tankX + 40 <= otherTankX + 60
                                && tankY >= otherTankY
                                && tankY <= otherTankY + 40) {//再判断右上角
                            return true;
                        }
                    }
                }
            } else if (tankDirection == Tank.MOVE_LEFT) {
                //让当前要判断的坦克和所有其他坦克进行比较
                //tank 的左上角坐标 (x,y),左下角坐标 (x,y+40)
                for (int i = 0; i < allTanks.size(); i++) {
                    Tank otherTank = allTanks.get(i);
                    int otherTankX = otherTank.getX();
                    int otherTankY = otherTank.getY();
                    //要把自己剔除，不然永远都是碰撞的
                    if (otherTank == this) {
                        continue;
                    }
                    int otherTankDirection = otherTank.getDirection();
                    if (otherTankDirection == Tank.MOVE_UP || otherTankDirection == Tank.MOVE_DOWN) {//如果其他坦克是上下方向的
                        //tank 的左上角或左下角进入 otherTank 的区域内，则重叠
                        //otherTank 的 x 范围 [x,x+40], y 的范围 [y,y+60]
                        if (tankX >= otherTankX
                                && tankX <= otherTankX + 40
                                && tankY >= otherTankY
                                && tankY <= otherTankY + 60) {//先判断左上角
                            return true;
                        }
                        if (tankX >= otherTankX
                                && tankX <= otherTankX + 40
                                && tankY + 40 >= otherTankY
                                && tankY + 40 <= otherTankY + 60) {//再判断左下角
                            return true;
                        }
                    } else if (otherTankDirection == Tank.MOVE_LEFT || otherTankDirection == Tank.MOVE_RIGHT) {//如果其他坦克是左右方向的
                        ////otherTank 的 x 范围 [x,x+60], y 的范围 [y,y+40]
                        if (tankX >= otherTankX
                                && tankX <= otherTankX + 60
                                && tankY >= otherTankY
                                && tankY <= otherTankY + 40) {//先判断左上角
                            return true;
                        }
                        if (tankX >= otherTankX
                                && tankX <= otherTankX + 60
                                && tankY + 40 >= otherTankY
                                && tankY + 40 <= otherTankY + 40) {//再判断右上角
                            return true;
                        }
                    }
                }
            } else if (tankDirection == Tank.MOVE_DOWN) {
                //让当前要判断的坦克和所有其他坦克进行比较
                //tank 的左下角坐标 (x,y+60),右下角坐标 (x+40,y+60)
                for (int i = 0; i < allTanks.size(); i++) {
                    Tank otherTank = allTanks.get(i);
                    int otherTankX = otherTank.getX();
                    int otherTankY = otherTank.getY();
                    //要把自己剔除，不然永远都是碰撞的
                    if (otherTank == this) {
                        continue;
                    }
                    int otherTankDirection = otherTank.getDirection();
                    if (otherTankDirection == Tank.MOVE_UP || otherTankDirection == Tank.MOVE_DOWN) {//如果其他坦克是上下方向的
                        //tank 的左上角或左下角进入 otherTank 的区域内，则重叠
                        //otherTank 的 x 范围 [x,x+40], y 的范围 [y,y+60]
                        if (tankX >= otherTankX
                                && tankX <= otherTankX + 40
                                && tankY + 60 >= otherTankY
                                && tankY + 60 <= otherTankY + 60) {//先判断左下角
                            return true;
                        }
                        if (tankX + 40 >= otherTankX
                                && tankX + 40 <= otherTankX + 40
                                && tankY + 60 >= otherTankY
                                && tankY + 60 <= otherTankY + 60) {//再判断右下角
                            return true;
                        }
                    } else if (otherTankDirection == Tank.MOVE_LEFT || otherTankDirection == Tank.MOVE_RIGHT) {//如果其他坦克是左右方向的
                        ////otherTank 的 x 范围 [x,x+60], y 的范围 [y,y+40]
                        if (tankX >= otherTankX
                                && tankX <= otherTankX + 60
                                && tankY + 60 >= otherTankY
                                && tankY + 60 <= otherTankY + 40) {//先判断左上角
                            return true;
                        }
                        if (tankX + 40 >= otherTankX
                                && tankX + 40 <= otherTankX + 60
                                && tankY + 60 >= otherTankY
                                && tankY + 60 <= otherTankY + 40) {//再判断右上角
                            return true;
                        }
                    }
                }
            } else if (tankDirection == Tank.MOVE_RIGHT) {
                //让当前要判断的坦克和所有其他坦克进行比较
                //tank 的右上角坐标 (x+60,y),右下角坐标 (x+60,y+40)
                for (int i = 0; i < allTanks.size(); i++) {
                    Tank otherTank = allTanks.get(i);
                    int otherTankX = otherTank.getX();
                    int otherTankY = otherTank.getY();
                    //要把自己剔除，不然永远都是碰撞的
                    if (otherTank == this) {
                        continue;
                    }
                    int otherTankDirection = otherTank.getDirection();
                    if (otherTankDirection == Tank.MOVE_UP || otherTankDirection == Tank.MOVE_DOWN) {//如果其他坦克是上下方向的
                        //tank 的左上角或左下角进入 otherTank 的区域内，则重叠
                        //otherTank 的 x 范围 [x,x+40], y 的范围 [y,y+60]
                        if (tankX + 60 >= otherTankX
                                && tankX + 60 <= otherTankX + 40
                                && tankY >= otherTankY
                                && tankY <= otherTankY + 60) {//先判断右上角
                            return true;
                        }
                        if (tankX + 60 >= otherTankX
                                && tankX + 60 <= otherTankX + 40
                                && tankY + 40 >= otherTankY
                                && tankY + 40 <= otherTankY + 60) {//再判断右下角
                            return true;
                        }
                    } else if (otherTankDirection == Tank.MOVE_LEFT || otherTankDirection == Tank.MOVE_RIGHT) {//如果其他坦克是左右方向的
                        ////otherTank 的 x 范围 [x,x+60], y 的范围 [y,y+40]
                        if (tankX + 60 >= otherTankX
                                && tankX + 60 <= otherTankX + 60
                                && tankY >= otherTankY
                                && tankY <= otherTankY + 40) {//先判断右上角
                            return true;
                        }
                        if (tankX + 60 >= otherTankX
                                && tankX + 60 <= otherTankX + 60
                                && tankY + 40 >= otherTankY
                                && tankY + 40 <= otherTankY + 40) {//再判断右下角
                            return true;
                        }
                    }
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            //暂时无法解决在别的线程修改坦克数量的时候的问题
        }
        return false;
    }

    public static void setAllTanks(Vector<Tank> allTanks) {
        Tank.allTanks = allTanks;
    }
}
