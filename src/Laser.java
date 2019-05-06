import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Laser extends Collidable {
    int imageWidth=0;
    int imageHeight=0;
    BufferedImage image=null;

    @Override
    public void setLeft(boolean left) {
        this.left = left;
    }

    @Override
    public void setRight(boolean right) {
        this.right = right;
    }

    public static int widthOfImage=50;
    int x=0;
    int shootDecide=0;
    boolean right=false;
    boolean left=false;
    int y=0;
    public Laser(int x, int y, File file) {
        super(x,y,file);
        this.x=x;
        this.y=y;
        image=super.tools.scale(convertFileToImage(),10.0,5.0);
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

    public void update() {
        if(left) {
            setRect(new Rectangle((int)getRect().getX()-3,(int)getRect().getY(),(int)getRect().getWidth(),(int)getRect().getHeight()));
        }


        if(right) {
            setRect(new Rectangle((int)getRect().getX()+3,(int)getRect().getY(),(int)getRect().getWidth(),(int)getRect().getHeight()));
        }
    }
}
