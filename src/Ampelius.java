import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Ampelius extends Collidable{
    int imageWidth=0;
    int imageHeight=0;
    BufferedImage image=null;

    boolean up=false;
    boolean down=false;
    boolean right=false;
    boolean left=false;

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

        System.out.println("\t\t\t\t\tHEY MY WIDTH IS "+getRect().getWidth()+". MY HEIGHT IS"+getRect().getHeight());
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

    public void update() {
        if (up) {
            setRect(new Rectangle((int)getRect().getX(),(int)(getRect().getY()-2),(int)getRect().getWidth(),(int)getRect().getHeight()));
        } else if (down) {
            setRect(new Rectangle((int)getRect().getX(),(int)(getRect().getY()+2),(int)getRect().getWidth(),(int)getRect().getHeight()));
        } else if (left) {
            setRect(new Rectangle((int)(getRect().getX()-2),(int)(getRect().getY()),(int)getRect().getWidth(),(int)getRect().getHeight()));
        } else if (right) {
            setRect(new Rectangle((int)(getRect().getX()+2),(int)(getRect().getY()),(int)getRect().getWidth(),(int)getRect().getHeight()));
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
