import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class OracleDefenderFrame extends JFrame implements KeyListener {
    ImageTools2 tools=new ImageTools2();
    OracleDefenderGame game=new OracleDefenderGame();
    int worldX=0;
    int worldWidth=game.levelWidth;
    int worldHeight=game.levelWidth;
    boolean rightPressed=false;
    boolean upPressed=false;
    boolean downPressed=false;
    boolean leftPressed=false;
    int worldY=0;
    BufferedImage buffer=null;
    boolean gameBeginning=true;
    File wallLocation=new File("Wall.png");
    File walkableTileLocation=new File("WalkableTile.png");
    public OracleDefenderFrame() {
        super();
        addKeyListener(this);
        setSize(400,400);

        buffer=new BufferedImage(getWidth(),getHeight(),BufferedImage.TYPE_4BYTE_ABGR);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        setVisible(true);
        setResizable(false);
    }
    public void paint(Graphics g) {

        Graphics bg=buffer.getGraphics();
        bg.setColor(new Color(100,125,170));
        bg.fillRect(0,0,getWidth(),getHeight());
        int tempX=0;
        for (int v = 0; v < game.getLevelOne().size(); v++) {
            int startColumnX = worldX / 25;
            int finderX = startColumnX * 25;
            int toMove=worldX%25;
            int toMoveY=worldY%25;

            GameElement r = game.getLevelOne().get(v);

            if(rightPressed) {
                r.setX(r.getX() - toMove);
            }
            else if(leftPressed) {
                r.setX(r.getX() + toMove);
            }
            if(r.getX()<getWidth()) {
                if (r.getX() == finderX) {
                    bg.drawImage(r.getElementImage().getSubimage(toMove, 0, r.getElementImage().getWidth()-toMove, r.getElementImage().getHeight()), r.getX(), r.getY(), null);
                } else if (r.getX() + 25 > getWidth()) {
                    bg.drawImage(r.getElementImage().getSubimage(0, 0, getWidth() - r.getX(), r.getElementImage().getHeight()), r.getX(), r.getY(), null);
                } else {
                    bg.drawImage(r.getElementImage(), r.getX(), r.getY(), null);
                }
            }
            tempX=startColumnX;

        }

        g.drawImage(buffer,0,0,null);
    }

    public void drawLevelOne(Graphics g) {
        for(int x=0;x<game.getLevelOne().size();x++) {
            GameElement element=game.getLevelOne().get(x);
            g.drawImage(element.getElementImage(), element.getX(), element.getY(), null);
        }
        gameBeginning=false;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyChar()=='D'||e.getKeyChar()=='d') {
            if(worldX<game.levelWidth) {
                worldX++;
            }
            rightPressed=true;
            leftPressed=false;
            upPressed=false;
            downPressed=false;
            repaint();
        }
        else if(e.getKeyChar()=='A'||e.getKeyChar()=='a') {
            if(worldX>0) {
                worldX--;
                rightPressed=false;
                leftPressed=true;
                upPressed=false;
                downPressed=false;
                repaint();
            }
        }

        else if(e.getKeyChar()=='W'||e.getKeyChar()=='w') {
            if(worldY>0) {
                worldY++;
                rightPressed=false;
                leftPressed=false;
                upPressed=true;
                downPressed=false;
                repaint();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
