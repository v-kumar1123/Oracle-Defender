import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Ampelius extends GameElement{
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
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public int getX() {
        return x;
    }

    public void updateRectangle(){
        setRect(new Rectangle(x,y,width,height));
    }

    @Override
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
        super.setElementImage(image);
        this.imageHeight=image.getHeight();
        this.imageWidth=image.getWidth();
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
    }

    @Override
    public int getImageWidth() {
        return imageWidth;
    }

    @Override
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
