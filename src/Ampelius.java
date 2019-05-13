import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Ampelius extends Collidable{
    int imageWidth=0;
    int imageHeight=0;
    BufferedImage image=null;
    int startX=0;
    int startY=0;
    int speed=2;
    int boostLeft=100;
    int lives=5;
    boolean up=false;
    boolean down=false;
    boolean right=false;
    boolean left=false;
    int yeet=0;

    /*public WalkableTile(File convertToImage, int imageWidth, int imageHeight) {
            super(convertToImage, imageWidth, imageHeight);
            image=super.getElementImage();
        }*/
    public Ampelius(int x, int y,File file) {
        super(x,y,file);
        image=convertFileToImage();
        this.imageHeight=image.getHeight();
        this.imageWidth=image.getWidth();
        setRect(new Rectangle(x,y,imageWidth,imageHeight));
        super.setImage(image);
        this.startX=x;
        this.startY=y;
    }

    public int getLives() {
        return lives;
    }

    public void decreaseLife() {
        lives--;
    }

    public void restart() {
        setRect(new Rectangle(startX,startY,(int)getRect().getWidth(),(int)getRect().getHeight()));
    }


    public void setUp(boolean up){
        this.up=up;
    }
    public void setDown(boolean up){
        this.down=up;
    }
    public void setRight(boolean up){
        this.right=up;
    }
    public void setLeft(boolean up){
        this.left=up;
    }

    public boolean isUp() {
        return up;
    }

    public boolean isDown() {
        return down;
    }

    public boolean isRight() {
        return right;
    }

    public boolean isLeft() {
        return left;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void speedGain() {
        while(boostLeft<100) {
            if(yeet%15==0) {
                boostLeft++;
            }
            yeet++;
        }
    }

    public void update() {
        /*if(speed>2) {
            boostLeft--;
        }
        if(boostLeft<=0) {
            boostLeft=0;
            setSpeed(2);
        }*/
        if (up) {
            setRect(new Rectangle((int)getRect().getX(),(int)(getRect().getY()-speed),(int)getRect().getWidth(),(int)getRect().getHeight()));
        } else if (down) {
            setRect(new Rectangle((int)getRect().getX(),(int)(getRect().getY()+speed),(int)getRect().getWidth(),(int)getRect().getHeight()));
        } else if (left) {
            setRect(new Rectangle((int)(getRect().getX()-speed),(int)(getRect().getY()),(int)getRect().getWidth(),(int)getRect().getHeight()));
        } else if (right) {
            setRect(new Rectangle((int)(getRect().getX()+speed),(int)(getRect().getY()),(int)getRect().getWidth(),(int)getRect().getHeight()));
        }
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public BufferedImage getImage() {
        return image;
    }


    public void stop() {
        up=false;
        down=false;
        left=false;
        right=false;
    }
}
