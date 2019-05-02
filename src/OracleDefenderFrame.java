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
            if(r instanceof Ampelius&&((Ampelius) r).isRight()) {
                if(r.getX()+r.getImageWidth()>=getWidth()) {
                    r.setX((int) (getWidth() - r.getRect().getWidth()));
                }
                bg.setColor(new Color(100,125,170));
                bg.fillRect(0,0,getWidth(),getHeight());
                for(int e=0;e<game.getGeneric().size();e++) {
                    int finderX = startColumnX * 25;
                    int toMove = /*worldX % 25*/3;
                    int toMoveY = worldY % 25;
                    GameElement y = (GameElement) game.getGeneric().get(e);
                    if(!(y instanceof Ampelius)) {
                        y.setX(y.getX() - toMove);
                    }
                    if (y.getX() == finderX) {
                        bg.drawImage(y.getElementImage().getSubimage(toMove, 0, y.getElementImage().getWidth() - toMove, y.getElementImage().getHeight()), y.getX(), y.getY(), null);
                    } else if (y.getX() + 25 > getWidth() && getWidth() - y.getX() > 0) {
                        bg.drawImage(y.getElementImage().getSubimage(0, 0, getWidth() - y.getX(), y.getElementImage().getHeight()), y.getX(), y.getY(), null);
                    } else {
                        bg.drawImage(y.getElementImage(), y.getX(), y.getY(), null);
                    }
                    //}
                }
            }
            if(r instanceof Ampelius&&((Ampelius) r).isLeft()) {
                if(r.getX()<0) {
                    r.setX(0);
                }
                bg.setColor(new Color(100,125,170));
                bg.fillRect(0,0,getWidth(),getHeight());
                for(int e=0;e<game.getGeneric().size();e++) {
                    int finderX = startColumnX * 25;
                    int toMove = /*worldX % 25*/3;
                    int toMoveY = worldY % 25;
                    GameElement y = (GameElement) game.getGeneric().get(e);
                    if(!(y instanceof Ampelius)) {
                        y.setX(y.getX() + toMove);
                    }
                    if (y.getX() == finderX) {
                        bg.drawImage(y.getElementImage().getSubimage(0, 0, y.getElementImage().getWidth() - toMove, y.getElementImage().getHeight()), y.getX(), y.getY(), null);
                    } else if (y.getX()<0&&y.getX()+y.getWidth()>0) {
                        bg.drawImage(y.getElementImage().getSubimage(0-y.getX(), 0, y.getWidth() - (0-y.getX()), y.getElementImage().getHeight()), y.getX(), y.getY(), null);
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
            if(worldX>0) {
                worldX--;
            }
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
        Ampelius ampelius=null;
        for (int y = 0; y < game.getGeneric().size(); y++) {
            if(game.getGeneric().get(y) instanceof Ampelius) {
                ampelius = (Ampelius) game.getGeneric().get(y);
            }
            game.getGeneric().get(y).update();
        }
        System.out.println(ampelius.getRect());
        for(int g=0;g<game.getGeneric().size();g++) {
            if(game.getGeneric().get(g) instanceof Wall&&game.getGeneric().get(g).getRect().getX()>ampelius.getX()) {
                System.out.println("\t\t\t\t\t\t\t\t\t"+game.getGeneric().get(g));
            }
            if(ampelius.collidesWith(game.getGeneric().get(g))&&game.getGeneric().get(g) instanceof Wall) {
                System.out.println("HI. I HAVE COLLIDED WITH A WALL");
                Collidable c = game.getGeneric().get(g);
                if (ampelius.isUp()) {
                    ampelius.setY((int) c.getRect().getY() + (int) c.getRect().getHeight());
                } else if (ampelius.isDown()) {
                    ampelius.setY((int) c.getRect().getY() - (int) ampelius.getRect().getHeight());
                } else if (ampelius.isLeft()) {
                    ampelius.setX((int) c.getRect().getX() + (int) c.getRect().getWidth());
                } else if (ampelius.isRight()) {
                    ampelius.setX((int) c.getRect().getX() - (int) ampelius.getRect().getWidth());
                } else {

                }
            }
        }

        //Todo 5.1.19: FIND OUT WHAT'S WRONG WITH THIS CODE
        /*for (int y = 0; y < game.getGeneric().size(); y++) {
            if(game.getGeneric().get(y) instanceof Wall) {
                System.out.println("I FOUND A WALL");
            }
            if(ampelius.getRect().intersects(game.getGeneric().get(y).getRect())&&!(game.getGeneric().get(y) instanceof Ampelius&&game.getGeneric().get(y) instanceof Wall)) {
                Collidable c = game.getGeneric().get(y);

                System.out.println("\t\t\t\t\t\tI HAVE COLLIDED");
                System.out.println("\t\t\t\t\t\t STOP");
                if (ampelius.up) {
                    ampelius.setY((int) c.getRect().getY() + (int) c.getRect().getHeight());
                } else if (ampelius.down) {
                    ampelius.setY((int) c.getRect().getY() - (int) ampelius.getRect().getHeight());
                } else if (ampelius.left) {
                    ampelius.setX((int) c.getRect().getX() + (int) c.getRect().getWidth());
                } else if (ampelius.right) {
                    ampelius.setX((int) c.getRect().getX() - (int) ampelius.getRect().getWidth());
                } else {

                }
                ampelius.stop();

            }

            game.getGeneric().get(y).update();

        }*/

        
        /*
         */
        
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
