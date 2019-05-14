import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class PatternRect extends Collidable {
    int imageWidth=0;
    int imageHeight=0;
    BufferedImage image=null;
    int speed=4;
    int shouldI=0;

    @Override
    public void setLeft(boolean left) {
        this.left = left;
        right=false;
    }

    @Override
    public void setRight(boolean right) {
        this.right = right;
        left=false;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public static int widthOfImage=50;
    int x=0;
    int shootDecide=0;
    boolean right=false;
    boolean left=false;
    boolean up=true;
    boolean down=false;
    int y=0;
    public PatternRect(int x, int y, File file) {
        super(x,y,file);
        this.x=x;
        this.y=y;
        image=super.tools.scale(convertFileToImage(),5.0,5.0);
        this.imageHeight=image.getHeight();
        this.imageWidth=image.getWidth();
        widthOfImage=imageWidth;
        setRect(new Rectangle(x,y,imageWidth,imageHeight));
        super.setImage(image);
    }

    public boolean isRight() {
        return right;
    }

    public boolean isLeft() {
        return left;
    }
    public void changeDirection() {
        if(up){
            up=false;
            right=true;
            down=false;
            left=false;
            return;
        }
        else if(left){
            up=true;
            right=false;
            down=false;
            left=false;
            return;
        }
        else if(right){
            up=false;
            right=false;
            down=true;
            left=false;
            return;
        }
        else if(down){
            up=false;
            right=false;
            down=false;
            left=true;
            return;
        }
    }

    @Override
    public void setUp(boolean up) {
        this.up = up;
        down=false;
    }

    @Override
    public void setDown(boolean down) {
        this.down = down;
        up=false;
    }

    public void update() {
        if(shouldI%400==0) {
            changeDirection();
        }
        if(down) {
            setRect(new Rectangle((int)getRect().getX(),(int)getRect().getY()+speed,(int)getRect().getWidth(),(int)getRect().getHeight()));
        }
        if(up) {
            setRect(new Rectangle((int)getRect().getX(),(int)getRect().getY()-speed,(int)getRect().getWidth(),(int)getRect().getHeight()));
        }
        if(left) {
            setRect(new Rectangle((int)getRect().getX()-speed,(int)getRect().getY(),(int)getRect().getWidth(),(int)getRect().getHeight()));
        }
        if(right) {
            setRect(new Rectangle((int)getRect().getX()+speed,(int)getRect().getY(),(int)getRect().getWidth(),(int)getRect().getHeight()));
        }

        shouldI++;
    }
}
