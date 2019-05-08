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
    int toMove=3;
    boolean touchingObstacle=false;
    OracleDefenderGame game=new OracleDefenderGame();
    int worldX=0;
    int worldWidth=game.levelWidth;
    int worldHeight=game.levelWidth;
    boolean rightHit=false;
    boolean upHit=false;
    boolean downHit=false;
    boolean leftHit=false;
    boolean restart=false;
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
        boolean restart=false;
        game.setLevelOne();
        Thread t=new Thread(this);
        t.start();
    }
    public void paint(Graphics g) {
        Graphics bg=buffer.getGraphics();
        int startColumnX=worldX/25;
        int startColumnY=worldY/25;
        int finderX=startColumnX*25;
        int finderY=startColumnY*25;
        //bg.setColor(new Color(100,125,170));
        bg.setColor(Color.BLACK);
        bg.fillRect(0,0,getWidth(),getHeight());
        int tempX=0;
        if(restart) {
            game.reset();
            restart=false;
        }
        //Draws world
        for(int x=0;x<game.getGeneric().size();x++) {
            Collidable r=game.getGeneric().get(x);
            if(game.getPlayer().isLeft()||game.getPlayer().isRight()) {
                if (r.getRect().getX() == finderX) {
                    bg.drawImage(r.getElementImage().getSubimage(toMove, 0, r.getElementImage().getWidth() - toMove, r.getElementImage().getHeight()), (int) r.getRect().getX(), (int) r.getRect().getY(), null);
                }
                else if (r.getRect().getX() + r.getRect().getWidth() > getWidth() && getWidth() - r.getRect().getX() > 0) {
                    bg.drawImage(r.getElementImage().getSubimage(0, 0, getWidth() - (int) r.getRect().getX(), r.getElementImage().getHeight()), (int) r.getRect().getX(), (int) r.getRect().getY(), null);
                } else {
                    bg.drawImage(game.getGeneric().get(x).getElementImage(), (int) game.getGeneric().get(x).getRect().getX(), (int) game.getGeneric().get(x).getRect().getY(), null);
                }
            }
            else if(game.getPlayer().isUp()||game.getPlayer().isDown()) {
                if (r.getRect().getY() == finderY) {
                    bg.drawImage(r.getElementImage().getSubimage(0, toMove, r.getElementImage().getWidth(), r.getElementImage().getHeight()-toMove), (int) r.getRect().getX(), (int) r.getRect().getY(), null);
                }
                else if (r.getRect().getY() + r.getRect().getHeight() > getHeight() && getHeight() - r.getRect().getY() > 0) {
                    bg.drawImage(r.getElementImage().getSubimage(0, 0, (int)r.getRect().getWidth(), (int)(getHeight()- r.getRect().getY())), (int) r.getRect().getX(), (int) r.getRect().getY(), null);
                } else {
                    bg.drawImage(game.getGeneric().get(x).getElementImage(), (int) game.getGeneric().get(x).getRect().getX(), (int) game.getGeneric().get(x).getRect().getY(), null);
                }
            }
            else {
                bg.drawImage(game.getGeneric().get(x).getElementImage(), (int) game.getGeneric().get(x).getRect().getX(), (int) game.getGeneric().get(x).getRect().getY(), null);
            }

        }
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
            game.getPlayer().setDown(false);
            game.getPlayer().setUp(false);
            game.getPlayer().setRight(false);
            game.getPlayer().setLeft(true);
            rightHit=false;
            upHit=false;
            downHit=false;
        }
        else if (e.getKeyChar() == 'd') {
            worldX++;
            game.getPlayer().setDown(false);
            game.getPlayer().setUp(false);
            game.getPlayer().setRight(true);
            game.getPlayer().setLeft(false);
            upHit=false;
            downHit=false;
            leftHit=false;
        }
        else if (e.getKeyChar() == 's') {
            worldY++;
            game.getPlayer().setDown(true);
            game.getPlayer().setUp(false);
            game.getPlayer().setRight(false);
            game.getPlayer().setLeft(false);
            rightHit=false;
            upHit=false;
            leftHit=false;
        }
        else if (e.getKeyChar() == 'w') {
            worldY--;
            game.getPlayer().setDown(false);
            game.getPlayer().setUp(true);
            game.getPlayer().setRight(false);
            game.getPlayer().setLeft(false);
            rightHit=false;
            downHit=false;
            leftHit=false;
        }
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
        Collidable r=game.getPlayer();
        if(game.getPlayer().isRight()&&!rightHit) {
            if(r.getRect().getX()+r.getImageWidth()>=getWidth()) {
                r.setRect(new Rectangle((int) (getWidth() - r.getRect().getWidth()), (int)r.getRect().getY(),(int)r.getRect().getWidth(),(int)r.getRect().getHeight()));
            }
            for(int x=0;x<game.getGeneric().size();x++) {
                Collidable y=game.getGeneric().get(x);
                if(!(y instanceof Ampelius)&&!touchingObstacle) {
                    y.setRect(new Rectangle((int)(y.getRect().getX() - toMove),(int)y.getRect().getY(),(int)y.getRect().getWidth(),(int)y.getRect().getHeight()));
                    //System.out.println("HERE's My New X"+r.getRect().getX());
                }
            }
        }
        //If player is moving LEFT and not touching an obstacle
        if(game.getPlayer().isLeft()&&!leftHit) {
            if(r.getRect().getX()-r.getImageWidth()<0) {
                r.setRect(new Rectangle(0, (int)r.getRect().getY(),(int)r.getRect().getWidth(),(int)r.getRect().getHeight()));
            }
            for(int x=0;x<game.getGeneric().size();x++) {
                Collidable y=game.getGeneric().get(x);
                if(!(y instanceof Ampelius)&&!touchingObstacle) {
                    y.setRect(new Rectangle((int)(y.getRect().getX() + toMove),(int)y.getRect().getY(),(int)y.getRect().getWidth(),(int)y.getRect().getHeight()));
                    //System.out.println("HERE's My New X"+r.getRect().getX());
                }
            }
        }
        //If player is moving down and not touching an obstacle
        if(game.getPlayer().isDown()&&!downHit) {
            if(r.getRect().getY()+r.getRect().getHeight()>=getHeight()) {
                r.setRect(new Rectangle((int)r.getRect().getX(), (int)getHeight()-(int)r.getRect().getY(),(int)r.getRect().getWidth(),(int)r.getRect().getHeight()));
            }
            for(int x=0;x<game.getGeneric().size();x++) {
                Collidable y=game.getGeneric().get(x);
                if(!(y instanceof Ampelius)&&!touchingObstacle) {
                    y.setRect(new Rectangle((int)(y.getRect().getX()),(int)y.getRect().getY()-toMove,(int)y.getRect().getWidth(),(int)y.getRect().getHeight()));
                    //System.out.println("HERE's My New X"+r.getRect().getX());
                }
            }
        }
        //If player is moving up and not touching an obstacle
        if(game.getPlayer().isUp()&&!upHit) {
            if(r.getRect().getY()-r.getRect().getHeight()<0) {
                r.setRect(new Rectangle((int)r.getRect().getX(), 0,(int)r.getRect().getWidth(),(int)r.getRect().getHeight()));
            }
            for(int x=0;x<game.getGeneric().size();x++) {
                Collidable y=game.getGeneric().get(x);
                if(!(y instanceof Ampelius)&&!touchingObstacle) {
                    y.setRect(new Rectangle((int)(y.getRect().getX()),(int)y.getRect().getY()+toMove,(int)y.getRect().getWidth(),(int)y.getRect().getHeight()));
                    //System.out.println("HERE's My New X"+r.getRect().getX());
                }
            }
        }

        //System.out.println("HI. I am Ampelius"+ampelius);
        for(int g=0;g<game.getGeneric().size();g++) {
            if((game.getPlayer().collidesWith(game.getGeneric().get(g)))&&!(game.getGeneric().get(g) instanceof WalkableTile)) {
                if(game.getGeneric().get(g) instanceof Wall||game.getGeneric().get(g) instanceof LaserRect||game.getGeneric().get(g) instanceof VerticalLaserRect||game.getGeneric().get(g) instanceof VerticalLaserAlternate||game.getGeneric().get(g) instanceof AlternatingLaser) {
                    Collidable c = game.getGeneric().get(g);
                    if (game.getPlayer().isUp()) {
                        upHit=true;
                        game.getPlayer().setRect(new Rectangle((int)game.getPlayer().getRect().getX(),(int) c.getRect().getY() + (int) c.getRect().getHeight(),(int)game.getPlayer().getRect().getWidth(),(int)game.getPlayer().getRect().getHeight()));


                        return;
                    } else if (game.getPlayer().isDown()) {
                        downHit=true;
                        game.getPlayer().setRect(new Rectangle((int)game.getPlayer().getRect().getX(),(int) c.getRect().getY() - (int) game.getPlayer().getRect().getHeight(),(int)game.getPlayer().getRect().getWidth(),(int)game.getPlayer().getRect().getHeight()));


                        return;
                    } else if (game.getPlayer().isLeft()) {
                        leftHit=true;
                        game.getPlayer().setRect(new Rectangle((int) c.getRect().getX() + (int) c.getRect().getWidth(),(int) game.getPlayer().getRect().getY(),(int)game.getPlayer().getRect().getWidth(),(int)game.getPlayer().getRect().getHeight()));
                        return;
                    } else if (game.getPlayer().isRight()) {
                        rightHit=true;
                        game.getPlayer().setRect(new Rectangle((int) c.getRect().getX() - (int) game.getPlayer().getRect().getWidth(),(int) game.getPlayer().getRect().getY(),(int)game.getPlayer().getRect().getWidth(),(int)game.getPlayer().getRect().getHeight()));

                        return;
                    }
                }

                //TODO 5/7/19: Figure out how to restart
                if(game.getGeneric().get(g) instanceof Laser||game.getGeneric().get(g) instanceof VerticalLaser) {
                    System.out.println("HEYY");
                    restart=true;
                    break;
                }
                break;
            }
        }/*
        //CHECKS TO SEE IF AMPELIUS IS COLLIDNG WITH ANYTHING, IF NOT SETS TOUCHING OBSTACLE TO FALSE
        for(int x=0;x<game.getGeneric().size();x++) {
            if(game.getPlayer().collidesWith(game.getGeneric().get(x))&&!(game.getGeneric().get(x) instanceof WalkableTile)) {
                touchingObstacle=true;
                break;
            }
        }*/

        touchingObstacle=false;

        //If player is moving right and not touching an obstacle



        for (int y = 0; y < game.getGeneric().size(); y++) {
            game.getGeneric().get(y).update();

            if(game.getGeneric().get(y) instanceof LaserRect) {
                if(((LaserRect) game.getGeneric().get(y)).getShootDecide()%120==0) {
                    if(((LaserRect) game.getGeneric().get(y)).directionRight) {
                        game.getGeneric().add(new Laser((int)game.getGeneric().get(y).getRect().getX()+50,(int)game.getGeneric().get(y).getRect().getY()+20,new File("Laser.png")));
                        ((Laser)game.getGeneric().get(game.getGeneric().size()-1)).setRight(true);
                    }
                    else {
                        game.getGeneric().add(new Laser((int)game.getGeneric().get(y).getRect().getX()-50,(int)game.getGeneric().get(y).getRect().getY()+20,new File("Laser.png")));
                        ((Laser)game.getGeneric().get(game.getGeneric().size()-1)).setLeft(true);
                    }

                    ((Laser)game.getGeneric().get(game.getGeneric().size()-1)).setSpeed(7);
                }
            }

            if(game.getGeneric().get(y) instanceof VerticalLaserRect) {
                if(((VerticalLaserRect) game.getGeneric().get(y)).getShootDecide()%120==0) {
                    if(((VerticalLaserRect) game.getGeneric().get(y)).directionUp) {
                        game.getGeneric().add(new VerticalLaser((int)game.getGeneric().get(y).getRect().getX()+20,(int)game.getGeneric().get(y).getRect().getY()-50,new File("VerticalRect.png")));
                        ((VerticalLaser)game.getGeneric().get(game.getGeneric().size()-1)).setUp(true);
                    }
                    else {
                        game.getGeneric().add(new VerticalLaser((int)game.getGeneric().get(y).getRect().getX()+20,(int)game.getGeneric().get(y).getRect().getY()+50,new File("VerticalRect.png")));
                        ((VerticalLaser)game.getGeneric().get(game.getGeneric().size()-1)).setDown(true);
                    }

                    ((VerticalLaser)game.getGeneric().get(game.getGeneric().size()-1)).setSpeed(7);
                }
            }

            if(game.getGeneric().get(y) instanceof AlternatingLaser) {
                if(((AlternatingLaser) game.getGeneric().get(y)).getShootDecide()%70==0) {
                    if(((AlternatingLaser) game.getGeneric().get(y)).directionRight) {
                        game.getGeneric().add(new Laser((int)game.getGeneric().get(y).getRect().getX()+50,(int)game.getGeneric().get(y).getRect().getY()+20,new File("Laser.png")));
                        ((Laser)game.getGeneric().get(game.getGeneric().size()-1)).setRight(true);
                        ((AlternatingLaser) game.getGeneric().get(y)).setDirectionRight(false);
                    }
                    else {
                        game.getGeneric().add(new Laser((int)game.getGeneric().get(y).getRect().getX()-50,(int)game.getGeneric().get(y).getRect().getY()+20,new File("Laser.png")));
                        ((Laser)game.getGeneric().get(game.getGeneric().size()-1)).setLeft(true);
                        ((AlternatingLaser) game.getGeneric().get(y)).setDirectionRight(true);
                    }

                    ((Laser)game.getGeneric().get(game.getGeneric().size()-1)).setSpeed(7);
                }
            }

            if(game.getGeneric().get(y) instanceof VerticalLaserAlternate) {
                if(((VerticalLaserAlternate) game.getGeneric().get(y)).getShootDecide()%20==0) {
                    if(((VerticalLaserAlternate) game.getGeneric().get(y)).directionUp) {
                        game.getGeneric().add(new VerticalLaser((int)game.getGeneric().get(y).getRect().getX()+20,(int)game.getGeneric().get(y).getRect().getY()-50,new File("VerticalRect.png")));
                        ((VerticalLaser)game.getGeneric().get(game.getGeneric().size()-1)).setUp(true);
                        ((VerticalLaserAlternate) game.getGeneric().get(y)).setDirectionUp(false);
                    }
                    else {
                        game.getGeneric().add(new VerticalLaser((int)game.getGeneric().get(y).getRect().getX()+20,(int)game.getGeneric().get(y).getRect().getY()+50,new File("VerticalRect.png")));
                        ((VerticalLaser)game.getGeneric().get(game.getGeneric().size()-1)).setDown(true);
                        ((VerticalLaserAlternate) game.getGeneric().get(y)).setDirectionUp(true);
                    }

                    ((VerticalLaser)game.getGeneric().get(game.getGeneric().size()-1)).setSpeed(7);
                }
            }
        }
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
