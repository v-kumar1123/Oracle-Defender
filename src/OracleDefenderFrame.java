import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class OracleDefenderFrame extends JFrame {
    ImageTools2 tools=new ImageTools2();
    OracleDefenderGame game=new OracleDefenderGame();
    File wallLocation=new File("Wall.png");
    File walkableTileLocation=new File("WalkableTile.png");
    public OracleDefenderFrame() {
        super();
        setSize(400,400);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        setVisible(true);
        setResizable(false);
        game.levelOneLoader();
    }
    public void paint(Graphics g) {
        /*g.setColor(new Color(100,125,170));
        g.fillRect(0,0,getWidth(),getHeight());*/
        drawLevelOne(g);
    }

    public void drawLevelOne(Graphics g) {
        for(int x=0;x<game.getLevelOne().size();x++) {
            GameElement element=game.getLevelOne().get(x);
            g.drawImage(element.getElementImage(), element.getX(), element.getY(), null);
        }
    }
}
