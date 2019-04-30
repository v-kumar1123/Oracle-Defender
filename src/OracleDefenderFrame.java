import org.ietf.jgss.GSSManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class OracleDefenderFrame extends JFrame implements KeyListener, Runnable {
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
    public static final int UPS=60;
    private long updatesDone=0;
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

        Thread t=new Thread(this);
        t.start();
    }
    public void paint(Graphics g) {
        game.setLevelOne();
        Graphics bg=buffer.getGraphics();
        bg.setColor(new Color(100,125,170));
        bg.fillRect(0,0,getWidth(),getHeight());
        int tempX=0;

        int startColumnX = worldX / 25;
        //Draws World
        for (int v = 0; v < game.getGeneric().size(); v++) {
            GameElement r = (GameElement) game.getGeneric().get(v);
            if(r instanceof Ampelius&&r.getX()+r.getImageWidth()>=getWidth()) {

                bg.setColor(new Color(100,125,170));
                bg.fillRect(0,0,getWidth(),getHeight());
                for(int e=0;e<game.getGeneric().size();e++) {
                    int finderX = startColumnX * 25;
                    int toMove = worldX % 25;
                    int toMoveY = worldY % 25;
                    GameElement y = (GameElement) game.getGeneric().get(e);
                    //if(!(y instanceof Ampelius)) {
                    if (y.getX() == finderX) {
                        System.out.println("GAME WILL SCROLL");
                        bg.drawImage(y.getElementImage().getSubimage(toMove, 0, y.getElementImage().getWidth() - toMove, y.getElementImage().getHeight()), y.getX(), y.getY(), null);
                    } else if (y.getX() + 25 > getWidth() && getWidth() - y.getX() > 0) {
                        bg.drawImage(y.getElementImage().getSubimage(0, 0, getWidth() - y.getX(), y.getElementImage().getHeight()), y.getX(), y.getY(), null);
                    } else {
                        bg.drawImage(y.getElementImage(), y.getX(), y.getY(), null);
                    }
                    //}
                }
            }
            else {
                bg.drawImage(r.getElementImage(),r.getX(),r.getY(),null);
            }
        }
        //Draws Ampelius
        for (int v = 0; v < game.getGeneric().size(); v++) {
            GameElement r = (GameElement) game.getGeneric().get(v);
            if (r instanceof Ampelius) {
                bg.drawImage(r.getElementImage(), r.getX(), r.getY(), null);
            }
        }

        g.drawImage(buffer,0,0,null);
    }

    public void drawLevelOne(Graphics g) {
        for(int x=0;x<game.getGeneric().size();x++) {
            GameElement element=(GameElement)game.getGeneric().get(x);
            g.drawImage(element.getElementImage(), element.getX(), element.getY(), null);
        }
        gameBeginning=false;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int x=0;
        for(;x<game.getGeneric().size();x++) {
            if (game.getGeneric().get(x) instanceof Ampelius) {
                break;
            }
        }
        if (e.getKeyChar() == 'a') {
            worldX--;
            game.getGeneric().get(x).setLeft(true);
        }
        else if (e.getKeyChar() == 'd') {
            worldX++;
            game.getGeneric().get(x).setRight(true);
        }
        else if (e.getKeyChar() == 's') {
            game.getGeneric().get(x).setDown(true);
        }
        else if (e.getKeyChar() == 'w') {
            game.getGeneric().get(x).setUp(true);
        }
        /*if(e.getKeyChar()=='D'||e.getKeyChar()=='d') {
            if(worldX+getWidth()<game.levelWidth) {
                worldX++;
            }

            rightPressed=true;
            leftPressed=false;
            upPressed=false;
            downPressed=false;
            repaint();
        }
        else if(e.getKeyChar()=='A'||e.getKeyChar()=='a') {
            if(worldX>=0) {
                worldX--;
            }

            rightPressed=false;
            leftPressed=true;
            upPressed=false;
            downPressed=false;
            repaint();
        }

        else if(e.getKeyChar()=='W'||e.getKeyChar()=='w') {
            System.out.println(worldY+"");
            if(worldY<game.levelHeight) {
                worldY--;
            }

            rightPressed=false;
            leftPressed=false;
            upPressed=true;
            downPressed=false;
            repaint();
        }

        else if(e.getKeyChar()=='S'||e.getKeyChar()=='s') {
            if(worldY>=0) {
                worldY++;
            }

            rightPressed=false;
            leftPressed=false;
            upPressed=false;
            downPressed=true;

            repaint();
        }*/
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int x=0;
        for(;x<game.getGeneric().size();x++) {
            if (game.getGeneric().get(x) instanceof Ampelius) {
                break;
            }
        }
        if (e.getKeyChar() == 'a') {
            game.getGeneric().get(x).setLeft(false);
        }
        else if (e.getKeyChar() == 'd') {
            game.getGeneric().get(x).setRight(false);
        }
        else if (e.getKeyChar() == 's') {
            game.getGeneric().get(x).setDown(false);
        }
        else if (e.getKeyChar() == 'w') {
            game.getGeneric().get(x).setUp(false);
        }
        else {
            //System.out.println(game.getGeneric().get(x).getRect().getY());
            //game.getGeneric().get(x).getRect().setRect((int)game.getGeneric().get(x).getRect().getX(),(int)((game.getGeneric().get(x).getRect().getY())-2),(int)game.getGeneric().get(x).getRect().getWidth(),(int)game.getGeneric().get(x).getRect().getHeight());
            //System.out.println(game.getGeneric().get(x).getRect().getY());
        }
    }

    public void update() {
        for (int y = 0; y < game.getGeneric().size(); y++) {
            game.getGeneric().get(y).update();
        }
        repaint();

        /*
        Ampelius p=null;
        for (int x = 0; x < game.getGeneric().size(); x++) {
            if(game.getGeneric().get(x) instanceof Ampelius) {
                p=(Ampelius)game.getGeneric().get(x);
            }
        }
        for(int x=0;x<game.getGeneric().size();x++) {
            if(game.getGeneric().get(x) instanceof Wall&&p.collidesWith(game.getGeneric().get(x))) {
                Collidable c= game.getGeneric().get(x);
                if (p.up) {
                    p.setY((int) c.getRect().getY() + (int) c.getRect().getHeight());
                } else if (p.down) {
                    p.setY((int) c.getRect().getY() - (int) p.getRect().getHeight());
                } else if (p.left) {
                    p.setX((int) c.getRect().getX() + (int) c.getRect().getWidth());
                } else if (p.right) {
                    p.setX((int) c.getRect().getX() - (int) p.getRect().getWidth());
                } else {

                }
            }
        }*/
    }

    /*
                            if (p.up) {
                            p.setY((int) c.getRect().getY() + (int) c.getRect().getHeight());
                        } else if (p.down) {
                            p.setY((int) c.getRect().getY() - (int) p.getRect().getHeight());
                        } else if (p.left) {
                            p.setX((int) c.getRect().getX() + (int) c.getRect().getWidth());
                        } else if (p.right) {
                            p.setX((int) c.getRect().getX() - (int) p.getRect().getWidth());
                        } else {

                        }
     */

    @Override
    public void run() {
        long startTime=System.nanoTime();

        double sleepTime=1000.0/UPS;
        while (true) {
            boolean didUpdate = false;
            long updatesNeed = (long) (((System.nanoTime() - startTime) / 1000000) / sleepTime);
            for (; updatesDone < updatesNeed; updatesDone++) {
                update();
                didUpdate = true;
            }
            if (didUpdate) {
                repaint();
            }
            try {
                Thread.sleep((int) sleepTime);
            } catch (Exception e) {
            }


        }
    }
}
