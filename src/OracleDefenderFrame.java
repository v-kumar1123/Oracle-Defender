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

        game.setLevelOne();
        Thread t=new Thread(this);
        t.start();
    }
    public void paint(Graphics g) {
        Graphics bg=buffer.getGraphics();
        bg.setColor(new Color(100,125,170));
        bg.fillRect(0,0,getWidth(),getHeight());
        int tempX=0;

        int startColumnX = worldX / 25;
        if(game.getPlayer().isRight()) {
            Collidable r=game.getPlayer();
            if(r.getRect().getX()+r.getImageWidth()>=getWidth()) {
                r.setRect(new Rectangle((int) (getWidth() - r.getRect().getWidth()), (int)r.getRect().getY(),(int)r.getRect().getWidth(),(int)r.getRect().getHeight()));
            }
            bg.setColor(new Color(100,125,170));
            bg.fillRect(0,0,getWidth(),getHeight());
            for(int e=0;e<game.getGeneric().size();e++) {
                int finderX = startColumnX * 25;
                int toMove = /*worldX % 25*/3;
                int toMoveY = worldY % 25;
                Collidable y = (Collidable) game.getGeneric().get(e);
                if(!(y instanceof Ampelius)) {
                    y.setRect(new Rectangle((int)(y.getRect().getX() - toMove),(int)r.getRect().getY(),(int)r.getRect().getWidth(),(int)r.getRect().getHeight()));
                }
                if (y.getRect().getX() == finderX) {
                    bg.drawImage(y.getElementImage().getSubimage(toMove, 0, y.getElementImage().getWidth() - toMove, y.getElementImage().getHeight()), (int)y.getRect().getX(), (int)y.getRect().getY(), null);
                } else if (y.getRect().getX() + 25 > getWidth() && getWidth() - y.getRect().getX() > 0) {
                    bg.drawImage(y.getElementImage().getSubimage(0, 0, getWidth() - (int)y.getRect().getX(), y.getElementImage().getHeight()), (int)y.getRect().getX(), (int)y.getRect().getY(), null);
                } else {
                    bg.drawImage(y.getElementImage(), (int)y.getRect().getX(), (int)y.getRect().getY(), null);
                }
                //}
            }
        }
        if(game.getPlayer().isLeft()) {
            Collidable r=game.getPlayer();
            if(r.getRect().getX()<0) {
                r.setRect(new Rectangle(0,(int)r.getRect().getY(),(int)r.getRect().getWidth(),(int)r.getRect().getHeight()));
            }
            bg.setColor(new Color(100,125,170));
            bg.fillRect(0,0,getWidth(),getHeight());
            for(int e=0;e<game.getGeneric().size();e++) {
                int finderX = startColumnX * 25;
                int toMove = /*worldX % 25*/3;
                int toMoveY = worldY % 25;
                Collidable y = (Collidable) game.getGeneric().get(e);
                if(!(y instanceof Ampelius)) {
                    y.setRect(new Rectangle((int)y.getRect().getX() + toMove,(int)r.getRect().getY(),(int)r.getRect().getWidth(),(int)r.getRect().getHeight()));
                }
                if (y.getRect().getX() == finderX) {
                    bg.drawImage(y.getElementImage().getSubimage(0, 0, y.getElementImage().getWidth() - toMove, y.getElementImage().getHeight()), (int)y.getRect().getX(), (int)y.getRect().getY(), null);
                } else if (y.getRect().getX()<0&&y.getRect().getX()+y.getRect().getWidth()>0) {
                    bg.drawImage(y.getElementImage().getSubimage((int)(0-y.getRect().getX()), 0, (int)(y.getRect().getWidth() - (0-y.getRect().getX())), y.getElementImage().getHeight()), (int)y.getRect().getX(), (int)y.getRect().getY(), null);
                } else {
                    bg.drawImage(y.getElementImage(), (int)y.getRect().getX(), (int)y.getRect().getY(), null);
                }
                //}
            }
        }


        //Draws World
        //Draws Ampelius
      
        bg.drawImage(game.getPlayer().getElementImage(), (int)game.getPlayer().getRect().getX(), (int)game.getPlayer().getRect().getY(), null);
         

        g.drawImage(buffer,0,0,null);
    }

    public void drawLevelOne(Graphics g) {
        for(int x=0;x<game.getGeneric().size();x++) {
            GameElement element=(GameElement)game.getGeneric().get(x);
            g.drawImage(element.getElementImage(), (int)element.getRect().getX(), element.getY(), null);
        }
        gameBeginning=false;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyChar() == 'a') {
            if(worldX>0) {
                worldX--;
            }
            game.getPlayer().setLeft(true);
        }
        else if (e.getKeyChar() == 'd') {
            worldX++;
            game.getPlayer().setRight(true);
        }
        else if (e.getKeyChar() == 's') {
            game.getPlayer().setDown(true);
        }
        else if (e.getKeyChar() == 'w') {
            game.getPlayer().setUp(true);
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

        if (e.getKeyChar() == 'a') {
            game.getPlayer().setLeft(false);
        }
        else if (e.getKeyChar() == 'd') {
            game.getPlayer().setRight(false);
        }
        else if (e.getKeyChar() == 's') {
            game.getPlayer().setDown(false);
        }
        else if (e.getKeyChar() == 'w') {
            game.getPlayer().setUp(false);
        }
        else {
            //System.out.println(game.getGeneric().get(x).getRect().getY());
            //game.getGeneric().get(x).getRect().setRect((int)game.getGeneric().get(x).getRect().getRect().getX(),(int)((game.getGeneric().get(x).getRect().getY())-2),(int)game.getGeneric().get(x).getRect().getWidth(),(int)game.getGeneric().get(x).getRect().getHeight());
            //System.out.println(game.getGeneric().get(x).getRect().getY());
        }
    }

    public void update() {

        for (int y = 0; y < game.getGeneric().size(); y++) {
            game.getGeneric().get(y).update();
        }
        //System.out.println("HI. I am Ampelius"+ampelius);
        for(int g=0;g<game.getGeneric().size();g++) {
            if(game.getPlayer().collidesWith(game.getGeneric().get(g))) {
                System.out.println("HI. I HAVE COLLIDED WITH A WALL");
                if(game.getGeneric().get(g) instanceof Wall) {
                    Collidable c = game.getGeneric().get(g);
                    if (game.getPlayer().isUp()) {
                        game.getPlayer().setRect(new Rectangle((int)game.getPlayer().getRect().getX(),(int) c.getRect().getY() + (int) c.getRect().getHeight(),(int)game.getPlayer().getRect().getWidth(),(int)game.getPlayer().getRect().getHeight()));
                    } else if (game.getPlayer().isDown()) {
                        game.getPlayer().setRect(new Rectangle((int)game.getPlayer().getRect().getX(),(int) c.getRect().getY() - (int) game.getPlayer().getRect().getHeight(),(int)game.getPlayer().getRect().getWidth(),(int)game.getPlayer().getRect().getHeight()));
                    } else if (game.getPlayer().isLeft()) {
                        game.getPlayer().setRect(new Rectangle((int) c.getRect().getX() + (int) c.getRect().getWidth(),(int) c.getRect().getY(),(int)game.getPlayer().getRect().getWidth(),(int)game.getPlayer().getRect().getHeight()));
                    } else if (game.getPlayer().isRight()) {
                        game.getPlayer().setRect(new Rectangle((int) c.getRect().getX() - (int) game.getPlayer().getRect().getWidth(),(int) c.getRect().getY(),(int)game.getPlayer().getRect().getWidth(),(int)game.getPlayer().getRect().getHeight()));
                    }
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
                    ampelius.setX((int) c.getRect().getRect().getX() + (int) c.getRect().getWidth());
                } else if (ampelius.right) {
                    ampelius.setX((int) c.getRect().getRect().getX() - (int) ampelius.getRect().getWidth());
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
                    p.setX((int) c.getRect().getRect().getX() + (int) c.getRect().getWidth());
                } else if (p.right) {
                    p.setX((int) c.getRect().getRect().getX() - (int) p.getRect().getWidth());
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
                            p.setX((int) c.getRect().getRect().getX() + (int) c.getRect().getWidth());
                        } else if (p.right) {
                            p.setX((int) c.getRect().getRect().getX() - (int) p.getRect().getWidth());
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
