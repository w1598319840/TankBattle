import javax.swing.*;

public class GameInterfaceFrame extends JFrame {
    //游戏界面的Frame
    GameInterfacePanel contain;

    public GameInterfaceFrame(String title) {
        super(title);
        setBounds(300, 80, 1000, 750);
        contain = new GameInterfacePanel();
        add(contain);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
