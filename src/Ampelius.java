import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Ampelius extends Collidable{
    int imageWidth=0;
    int imageHeight=0;
    BufferedImage image=null;
    int x,y,width,height,startX,startY;
    boolean up=false;
    boolean down=false;
    boolean right=false;
    boolean left=false;

    public void setX(int x) {
        this.x = x;

        updateRectangle();
    }

    public void setY(int y) {

        updateRectangle();
        this.y = y;

        updateRectangle();
    }

    public int getX() {

        updateRectangle();
        return x;
    }

    public void updateRectangle(){
        setRect(new Rectangle(x,y,width,height));
    }

    public int getY() {
        return y;
    }

    /*public WalkableTile(File convertToImage, int imageWidth, int imageHeight) {
            super(convertToImage, imageWidth, imageHeight);
            image=super.getElementImage();
        }*/
    public Ampelius(int x, int y,File file) {
        super(x,y,file);
        this.x=x;
        this.y=y;
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
            y -= 2;
        } else if (down) {
            y += 2;
        } else if (left) {
            x -= 2;
        } else if (right) {
            x += 2;
        }
        updateRectangle();
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

    public void setTheRect() {
        setRect(new Rectangle(x,y,width,height));
    }

    public void stop() {
        up=false;
        down=false;
        left=false;
        right=false;
    }
}
