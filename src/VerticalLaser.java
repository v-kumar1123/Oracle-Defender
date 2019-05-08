import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class VerticalLaser extends Collidable {
    int imageWidth=0;
    int imageHeight=0;
    BufferedImage image=null;
    int speed=0;

    public boolean isDown() {
        return down;
    }

    public boolean isUp() {
        return up;
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
    boolean down=false;
    boolean up=false;
    int y=0;
    public VerticalLaser(int x, int y, File file) {
        super(x,y,file);
        this.x=x;
        this.y=y;
        image=super.tools.scale(convertFileToImage(),5.0,7.0);
        //image=super.tools.rotate(image,90);
        this.imageHeight=image.getHeight();
        this.imageWidth=image.getWidth();
        widthOfImage=imageWidth;
        setRect(new Rectangle(x,y,imageWidth,imageHeight));
        super.setImage(image);
    }

    @Override
    public void setDown(boolean down) {
        this.down = down;
    }

    @Override
    public void setUp(boolean up) {
        this.up = up;
    }

    public void update() {
        if(down) {
            setRect(new Rectangle((int)getRect().getX(),(int)getRect().getY()+speed,(int)getRect().getWidth(),(int)getRect().getHeight()));
        }
        if(up) {
            setRect(new Rectangle((int)getRect().getX(),(int)getRect().getY()-speed,(int)getRect().getWidth(),(int)getRect().getHeight()));
        }
    }
}
